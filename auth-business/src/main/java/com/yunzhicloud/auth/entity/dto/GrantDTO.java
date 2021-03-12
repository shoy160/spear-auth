package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.GrantPolicyEnum;
import com.yunzhicloud.auth.entity.enums.GrantTargetTypeEnum;
import com.yunzhicloud.auth.entity.enums.GrantTypeEnum;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.data.domain.dto.BaseDateDTO;
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
public class GrantDTO extends BaseDateDTO {

    @ApiModelProperty("授权ID")
    private String id;

    @ApiModelProperty("授权分组")
    private String namespace;

    /**
     * 授权类型：1.登录授权，2.资源授权
     */
    @ApiModelProperty("授权类型")
    private GrantTypeEnum type;

    /**
     * 授权对象类型：1.用户，2.角色，3.分组，4.机构
     */
    @ApiModelProperty("授权对象类型")
    private GrantTargetTypeEnum targetType;

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
}
