package com.yunzhicloud.auth.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.auth.entity.enums.StateEnum;
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
public class ApplicationPO implements Serializable {
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
     * 创建时间
     */
    @TableField("fd_create_time")
    private LocalDateTime createTime;

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
        this.setCreateTime(LocalDateTime.now());
        setState(StateEnum.Normal.getValue());
    }
}

