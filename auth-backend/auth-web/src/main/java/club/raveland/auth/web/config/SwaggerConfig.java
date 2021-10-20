package club.raveland.auth.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import club.raveland.auth.core.AuthConstants;
import club.raveland.core.Constants;
import club.raveland.web.config.BaseProperties;
import club.raveland.web.swagger.BaseSwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shay
 * @date 2021/2/24
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig extends BaseSwaggerConfig {
    @Resource
    private BaseProperties properties;

    private String getBasePackage(String group) {
        final String restPackage = AuthConstants.REST_PACKAGE;
        return String.format("%s.%s", restPackage, group);
    }

    @Override
    protected Docket customHeader(Docket docket) {
        List<Parameter> params = new ArrayList<>();
        Parameter tokenHeader = new ParameterBuilder()
                .name(AuthConstants.HEADER_AUTHORIZATION)
                .description("用户凭证")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue(AuthConstants.HEADER_BEARER.concat(" ").concat(this.properties.getSwaggerToken()))
                .build();
        Parameter poolHeader = new ParameterBuilder()
                .name(AuthConstants.HEADER_POOL_ID)
                .description("用户池ID")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        Parameter appHeader = new ParameterBuilder()
                .name(AuthConstants.HEADER_APP_ID)
                .description("应用ID")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        params.add(tokenHeader);
        params.add(poolHeader);
        params.add(appHeader);
        return docket.globalOperationParameters(params);
    }

    @Bean
    @Profile({Constants.MODE_DEV, Constants.MODE_TEST})
    public Docket mediaDocket() {
        return getDocket("后台管理", AuthConstants.VERSION, getBasePackage("manage"), "02_management");
    }

    @Bean
    @Profile({Constants.MODE_DEV, Constants.MODE_TEST})
    public Docket fileDocket() {
        return getDocket("认证授权", AuthConstants.VERSION, getBasePackage("core"), "01_auth");
    }
}
