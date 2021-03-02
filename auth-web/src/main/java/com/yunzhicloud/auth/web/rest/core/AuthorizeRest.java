package com.yunzhicloud.auth.web.rest.core;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.AuthorizeCmd;
import com.yunzhicloud.auth.web.command.TokenCmd;
import com.yunzhicloud.auth.web.config.AuthProperties;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.auth.web.vo.AppPublicConfigVO;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class AuthorizeRest extends BaseRest {

    private final ApplicationService appService;
    private final AuthorizeHandler handler;
    private final AuthProperties config;

    @GetMapping("config/{app_id}")
    @ApiOperation(value = "应用公共配置")
    public ResultDTO<AppPublicConfigVO> config(@PathVariable String app_id) {
        ApplicationDTO app = currentApp(app_id);
        return success(toBean(app, AppPublicConfigVO.class));
    }

    @GetMapping("authorize")
    @ApiOperation(value = "统一认证")
    public void authorize(@Validated AuthorizeCmd cmd,
                          HttpServletResponse response) throws IOException {
        cmd.validate();
        String appId = cmd.getClient_id();
        ApplicationDTO app = currentApp(appId);
        if (cmd.typeCode()) {
            //判断登录状态
            Token token = currentToken(appId);
            if (token != null) {
                Map<String, Object> map = new HashMap<>(3);
                String code = handler.saveCode(currentAccessToken(), app);
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

    @GetMapping("token")
    @ApiOperation(value = "获取凭证")
    public ResultDTO<AccessTokenVO> token(@Validated TokenCmd cmd) {
        cmd.validate();
        String appId = cmd.getClient_id();
        ApplicationDTO app = currentApp(appId);
        if (cmd.typeCode()) {
            if (!app.getSecret().equalsIgnoreCase(cmd.getClient_secret())) {
                return fail("应用秘钥不匹配");
            }
            String authToken = handler.checkCode(cmd.getClient_id(), cmd.getCode());
            if (CommonUtils.isNotEmpty(authToken)) {
                //获取凭证
                Token token = currentToken(appId);
                if (token == null) {
                    return fail("用户尚未登录");
                }
                AccessTokenVO tokenVO = handler.generateToken(token, app);
                return success(tokenVO);
            } else {
                return fail("授权码已失效");
            }
        } else if (cmd.typeRefresh()) {
            //刷新Token
            if (!app.getSecret().equalsIgnoreCase(cmd.getClient_secret())) {
                return fail("应用秘钥不匹配");
            }
            Token token = handler.checkRefreshToken(appId, cmd.getRefresh_token());
            if (token == null) {
                return fail("授权刷新码已失效");
            }
            AccessTokenVO tokenVO = handler.generateToken(token, app);
            return success(tokenVO);
        }
        AccessTokenVO tokenVO = new AccessTokenVO();
        return success(tokenVO);
    }

    @GetMapping("sync/{clientId}")
    @ApiOperation(value = "同步登录凭证")
    public ResultDTO<AccessTokenVO> sync(@PathVariable String clientId) {
        Token token = currentToken(clientId);
        if (token != null) {
            ApplicationDTO app = currentApp(clientId);
            AccessTokenVO tokenVO = handler.generateToken(token, app);
            return success(tokenVO);
        }
        return success(new AccessTokenVO());
    }

    @PutMapping("logout/{clientId}")
    @ApiOperation(value = "注销登录")
    public ResultDTO logout(@PathVariable String clientId) {
        handler.removeToken(clientId, currentToken(clientId));
        return success();
    }
}
