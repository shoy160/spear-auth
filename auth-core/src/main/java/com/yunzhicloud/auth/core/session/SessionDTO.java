package com.yunzhicloud.auth.core.session;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shay
 * @date 2021/3/1
 */
@Getter
@Setter
@ToString
public class SessionDTO {
    private Object userId;
    private Object tenantId;
    private String userName;
    private String role;

    public SessionDTO() {
    }

    public SessionDTO(Object userId) {
        this.userId = userId;
    }

    public SessionDTO(Object userId, Object tenantId) {
        this(userId);
        this.tenantId = tenantId;
    }
}
