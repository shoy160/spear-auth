package club.raveland.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 资源表
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_resource")
public class ResourcePO extends BaseAuthPO {
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
     * 类型
     */
    @TableField("fd_type")
    private int type;

    /**
     * 名称
     */
    @TableField("fd_name")
    private String name;

    /**
     * URL
     */
    @TableField("fd_url")
    private String url;

    /**
     * 图标
     */
    @TableField("fd_icon")
    private String icon;

    /**
     * 描述
     */
    @TableField("fd_remark")
    private String remark;

    /**
     * 操作列表
     */
    @TableField("fd_actions")
    private String actions;
}

