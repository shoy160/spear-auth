package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.GenderEnum;
import com.yunzhicloud.auth.entity.enums.RegisterTypeEnum;
import com.yunzhicloud.auth.entity.enums.VerifyTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author shay
 * @date 2021/3/1
 */
@Getter
@Setter
@ToString
public class UserDTO {
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String account;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 验证类型：0.未验证,1.手机验证,2.邮箱验证
     */
    private VerifyTypeEnum verifyType;

    /**
     * 注册方式：0.手动添加,1.手机号码注册,2.邮箱注册
     */
    private RegisterTypeEnum registerType;

    /**
     * 用户池ID
     */
    private String poolId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 登录次数
     */
    private Integer loginCount;
}
