package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.auth.entity.enums.StateEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class PoolPO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户池ID
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 用户池名称
     */
    @TableField("fd_name")
    private String name;

    /**
     * 用户池描述
     */
    @TableField("fd_remark")
    private String remark;

    /**
     * 用户池Logo
     */
    @TableField("fd_logo")
    private String logo;

    /**
     * 用户池域名
     */
    @TableField("fd_domain")
    private String domain;

    /**
     * 用户池秘钥
     */
    @TableField("fd_secret")
    private String secret;

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
     * 创建时间
     */
    @TableField("fd_create_time")
    private LocalDateTime createTime;

    /**
     * 租户ID
     */
    @TableField("fd_tenant_id")
    private int tenantId;

    /**
     * 最后注册时间
     */
    @TableField("fd_last_register_time")
    private LocalDateTime lastRegisterTime;

    /**
     * 最后登录时间
     */
    @TableField("fd_last_login_time")
    private LocalDateTime lastLoginTime;

    public PoolPO() {
        setCreateTime(LocalDateTime.now());
        setState(StateEnum.Normal.getValue());
    }
}

