package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.data.domain.dto.BaseDateDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shay
 * @date 2021/2/25
 */
@Getter
@Setter
@ApiModel
public class ApplicationDTO extends BaseDateDTO {
    /**
     * App ID
     */
    @ApiModelProperty("应用ID")
    private String id;

    /**
     * App Secret
     */
    @ApiModelProperty("应用秘钥")
    private String secret;

    /**
     * 用户池秘钥
     */
    @ApiModelProperty("用户池秘钥")
    private String poolSecret;

    /**
     * 应用名称
     */
    @ApiModelProperty("应用名称")
    private String name;

    /**
     * 应用Logo
     */
    @ApiModelProperty("应用Logo")
    private String logo;

    /**
     * 认证地址
     */
    @ApiModelProperty("应用域名")
    private String domain;

    /**
     * 登录回调
     */
    @ApiModelProperty("登录回调")
    private String redirectLogin;

    /**
     * 登出回调
     */
    @ApiModelProperty("登出回调")
    private String redirectLogout;

    /**
     * 所属认证池
     */
    @ApiModelProperty("所属认证池ID")
    private String poolId;

    /**
     * 授权码过期时间(秒）
     */
    @ApiModelProperty("授权码过期时间(秒）")
    private Integer timeCode;

    /**
     * access_token过期时间(秒)
     */
    @ApiModelProperty("access_token过期时间(秒)")
    private Integer timeAccess;

    /**
     * refresh_token过期时间(秒)
     */
    @ApiModelProperty("refresh_token过期时间(秒)")
    private Integer timeRefresh;

    /**
     * cookie过期时间(秒)
     */
    @ApiModelProperty("cookie过期时间(秒)")
    private Integer timeCookie;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private StateEnum state;
}
