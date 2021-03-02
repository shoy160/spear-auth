package com.yunzhicloud.auth.web;

import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.core.Constants;
import com.yunzhicloud.web.YzCloudApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author shay
 * @date 2021/2/24
 */
@SpringBootApplication
@ComponentScan(value = Constants.BASE_PACKAGES)
public class AuthApplication {
    public static void main(String[] args) {
        YzCloudApplication.run(AuthConstants.SERVICE_NAME, AuthApplication.class, args);
    }
}
