package com.yunzhicloud.auth.web.config;

import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.filter.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

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
}
