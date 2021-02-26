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
public class CodeLoginCmd {
    @NotBlank(message = "应用ID不能为空")
    @ApiModelProperty("应用ID")
    private String appId;

    @NotBlank(message = "用户池ID不能为空")
    @ApiModelProperty("用户池ID")
    private String poolId;

    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty("手机号码")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("图形验证码")
    private String verifyCode;
}
