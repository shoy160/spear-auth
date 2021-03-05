package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.data.domain.po.BaseAuditPO;
import lombok.Getter;
import lombok.Setter;

/**
 * 分组表
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_group")
public class GroupPO extends BaseAuditPO {

    private static final long serialVersionUID = 1L;
    /**
     * fd_id
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 用户池Id
     */
    @TableField("fd_pool_id")
    private String poolId;

    /**
     * 分组名称
     */
    @TableField("fd_name")
    private String name;

    /**
     * 分组标识
     */
    @TableField("fd_code")
    private String code;

    /**
     * 分组描述
     */
    @TableField("fd_remark")
    private String remark;

    /**
     * 分组类型：1.用户分组,2.权限分组
     */
    @TableField("fd_type")
    private int type;
}

