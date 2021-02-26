package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.auth.entity.po.ApplicationPO;
import com.yunzhicloud.core.domain.BaseDTO;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.EnumUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author shay
 * @date 2021/2/25
 */
@Getter
@Setter
public class ApplicationDTO extends BaseDTO {
    /**
     * App ID
     */
    private String id;

    /**
     * App Secret
     */
    private String secret;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用Logo
     */
    private String logo;

    /**
     * 认证地址
     */
    private String domain;

    /**
     * 登录回调
     */
    private String redirectLogin;

    /**
     * 登出回调
     */
    private String redirectLogout;

    /**
     * 所属认证池
     */
    private String poolId;

    /**
     * 授权码过期时间(秒）
     */
    private Integer timeCode;

    /**
     * access_token过期时间(秒)
     */
    private Integer timeAccess;

    /**
     * refresh_token过期时间(秒)
     */
    private Integer timeRefresh;

    /**
     * cookie过期时间(秒)
     */
    private Integer timeCookie;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private StateEnum state;

    public static ApplicationDTO convert(ApplicationPO entity) {
        if (entity == null) {
            return null;
        }
        ApplicationDTO dto = CommonUtils.toBean(entity, ApplicationDTO.class);
        dto.setState(EnumUtils.getEnum(entity.getState(), StateEnum.class));
        return dto;
    }
}
