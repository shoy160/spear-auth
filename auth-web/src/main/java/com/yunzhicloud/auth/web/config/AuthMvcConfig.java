package com.yunzhicloud.auth.web.config;

import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.core.session.BaseYzSession;
import com.yunzhicloud.auth.core.session.YzSession;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.filter.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author shay
 * @date 2021/2/25
 */
@Configuration
public class AuthMvcConfig implements WebMvcConfigurer {

    @Resource
    private AuthProperties config;
    @Resource
    private ApplicationService service;
    @Resource
    private AuthorizeHandler handler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthInterceptor interceptor = new AuthInterceptor(config, service, handler);
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Bean
    public YzSession getSession(HttpSession session) {
        return new BaseYzSession() {
            @Override
            protected Object getSessionUserId() {
                return session.getAttribute(AuthConstants.CLAIM_USER_ID);
            }

            @Override
            protected Object getSessionTenantId() {
                return session.getAttribute(AuthConstants.CLAIM_TENANT_ID);
            }

            @Override
            protected String getClaimValue(String key) {
                return session.getAttribute(key).toString();
            }
        };
    }
}
