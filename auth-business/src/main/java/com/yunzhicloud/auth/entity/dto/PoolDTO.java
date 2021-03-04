package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author shay
 * @date 2021/3/2
 */
@Getter
@Setter
@ToString
@ApiModel
public class PoolDTO {
    /**
     * 用户池ID
     */
    @ApiModelProperty("用户池ID")
    private String id;

    /**
     * 用户池名称
     */
    @ApiModelProperty("用户池名称")
    private String name;

    /**
     * 用户池描述
     */
    @ApiModelProperty("用户池描述")
    private String remark;

    /**
     * 用户池Logo
     */
    @ApiModelProperty("用户池Logo")
    private String logo;

    /**
     * 用户池域名
     */
    @ApiModelProperty("用户池域名")
    private String domain;

    /**
     * 用户池秘钥
     */
    @ApiModelProperty("用户池秘钥")
    private String secret;

    /**
     * 用户数
     */
    @ApiModelProperty("用户数")
    private Integer userCount;

    /**
     * 状态：1.正常,4.删除
     */
    @ApiModelProperty("状态")
    private StateEnum state;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 最后注册时间
     */
    @ApiModelProperty("最后注册时间")
    private Date lastRegister;
}
