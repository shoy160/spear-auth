package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunzhicloud.data.domain.po.BaseAuditPO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * t_login
 *
 * @author {user}
 * @date 2021/03/05
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_login")
public class UserTokenPO extends BaseAuditPO {
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
     * AccessToken
     */
    @TableField("fd_access_token")
    private String accessToken;

    /**
     * RefreshToken
     */
    @TableField("fd_refresh_token")
    private String refreshToken;

    /**
     * fd_user_agent
     */
    @TableField("fd_user_agent")
    private String userAgent;

    /**
     * 过期时间
     */
    @TableField("fd_expired_date")
    private Date expiredDate;

    /**
     * RefreshToken过期时间
     */
    @TableField("fd_expired_refresh_date")
    private Date expiredRefreshDate;
}

