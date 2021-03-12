package com.yunzhicloud.auth.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.data.domain.po.BaseAuditPO;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.Location;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 应用表
 *
 * @author shay
 * @date 2021/02/24
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_application")
public class ApplicationPO extends BaseAuditPO {
    private static final long serialVersionUID = 1L;
    /**
     * App ID
     */
    @TableId(value = "fd_id")
    private String id;


    /**
     * App Secret
     */
    @TableField("fd_secret")
    private String secret;

    /**
     * 应用名称
     */
    @TableField("fd_name")
    private String name;

    /**
     * 应用Logo
     */
    @TableField("fd_logo")
    private String logo;

    /**
     * 认证地址
     */
    @TableField("fd_domain")
    private String domain;

    /**
     * 登录回调
     */
    @TableField("fd_redirect_login")
    private String redirectLogin;

    /**
     * 登出回调
     */
    @TableField("fd_redirect_logout")
    private String redirectLogout;

    /**
     * 所属认证池
     */
    @TableField("fd_pool_id")
    private String poolId;

    /**
     * 访问控制：1.允许所有用户访问，2.拒绝所有用户访问
     */
    @TableField("fd_access_state")
    private int accessState;

    /**
     * 授权码过期时间(秒）
     */
    @TableField("fd_time_code")
    private int timeCode;

    /**
     * access_token过期时间(秒)
     */
    @TableField("fd_time_access")
    private int timeAccess;

    /**
     * refresh_token过期时间(秒)
     */
    @TableField("fd_time_refresh")
    private int timeRefresh;

    /**
     * cookie过期时间(秒)
     */
    @TableField("fd_time_cookie")
    private int timeCookie;

    /**
     * 状态
     */
    @TableField("fd_state")
    private int state;

    public ApplicationPO() {
        this.setTimeCode(600);
        this.setTimeAccess(1209600);
        this.setTimeRefresh(2592000);
        this.setTimeCookie(1209600);
        setState(StateEnum.Normal.getValue());
    }
}

