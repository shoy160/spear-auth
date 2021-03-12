package com.yunzhicloud.auth.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author shay
 * @date 2021/2/24
 */
@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    /**
     * 登录站点
     */
    private String loginSite = "/login";

    /**
     * 默认用户池ID
     */
    private String defaultPool = "ur2sqzodgf49becg";

    /**
     * 默认应用ID
     */
    private String defaultApp = "zkdvmn29r6urrmkx";
}
