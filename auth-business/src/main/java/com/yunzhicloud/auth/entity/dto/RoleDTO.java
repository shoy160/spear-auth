package com.yunzhicloud.auth.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ToString
@ApiModel
public class RoleDTO {
    @ApiModelProperty("角色ID")
    private String id;
    @ApiModelProperty("角色分组ID")
    private String namespace;
    @ApiModelProperty("角色编码")
    private String code;
    @ApiModelProperty("角色描述")
    private String remark;
    @ApiModelProperty("创建时间")
    private Date createDate;
    @ApiModelProperty("更新时间")
    private Date updatedDate;
}
