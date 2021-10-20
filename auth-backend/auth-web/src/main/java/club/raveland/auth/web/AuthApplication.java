package club.raveland.auth.web;

import club.raveland.auth.core.AuthConstants;
import club.raveland.core.Constants;
import club.raveland.web.RavelandApplication;
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
        RavelandApplication.run(AuthConstants.SERVICE_NAME, AuthApplication.class, args);
    }
}
