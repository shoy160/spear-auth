package com.yunzhicloud.auth.entity.dto;

import com.yunzhicloud.auth.entity.enums.StateEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author shay
 * @date 2021/3/2
 */
@Getter
@Setter
@ToString
public class PoolDTO {
    /**
     * 身份池ID
     */
    private String id;

    /**
     * 身份池名称
     */
    private String name;

    /**
     * 身份池描述
     */
    private String remark;

    /**
     * 身份池Logo
     */
    private String logo;

    /**
     * 身份池域名
     */
    private String domain;

    /**
     * 身份池秘钥
     */
    private String secret;

    /**
     * 用户数
     */
    private Integer userCount;

    /**
     * 状态：1.正常,4.删除
     */
    private StateEnum state;

    /**
     * 创建时间
     */
    private Date createTime;
}
