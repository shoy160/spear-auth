package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.GrantPolicyEnum;
import com.yunzhicloud.auth.entity.enums.GrantTypeEnum;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 授权对象
 *
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ApiModel
@ToString
public class GrantDTO {

    @ApiModelProperty("授权ID")
    private String id;

    @ApiModelProperty("授权分组")
    private String namespace;

    /**
     * 授权类型
     */
    @ApiModelProperty("授权类型")
    private GrantTypeEnum type;
    /**
     * 授权对象ID
     */
    @ApiModelProperty("授权对象ID")
    private String targetId;
    /**
     * 授权对象名称
     */
    @ApiModelProperty("授权对象名称")
    private String targetName;
    /**
     * 授权策略
     */
    @ApiModelProperty("授权策略")
    private GrantPolicyEnum policy;

    /**
     * 授权状态
     */
    @ApiModelProperty("授权状态")
    private StateEnum state;
}
