package com.yunzhicloud.auth.web.rest.core;

import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.core.domain.ResultDTO;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.vo.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @GetMapping
    @ApiOperation(value = "获取账号信息")
    public ResultDTO info() {
        return success();
    }

    @PostMapping("login")
    @ApiOperation(value = "密码登录")
    public ResultDTO<AccessTokenVO> login() {
        return success(new AccessTokenVO());
    }

    @PostMapping("login/code")
    @ApiOperation(value = "验证码登录")
    public ResultDTO<AccessTokenVO> codeLogin() {
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
    public String loginTest(
            @RequestParam String app_id,
            @RequestParam String redirect_uri,
            HttpServletResponse response) throws IOException {
        ApplicationPO app = appService.getAndCheck(app_id);
        Token token = new Token();
        token.setId("123456");
        token.setName("shay");
        token.setRole("admin");
        token.setClaimValue("test", "12456");
        String accessToken = token.jwt(app.getSecret());
        Cookie cookie = new Cookie("auth_token", accessToken);
        cookie.setMaxAge(app.getTimeCookie());
        response.addCookie(cookie);
        return redirect_uri;
    }
}