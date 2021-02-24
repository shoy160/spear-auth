package com.yunzhicloud.auth.web.rest.core;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.AuthorizeCmd;
import com.yunzhicloud.auth.web.command.TokenCmd;
import com.yunzhicloud.auth.web.config.AuthProperties;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.security.AuthContext;
import com.yunzhicloud.web.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.RequestUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务
 *
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("oauth")
@Api(tags = "认证服务")
public class AuthorizeRest extends BaseController {

    private final ApplicationService appService;
    private final Cache<String, String> cache;
    private final AuthProperties config;

    private Token getToken(String token, String secret) {
        if (CommonUtils.isEmpty(token)) {
            return null;
        }
        return Token.verify(token, secret);
    }

    private String currentUrl() {
        HttpServletRequest request = getRequest();
        String uri = request.getRequestURL().toString();
        Map<String, String[]> map = request.getParameterMap();
        return UriUtil.buildParams(uri, map);
    }

    @GetMapping("authorize")
    @ApiOperation(value = "统一认证")
    public void authorize(@Validated AuthorizeCmd cmd,
                          @CookieValue(value = "auth_token", required = false) String authToken,
                          HttpServletResponse response) throws IOException {
        cmd.validate();
        ApplicationPO app = appService.getAndCheck(cmd.getClient_id());
        if (cmd.isCode()) {
            //判断登录状态
            Token token = getToken(authToken, app.getSecret());
            if (token != null) {
                Map<String, Object> map = new HashMap<>(3);
                String code = RandomUtil.randomString(15);
                cache.put(String.format("auth:%s:%s", app.getId(), code), authToken, app.getTimeCode(), TimeUnit.SECONDS);
                map.put("code", code);
                map.put("pool", app.getPoolId());
                map.put("app_id", app.getId());
                String redirect = app.getRedirectLogin();
                if (!CommonUtils.isEmpty(cmd.getRedirect_uri())) {
                    redirect = cmd.getRedirect_uri();
                }
                String uri = UriUtil.build(redirect, map);
                response.sendRedirect(uri);
            } else {
                //跳转登录
                String loginSite = config.getLoginSite();
                Map<String, Object> map = new HashMap<>(3);
                map.put("redirect_uri", URLEncoder.encode(currentUrl(), "utf-8"));
                map.put("pool", app.getPoolId());
                map.put("app_id", app.getId());
                String uri = UriUtil.build(loginSite, map);
                response.sendRedirect(uri);
            }
        }
    }

    @GetMapping("token")
    @ApiOperation(value = "获取凭证")
    public ResultDTO<AccessTokenVO> token(@Validated TokenCmd cmd) {
        cmd.validate();
        if (cmd.isCode()) {
            ApplicationPO app = appService.getAndCheck(cmd.getClient_id());
            if (!app.getSecret().equalsIgnoreCase(cmd.getClient_secret())) {
                return fail("应用秘钥不匹配");
            }
            String key = String.format("auth:%s:%s", cmd.getClient_id(), cmd.getCode());
            String authToken = cache.get(key);
            if (CommonUtils.isNotEmpty(authToken)) {
                cache.remove(key);
                //获取凭证
                Token token = getToken(authToken, app.getSecret());
                if (token == null) {
                    return fail("用户尚未登录");
                }
                AccessTokenVO tokenVO = new AccessTokenVO();
                Date expiredAt = new Date(System.currentTimeMillis() + app.getTimeAccess() * 1000);
                String accessToken = token.jwt(app.getSecret(), expiredAt);
                tokenVO.setAccess_token(accessToken);
                tokenVO.setExpires_in(app.getTimeAccess());
                tokenVO.setRefresh_token(RandomUtil.randomString(32));
                tokenVO.setToken_type("bearer");
                tokenVO.setScope("");
                tokenVO.setUser_id(token.getId());
                return success(tokenVO);
            } else {
                return fail("授权码已过期");
            }
        }
        AccessTokenVO tokenVO = new AccessTokenVO();
        return success(tokenVO);
    }
}
