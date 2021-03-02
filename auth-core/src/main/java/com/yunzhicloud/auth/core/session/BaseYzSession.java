package com.yunzhicloud.auth.core.session;

import com.yunzhicloud.auth.core.AuthConstants;

import java.io.Closeable;

/**
 * @author shay
 * @date 2021/3/1
 */
public abstract class BaseYzSession implements YzSession {

    private SessionDTO tempSession;

    protected SessionDTO getTempSession() {
        return this.tempSession;
    }

    /**
     * 获取Session中用户ID
     *
     * @return userID
     */
    protected abstract Object getSessionUserId();

    /**
     * 获取Session中TenantID
     *
     * @return tenantID
     */
    protected abstract Object getSessionTenantId();

    /**
     * 获取Session中约定
     *
     * @param key key
     * @return value
     */
    protected abstract String getClaimValue(String key);

    @Override
    public Object getUserId() {
        if (this.tempSession != null) {
            return this.tempSession.getUserId();
        }
        return getSessionUserId();
    }

    @Override
    public Object getTenantId() {
        if (this.tempSession != null) {
            return this.tempSession.getTenantId();
        }
        return getSessionTenantId();
    }

    @Override
    public String getUserName() {
        if (this.tempSession != null) {
            return this.tempSession.getUserName();
        }
        return getClaimValue(AuthConstants.CLAIM_USERNAME);
    }

    @Override
    public String getRole() {
        if (this.tempSession != null) {
            return this.tempSession.getRole();
        }
        return getClaimValue(AuthConstants.CLAIM_ROLE);
    }

    @Override
    public TenancySide getSide() {
        return getTenantId() != null ? TenancySide.Tenant : TenancySide.Host;
    }

    @Override
    public Closeable use(SessionDTO session) {
        this.tempSession = session;
        return () -> tempSession = null;
    }
}
