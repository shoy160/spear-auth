package club.raveland.auth.web.rest.core;

import club.raveland.auth.entity.dto.ApplicationDTO;
import club.raveland.auth.entity.dto.UserDTO;
import club.raveland.auth.service.ApplicationService;
import club.raveland.auth.service.UserService;
import club.raveland.auth.web.command.CodeLoginCmd;
import club.raveland.auth.web.command.LoginCmd;
import club.raveland.auth.web.config.AuthorizeHandler;
import club.raveland.auth.web.filter.EnableAuth;
import club.raveland.auth.web.vo.AccessTokenVO;
import club.raveland.core.domain.dto.ResultDTO;
import club.raveland.core.enums.ResultCode;
import club.raveland.core.security.Token;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author shay
 * @date 2021/2/24
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("account")
@Api(tags = "账号服务")
public class AccountRest extends BaseRest {

    private final ApplicationService appService;
    private final UserService userService;
    private final AuthorizeHandler handler;

    @EnableAuth
    @GetMapping
    @ApiOperation(value = "获取账号信息")
    public ResultDTO<UserDTO> info() {
        Token token = currentToken();
        if (token == null) {
            return fail(ResultCode.UN_AUTHORIZED);
        }
        UserDTO dto = userService.detail(token.getId());
        return success(dto);
    }

    @PostMapping("login")
    @ApiOperation(value = "密码登录")
    public ResultDTO<AccessTokenVO> login(@Valid @RequestBody LoginCmd cmd) {
        ApplicationDTO app = appService.getAndCheck(cmd.getAppId());
        UserDTO dto = userService.login(cmd.getAccount(), cmd.getPassword(), cmd.getCode());
        AccessTokenVO vo = handler.generateToken(dto, app);
        String userAgent = getRequest().getHeader("User-Agent");
        String ip = getRequest().getRemoteHost();
        userService.updateLogin(dto.getId(), ip, userAgent);
        return success(vo);
    }

    @PostMapping("login/code")
    @ApiOperation(value = "验证码登录")
    public ResultDTO<AccessTokenVO> codeLogin(@Valid @RequestBody CodeLoginCmd cmd) {
        ApplicationDTO app = appService.getAndCheck(cmd.getAppId());
        UserDTO dto = userService.loginByMobile(cmd.getMobile(), cmd.getVerifyCode());
        AccessTokenVO vo = handler.generateToken(dto, app);
        String userAgent = getRequest().getHeader("User-Agent");
        String ip = getRequest().getRemoteHost();
        userService.updateLogin(dto.getId(), ip, userAgent);
        return success(vo);
    }

    @PostMapping("code/{mobile}")
    @ApiOperation(value = "发送验证码")
    public ResultDTO<?> sendCode(@PathVariable String mobile) {
        String code = RandomUtil.randomNumbers(6);
        userService.sendVerifyCode(mobile, code, false);
        return success(code);
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
}