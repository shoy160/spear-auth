package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.data.domain.po.BaseAuditPO;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权表
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_grant")
public class GrantPO extends BaseAuditPO {
    private static final long serialVersionUID = 1L;
    /**
     * fd_id
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 用户池ID
     */
    @TableField("fd_pool_id")
    private String poolId;

    /**
     * 命名空间(分组)
     */
    @TableField("fd_namespace")
    private String namespace;

    /**
     * 授权类型：1.登录授权，2.资源授权
     */
    @TableField("fd_type")
    private int type;

    /**
     * 授权对象类型：1.用户，2.角色，3.分组，4.机构
     */
    @TableField("fd_target_type")
    private int targetType;

    /**
     * 授权对象
     */
    @TableField("fd_target_id")
    private String targetId;

    /**
     * 授权对象名称
     */
    @TableField("fd_target_name")
    private String targetName;

    /**
     * 授权策略：1.允许，2.禁止
     */
    @TableField("fd_policy")
    private int policy;
}

