package com.yunzhicloud.auth.web.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author shay
 * @date 2021/2/26
 */
@Getter
@Setter
@ApiModel
public class LoginCmd {
    @NotBlank(message = "应用ID不能为空")
    @ApiModelProperty("应用ID")
    private String appId;

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty("登录账号")
    private String account;

    @NotBlank(message = "登录密码不能为空")
    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("图形验证码")
    private String code;
}
