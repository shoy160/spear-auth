package com.yunzhicloud.auth.core.session;

import cn.hutool.core.convert.Convert;
import com.yunzhicloud.core.exception.BusinessException;
import com.yunzhicloud.core.utils.CommonUtils;

import java.io.Closeable;

/**
 * @author shay
 * @date 2021/3/1
 */
public interface YzSession {
    /**
     * 获取用户ID
     *
     * @return UserId
     */
    Object getUserId();

    /**
     * 获取租户ID
     *
     * @return tenantId
     */
    Object getTenantId();

    /**
     * 获取用户名
     *
     * @return UserName
     */
    String getUserName();

    /**
     * 获取用户权限
     *
     * @return Role
     */
    String getRole();

    /**
     * 获取租户站点
     *
     * @return Side
     */
    TenancySide getSide();


    /**
     * 使用Session
     *
     * @param session session
     * @return closeable
     */
    Closeable use(SessionDTO session);

    /**
     * 获取用户ID
     *
     * @param clazz        clazz
     * @param defaultValue def
     * @param <T>          T
     * @return instance
     */
    default <T> T getUserId(Class<T> clazz, T defaultValue) {
        Object userId = getUserId();
        if (userId == null || CommonUtils.isEmpty(userId)) {
            return defaultValue;
        }
        return Convert.convert(clazz, userId);
    }

    /**
     * 获取用户ID
     *
     * @param clazz clazz
     * @param <T>   T
     * @return instance
     */
    default <T> T getRequiredUserId(Class<T> clazz) {
        Object userId = getUserId();
        if (userId == null || CommonUtils.isEmpty(userId)) {
            throw new BusinessException("用户ID不能为空");
        }
        return Convert.convert(clazz, userId);
    }

    /**
     * 获取租户ID
     *
     * @param clazz        clazz
     * @param defaultValue def
     * @param <T>          T
     * @return instance
     */
    default <T> T getTenantId(Class<T> clazz, T defaultValue) {
        Object userId = getTenantId();
        if (userId == null || CommonUtils.isEmpty(userId)) {
            return defaultValue;
        }
        return Convert.convert(clazz, userId);
    }

    /**
     * 获取租户ID
     *
     * @param clazz clazz
     * @param <T>   T
     * @return instance
     */
    default <T> T getRequiredTenantId(Class<T> clazz) {
        Object tenantId = getTenantId();
        if (tenantId == null || CommonUtils.isEmpty(tenantId)) {
            throw new BusinessException("租户ID不能为空");
        }
        return Convert.convert(clazz, tenantId);
    }
}
