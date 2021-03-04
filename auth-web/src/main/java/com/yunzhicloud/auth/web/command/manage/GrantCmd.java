package com.yunzhicloud.auth.web.command.manage;

import com.yunzhicloud.auth.entity.enums.GrantPolicyEnum;
import com.yunzhicloud.auth.entity.enums.GrantTypeEnum;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 授权命令
 *
 * @author shay
 * @date 2021/3/4
 */
@Getter
@Setter
@ApiModel
public class GrantCmd {
    @NotBlank(message = "分组不能为空")
    @ApiModelProperty("分组")
    private String namespace;

    @NotBlank(message = "授权类型不能为空")
    @ApiModelProperty("授权类型")
    private GrantTypeEnum type;

    @NotBlank(message = "授权对象不能为空")
    @ApiModelProperty("授权对象ID")
    private String targetId;

    @ApiModelProperty("授权策略")
    private GrantPolicyEnum policy;
}
