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
public class RoleCmd {
    @ApiModelProperty("分组")
    @NotBlank(message = "分组不能为空")
    private String namespace;
    
    @ApiModelProperty("分组")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @ApiModelProperty("角色描述")
    private String remark;
}
