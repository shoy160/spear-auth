package com.yunzhicloud.auth.web.rest.core;

import cn.hutool.core.util.IdUtil;
import com.yunzhicloud.auth.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.command.CodeLoginCmd;
import com.yunzhicloud.auth.web.command.LoginCmd;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.filter.EnableAuth;
import com.yunzhicloud.auth.web.utils.CookieUtil;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.core.enums.ResultCode;
import com.yunzhicloud.core.exception.BusinessException;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("account")
@Api(tags = "账号服务")
public class AccountRest extends BaseController {

    private final ApplicationService appService;
    private final AuthorizeHandler handler;

    @EnableAuth
    @GetMapping
    @ApiOperation(value = "获取账号信息")
    public ResultDTO info() {
        String authorization = getHeader("Authorization");
        if (CommonUtils.isEmpty(authorization)) {
            return fail(ResultCode.UN_AUTHORIZED);
        }
        String[] split = authorization.split("\\s", 2);
        if (split.length == 1 || !"bearer".equalsIgnoreCase(split[0])) {
            return fail(ResultCode.UN_AUTHORIZED);
        }
        String appId = getRequest().getParameter("app_id");
        ApplicationDTO app = appService.getAndCheck(appId);
        Token token = handler.verifyToken(split[1], app);
        if (token == null) {
            return fail(ResultCode.UN_AUTHORIZED);
        }
        return success(token);
    }

    @PostMapping("login")
    @ApiOperation(value = "密码登录")
    public ResultDTO<AccessTokenVO> login(@Validated @RequestBody LoginCmd cmd) {

        return success(new AccessTokenVO());
    }

    @PostMapping("login/code")
    @ApiOperation(value = "验证码登录")
    public ResultDTO<AccessTokenVO> codeLogin(@Validated @RequestBody CodeLoginCmd cmd) {
        return success(new AccessTokenVO());
    }

    @PostMapping("code")
    @ApiOperation(value = "发送验证码")
    public ResultDTO sendCode() {
        return success();
    }

    @PostMapping("register")
    @ApiOperation(value = "手机号注册")
    public ResultDTO<AccessTokenVO> register() {
        return success(new AccessTokenVO());
    }

    @PostMapping("register/email")
    @ApiOperation(value = "邮箱注册")
    public ResultDTO<AccessTokenVO> emailRegister() {
        return success(new AccessTokenVO());
    }

    @PostMapping("pwd/forget")
    @ApiOperation(value = "忘记密码")
    public ResultDTO forgetPwd() {
        return success();
    }

    @PostMapping("pwd/reset")
    @ApiOperation(value = "重置密码")
    public ResultDTO resetPwd() {
        return success();
    }

    @GetMapping("login/test")
    public void loginTest(
            @RequestParam String app_id,
            @RequestParam String redirect_uri) throws IOException {
        ApplicationDTO app = appService.getAndCheck(app_id);
        Token token = new Token();
        token.setId("123456");
        token.setName("shay");
        token.setRole("admin");
        token.setClaimValue("test", "12456");
        String accessToken = handler.saveToken(token, app);
        CookieUtil.set("auth_token", accessToken, app.getTimeCookie());
        getResponse().sendRedirect(redirect_uri);
    }
}