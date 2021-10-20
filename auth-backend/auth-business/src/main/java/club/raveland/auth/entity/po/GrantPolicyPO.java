package club.raveland.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权策略表
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_grant_policy")
public class GrantPolicyPO extends BaseAuthPO {
    private static final long serialVersionUID = 1L;
    /**
     * fd_id
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 授权ID
     */
    @TableField("fd_grant_id")
    private String grantId;

    /**
     * 资源类型
     */
    @TableField("fd_resource_type")
    private int resourceType;

    /**
     * 资源ID
     */
    @TableField("fd_resource_id")
    private String resourceId;

    /**
     * 资源名称
     */
    @TableField("fd_resource_name")
    private String resourceName;

    /**
     * 授权策略：1.允许，2.拒绝
     */
    @TableField("fd_policy")
    private int policy;

    /**
     * 授权操作
     */
    @TableField("fd_actions")
    private String actions;

    /**
     * 授权条件
     */
    @TableField("fd_condition")
    private String condition;
}

