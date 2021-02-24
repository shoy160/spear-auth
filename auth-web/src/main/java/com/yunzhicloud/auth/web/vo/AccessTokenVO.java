package com.yunzhicloud.auth.web.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shay
 * @date 2021/2/24
 */
@Getter
@Setter
public class AccessTokenVO {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;
    private String scope;
    private String user_id;
}
