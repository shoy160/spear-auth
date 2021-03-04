package com.yunzhicloud.auth.web.filter;

import com.yunzhicloud.web.security.YzAuth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shay
 * @date 2021/2/25
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAuth {
    /**
     * 允许匿名
     *
     * @return boolean
     */
    boolean anonymous() default false;

    boolean redirect() default false;

    /**
     * 权限列表(任一)
     *
     * @return roles
     */
    String[] roles() default {};
}
