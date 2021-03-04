package com.yunzhicloud.auth.web.rest.core;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.AuthorizeCmd;
import com.yunzhicloud.auth.web.command.TokenCmd;
import com.yunzhicloud.auth.web.config.AuthProperties;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.rest.BaseRest;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.auth.web.vo.AppPublicConfigVO;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.security.YzAuth;
import com.yunzhicloud.web.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
@YzAuth(anonymous = true)
public class AuthorizeRest extends BaseRest {

    private final ApplicationService appService;
    private final AuthorizeHandler handler;
    private final AuthProperties config;

    @GetMapping("config")
    @ApiOperation(value = "应用公共配置")
    public ResultDTO<AppPublicConfigVO> config(
            @ApiParam(value = "应用ID", required = true) @RequestParam String app_id
    ) {
        ApplicationDTO app = handler.currentApp(app_id, appService::getAndCheck);
        return success(toBean(app, AppPublicConfigVO.class));
    }

    @GetMapping("authorize")
    @ApiOperation(value = "统一认证")
    public void authorize(@Valid AuthorizeCmd cmd,
                          HttpServletResponse response) throws IOException {
        cmd.validate();
        ApplicationDTO app = currentApp();
        if (cmd.typeCode()) {
            //判断登录状态
            Token token = currentToken();
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
                UriUtil.redirectToLogin(config, app);
            }
        }
    }

    @GetMapping("token")
    @ApiOperation(value = "获取凭证")
    public ResultDTO<AccessTokenVO> token(@Valid TokenCmd cmd) {
        cmd.validate();
        ApplicationDTO app = currentApp();
        if (cmd.typeCode()) {
            if (!app.getSecret().equalsIgnoreCase(cmd.getClient_secret())) {
                return fail("应用秘钥不匹配");
            }
            String authToken = handler.checkCode(app.getPoolId(), cmd.getCode());
            if (CommonUtils.isNotEmpty(authToken)) {
                //获取凭证
                Token token = currentToken();
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
            Token token = handler.checkRefreshToken(cmd.getClient_id(), cmd.getRefresh_token());
            if (token == null) {
                return fail("授权刷新码已失效");
            }
            AccessTokenVO tokenVO = handler.generateToken(token, app);
            return success(tokenVO);
        }
        AccessTokenVO tokenVO = new AccessTokenVO();
        return success(tokenVO);
    }

    @GetMapping("sync")
    @ApiOperation(value = "同步登录凭证")
    public ResultDTO<AccessTokenVO> sync(
            @ApiParam(value = "应用ID", required = true) @RequestParam String app_id
    ) {
        Token token = currentToken();
        if (token != null) {
            ApplicationDTO app = currentApp();
            AccessTokenVO tokenVO = handler.generateToken(token, app);
            return success(tokenVO);
        }
        return success(new AccessTokenVO());
    }

    @PutMapping("logout")
    @ApiOperation(value = "注销登录")
    public ResultDTO logout(
            @ApiParam(value = "应用ID", required = true) @RequestParam String app_id
    ) {
        handler.removeToken(app_id, currentToken());
        return success();
    }
}
