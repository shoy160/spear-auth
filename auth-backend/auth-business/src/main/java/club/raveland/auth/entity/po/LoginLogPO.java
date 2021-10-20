package club.raveland.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * t_login_log
 *
 * @author shay
 * @date 2021/03/01
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_login_log")
public class LoginLogPO extends BaseAuthPO {

    private static final long serialVersionUID = 1L;
    /**
     * fd_id
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 用户ID
     */
    @TableField("fd_user_id")
    private String userId;

    /**
     * 应用ID
     */
    @TableField("fd_app_id")
    private String appId;

    /**
     * 用户池ID
     */
    @TableField("fd_pool_id")
    private String poolId;

    /**
     * UserAgent
     */
    @TableField("fd_user_agent")
    private String userAgent;

    /**
     * IP
     */
    @TableField("fd_ip_addr")
    private String ipAddr;

    /**
     * 状态：1.成功,4.失败
     */
    @TableField("fd_state")
    private int state;

    /**
     * fd_remark
     */
    @TableField("fd_remark")
    private String remark;
}
