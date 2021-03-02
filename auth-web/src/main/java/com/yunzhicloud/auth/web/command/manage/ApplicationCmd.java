package com.yunzhicloud.auth.web.command.manage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author shay
 * @date 2021/3/1
 */
@Getter
@Setter
@ApiModel
public class ApplicationCmd {

    @NotBlank(message = "应用名称不能为空")
    @ApiModelProperty("应用名称")
    private String name;

    @NotBlank(message = "应用域名不能为空")
    @ApiModelProperty("应用域名")
    private String domain;

    @NotBlank(message = "回调地址不能为空")
    @ApiModelProperty("回调地址")
    private String redirect;

    @ApiModelProperty("应用Logo")
    private String logo;
}
