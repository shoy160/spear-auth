package com.yunzhicloud.auth.web.command.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ApiModel
public class EmailRegisterCmd {
    @ApiModelProperty("用户名")
    private String account;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String mobile;

    @NotBlank(message = "分组名称不能为空")
    @ApiModelProperty("分组名称")
    private String password;
}
