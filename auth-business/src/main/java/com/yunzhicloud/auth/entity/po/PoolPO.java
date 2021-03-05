package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import com.yunzhicloud.data.domain.po.BaseAuditPO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 认证池表
 *
 * @author {user}
 * @date 2021/02/24
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_pool")
public class PoolPO extends BaseAuditPO {
    private static final long serialVersionUID = 1L;
    /**
     * 用户池ID
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 身份池秘钥
     */
    @TableField("fd_secret")
    private String secret;

    /**
     * 身份池名称
     */
    @TableField("fd_name")
    private String name;

    /**
     * 身份池描述
     */
    @TableField("fd_remark")
    private String remark;

    /**
     * 身份池Logo
     */
    @TableField("fd_logo")
    private String logo;

    /**
     * 身份池域名
     */
    @TableField("fd_domain")
    private String domain;

    /**
     * 用户数
     */
    @TableField("fd_user_count")
    private int userCount;

    /**
     * 状态：1.正常,4.删除
     */
    @TableField("fd_state")
    private int state;

    /**
     * 租户ID(所属用户ID)
     */
    @TableField("fd_tenant_id")
    private int tenantId;

    /**
     * 最后注册时间
     */
    @TableField("fd_last_register_time")
    private Date lastRegisterTime;

    /**
     * 最后登录时间
     */
    @TableField("fd_last_login_time")
    private Date lastLoginTime;

    /**
     * 是否开启单点登录
     */
    @TableField("fd_enable_sso")
    private int enableSso;

    /**
     * 安全域
     */
    @TableField("fd_cors_domain")
    private String corsDomain;

    /**
     * JWT有效时间(秒),默认1296000
     */
    @TableField("fd_jwt_expired")
    private int jwtExpired;

    /**
     * 登录失败次数限制
     */
    @TableField("fd_fail_login_limit")
    private int failLoginLimit;

    /**
     * 登录失败时间段(秒)
     */
    @TableField("fd_fail_login_time")
    private Integer failLoginTime;

    /**
     * 登录失败次数
     */
    @TableField("fd_fail_login_count")
    private Integer failLoginCount;

    /**
     * 验证规则：1.邮箱验证，2.手机验证
     */
    @TableField("fd_verify_rule")
    private int verifyRule;

    /**
     * 注册控制：1.开启允许策略,2.开启禁止策略,4.禁止注册
     */
    @TableField("fd_register_rule")
    private int registerRule;

    public PoolPO() {
        setState(StateEnum.Normal.getValue());
    }
}

