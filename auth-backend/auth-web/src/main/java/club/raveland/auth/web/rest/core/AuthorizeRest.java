package club.raveland.auth.web.rest.core;

import club.raveland.auth.entity.dto.ApplicationDTO;
import club.raveland.auth.web.command.AuthorizeCmd;
import club.raveland.auth.web.command.TokenCmd;
import club.raveland.auth.web.config.AuthProperties;
import club.raveland.auth.web.config.AuthorizeHandler;
import club.raveland.auth.web.utils.UriUtil;
import club.raveland.auth.web.vo.AccessTokenVO;
import club.raveland.auth.web.vo.AppPublicConfigVO;
import club.raveland.core.RavelandContext;
import club.raveland.core.domain.dto.ResultDTO;
import club.raveland.core.security.Token;
import club.raveland.core.utils.CommonUtils;
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
@CrossOrigin("http://192.168.2.252:8080")
public class AuthorizeRest extends BaseRest {

    private final AuthorizeHandler handler;
    private final AuthProperties config;

    @GetMapping("config")
    @ApiOperation(value = "应用公共配置")
    public ResultDTO<AppPublicConfigVO> config(
            @ApiParam(value = "应用ID") @RequestParam(required = false) String app_id
    ) {
        if (CommonUtils.isEmpty(app_id)) {
            app_id = config.getDefaultApp();
        }
        ApplicationDTO app = handler.currentApp(app_id);
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
                Map<String, Object> map = new HashMap<>(4);
                String code = handler.saveCode(app);
                map.put("code", code);
                map.put("pool", app.getPoolId());
                map.put("app_id", app.getId());
                if (RavelandContext.isDev()) {
                    map.put("secret", app.getSecret());
                }
                String redirect = app.getRedirectLogin();
                if (!CommonUtils.isEmpty(cmd.getRedirect_uri())) {
                    redirect = cmd.getRedirect_uri();
                }
                String uri = UriUtil.build(redirect, map);
                response.sendRedirect(uri);
            } else {
                //跳转登录
                UriUtil.redirectToLogin(config, app, "code");
            }
        } else if (cmd.typeToken()) {
            //判断登录状态
            Token token = currentToken();
            if (token != null) {
                String redirect = app.getRedirectLogin();
                if (!CommonUtils.isEmpty(cmd.getRedirect_uri())) {
                    redirect = cmd.getRedirect_uri();
                }
                Map<String, Object> map = new HashMap<>(2);
                map.put("app_id", app.getId());
                map.put("type", "token");
                redirect = UriUtil.build(redirect, map);
                AccessTokenVO tokenVO = handler.currentAccessToken(token, app);
                String uri = redirect.concat("#token=").concat(tokenVO.getAccess_token());
                response.sendRedirect(uri);
            } else {
                //跳转登录
                UriUtil.redirectToLogin(config, app, "token");
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
            handler.checkCode(app.getGroupId(), cmd.getCode());
            //获取凭证
            Token token = currentToken();
            if (token == null) {
                return fail("用户尚未登录");
            }
            AccessTokenVO tokenVO = handler.currentAccessToken(token, app);
            return success(tokenVO);
        } else if (cmd.typeRefresh()) {
            //刷新Token
            if (!app.getSecret().equalsIgnoreCase(cmd.getClient_secret())) {
                return fail("应用秘钥不匹配");
            }
            Token token = handler.checkRefreshToken(app, cmd.getRefresh_token());
            if (token == null) {
                return fail("授权刷新码已失效");
            }
            handler.removeToken(app, token);
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
            AccessTokenVO tokenVO = handler.currentAccessToken(token, app);
            return success(tokenVO);
        }
        return success(new AccessTokenVO());
    }

    @PutMapping("logout")
    @ApiOperation(value = "注销登录")
    public ResultDTO logout(
            @ApiParam(value = "应用ID") @RequestParam(required = false) String app_id
    ) {
        ApplicationDTO app = currentApp();
        handler.removeToken(app, currentToken());
        return success();
    }
}
