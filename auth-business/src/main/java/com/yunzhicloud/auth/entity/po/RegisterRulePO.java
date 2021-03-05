package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.data.domain.po.BaseAuditPO;
import lombok.Getter;
import lombok.Setter;

/**
 * 允许注册
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_register_rule")
public class RegisterRulePO extends BaseAuditPO {
    private static final long serialVersionUID = 1L;
    /**
     * fd_id
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 用户池
     */
    @TableField("fd_pool_id")
    private String poolId;

    /**
     * 类型：1.邮箱，2.手机号，3.用户名
     */
    @TableField("fd_type")
    private int type;

    /**
     * 规则
     */
    @TableField("fd_rule")
    private String rule;
}

