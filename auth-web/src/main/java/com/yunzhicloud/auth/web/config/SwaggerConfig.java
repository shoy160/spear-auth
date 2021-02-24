package com.yunzhicloud.auth.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.yunzhicloud.auth.AuthConstants;
import com.yunzhicloud.core.Constants;
import com.yunzhicloud.web.swagger.BaseSwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author shay
 * @date 2021/2/24
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends BaseSwaggerConfig {
    private String getBasePackage(String group) {
        final String restPackage = AuthConstants.REST_PACKAGE;
        return String.format("%s.%s", restPackage, group);
    }

    @Override
    protected Docket customHeader(Docket docket) {
        return docket;
    }

    @Bean
    @Profile({Constants.MODE_DEV, Constants.MODE_TEST})
    public Docket fileDocket() {
        return getDocket("认证授权服务", AuthConstants.VERSION, getBasePackage("core"), "认证授权服务");
    }

    @Bean
    @Profile({Constants.MODE_DEV, Constants.MODE_TEST})
    public Docket mediaDocket() {
        return getDocket("后台管理", AuthConstants.VERSION, getBasePackage("manage"), "后台管理");
    }
}
