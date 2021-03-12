package com.yunzhicloud.auth.core.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shay
 * @date 2021/3/8
 */
@Getter
@Setter
@ToString
public class TokenDTO {
    /**
     * 凭证类型
     */
    private String tokenType;
    /**
     * 访问凭证
     */
    private String accessToken;
    /**
     * 访问凭证过期时间(秒)
     */
    private Integer expiryIn;
    /**
     * 刷新凭证
     */
    private String refreshToken;
    /**
     * 刷新凭证过期时间(秒)
     */
    private Integer refreshExpiryIn;
    /**
     * 权限范围
     */
    private String scope;
}
