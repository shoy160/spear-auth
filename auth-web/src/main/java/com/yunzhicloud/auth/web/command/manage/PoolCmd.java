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
public class PoolCmd {
    @NotBlank(message = "用户池名称不能为空")
    @ApiModelProperty("用户池名称")
    private String name;

    @NotBlank(message = "用户池编码不能为空")
    @ApiModelProperty("用户池编码")
    private String code;

    @ApiModelProperty("用户池二级域名")
    private String domain;

    @ApiModelProperty("用户池备注")
    private String remark;

    @ApiModelProperty("用户池Logo")
    private String logo;
}
