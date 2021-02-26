package com.yunzhicloud.auth.web.utils;

import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.security.AuthContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/2/25
 */
public final class CookieUtil {
    /**
     * 获取Cookie
     *
     * @param name name
     * @return
     */
    public static String get(String name) {
        HttpServletRequest request = AuthContext.getRequest();
        if (request == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (CommonUtils.isEmpty(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void set(String name, String value) {
        set(name, value, 0, TimeUnit.SECONDS, true, null, null);
    }

    public static void set(String name, String value, int time) {
        set(name, value, time, TimeUnit.SECONDS, true, null, null);
    }

    public static void set(String name, String value, int time, TimeUnit timeUnit) {
        set(name, value, time, timeUnit, true, null, null);
    }

    public static void set(String name, String value, int time, TimeUnit timeUnit, boolean httpOnly, String domain, String path) {
        Cookie cookie = new Cookie(name, value);
        if (time > 0) {
            long maxAge = timeUnit.toSeconds(time);
            cookie.setMaxAge((int) maxAge);
        }
        cookie.setHttpOnly(httpOnly);
        if (CommonUtils.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        if (CommonUtils.isNotEmpty(path)) {
            cookie.setPath(path);
        } else {
            cookie.setPath("/");
        }
        AuthContext.getResponse().addCookie(cookie);
    }
}
