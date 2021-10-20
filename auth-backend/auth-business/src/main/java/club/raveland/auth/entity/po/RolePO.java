package club.raveland.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色表
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_role")
public class RolePO extends BaseAuthPO {
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
     * 角色编码
     */
    @TableField("fd_code")
    private String code;

    /**
     * 角色备注
     */
    @TableField("fd_remark")
    private String remark;
}

