package com.yunzhicloud.auth.web.rest.core;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.AuthorizeCmd;
import com.yunzhicloud.auth.web.command.TokenCmd;
import com.yunzhicloud.auth.web.config.AuthProperties;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.utils.CookieUtil;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.auth.web.vo.AppPublicConfigVO;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private final AuthorizeHandler handler;
    private final AuthProperties config;

    private String getAuthToken() {
        return CookieUtil.get("auth_token");
    }

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

    @GetMapping("config/{app_id}")
    @ApiOperation(value = "应用公共配置")
    public ResultDTO<AppPublicConfigVO> config(@PathVariable String app_id) {
        ApplicationDTO app = appService.getAndCheck(app_id);
        return success(toBean(app, AppPublicConfigVO.class));
    }

    @GetMapping("authorize")
    @ApiOperation(value = "统一认证")
    public void authorize(@Validated AuthorizeCmd cmd,
                          HttpServletResponse response) throws IOException {
        cmd.validate();
        ApplicationDTO app = appService.getAndCheck(cmd.getClient_id());
        if (cmd.typeCode()) {
            //判断登录状态
            String authToken = getAuthToken();
            Token token = getToken(authToken, app.getSecret());
            if (token != null) {
                Map<String, Object> map = new HashMap<>(3);
                String code = handler.saveCode(authToken, app);
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
                map.put("redirect_uri", currentUrl());
                map.put("pool", app.getPoolId());
                map.put("app_id", app.getId());
                String uri = UriUtil.build(loginSite, map);
                response.sendRedirect(uri);
            }
        }
    }

    private AccessTokenVO generateToken(Token token, ApplicationDTO app) {
        AccessTokenVO tokenVO = new AccessTokenVO();
        Date expiredAt = new Date(System.currentTimeMillis() + app.getTimeAccess() * 1000);
        String accessToken = token.jwt(app.getSecret(), expiredAt);
        tokenVO.setAccess_token(accessToken);
        tokenVO.setExpires_in(app.getTimeAccess());
        String refreshToken = handler.saveRefreshToken(token, app);
        tokenVO.setRefresh_token(refreshToken);
        tokenVO.setToken_type("bearer");
        tokenVO.setScope("");
        tokenVO.setUser_id(token.getId());
        return tokenVO;
    }

    @GetMapping("token")
    @ApiOperation(value = "获取凭证")
    public ResultDTO<AccessTokenVO> token(@Validated TokenCmd cmd) {
        cmd.validate();
        ApplicationDTO app = appService.getAndCheck(cmd.getClient_id());
        if (!app.getSecret().equalsIgnoreCase(cmd.getClient_secret())) {
            return fail("应用秘钥不匹配");
        }
        if (cmd.typeCode()) {
            String authToken = handler.checkCode(cmd.getClient_id(), cmd.getCode());
            if (CommonUtils.isNotEmpty(authToken)) {
                //获取凭证
                Token token = getToken(authToken, app.getSecret());
                if (token == null) {
                    return fail("用户尚未登录");
                }
                AccessTokenVO tokenVO = generateToken(token, app);
                return success(tokenVO);
            } else {
                return fail("授权码已失效");
            }
        } else if (cmd.typeRefresh()) {
            //刷新Token
            Token token = handler.checkRefreshToken(cmd.getClient_id(), cmd.getRefresh_token());
            if (token == null) {
                return fail("授权刷新码已失效");
            }
            AccessTokenVO tokenVO = generateToken(token, app);
            return success(tokenVO);
        }
        AccessTokenVO tokenVO = new AccessTokenVO();
        return success(tokenVO);
    }
}
