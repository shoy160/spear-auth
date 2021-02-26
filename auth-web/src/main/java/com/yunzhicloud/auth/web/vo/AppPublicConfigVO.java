package com.yunzhicloud.auth.web.vo;

import com.yunzhicloud.auth.entity.enums.StateEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author shay
 * @date 2021/2/26
 */
@Getter
@Setter
@ToString
public class AppPublicConfigVO {
    /**
     * App ID
     */
    private String id;

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
     * 创建时间
     */
    private Date createTime;
}
