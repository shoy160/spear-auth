package com.yunzhicloud.auth.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author shay
 * @date 2021/03/01
 * @serial generate by spear
 */
@Getter
@Setter
@TableName("t_user")
public class UserPO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * fd_id
     */
    @TableId(value = "fd_id")
    private String id;

    /**
     * 姓名
     */
    @TableField("fd_name")
    private String name;

    /**
     * 用户名
     */
    @TableField("fd_account")
    private String account;

    /**
     * 昵称
     */
    @TableField("fd_nick")
    private String nick;

    /**
     * 头像
     */
    @TableField("fd_avatar")
    private String avatar;

    /**
     * 手机号
     */
    @TableField("fd_mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField("fd_email")
    private String email;

    /**
     * 登录密码
     */
    @TableField("fd_password")
    private String password;

    /**
     * 性别
     */
    @TableField("fd_gender")
    private int gender;

    /**
     * 验证类型：0.未验证,1.手机验证,2.邮箱验证
     */
    @TableField("fd_verify_type")
    private int verifyType;

    /**
     * 注册方式：0.手动添加,1.邮箱注册,2.手机号码注册
     */
    @TableField("fd_register_type")
    private int registerType;

    /**
     * 用户池ID
     */
    @TableField("fd_pool_id")
    private String poolId;

    /**
     * 创建时间
     */
    @TableField("fd_create_date")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @TableField("fd_update_date")
    private LocalDateTime updateDate;

    /**
     * 最后登录时间
     */
    @TableField("fd_last_login_date")
    private LocalDateTime lastLoginDate;

    /**
     * 最后登录IP
     */
    @TableField("fd_last_login_ip")
    private String lastLoginIp;

    /**
     * 登录次数
     */
    @TableField("fd_login_count")
    private int loginCount;
}
