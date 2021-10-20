package club.raveland.auth.web.config;

import club.raveland.auth.core.AuthConstants;
import club.raveland.core.security.Token;
import club.raveland.core.security.TokenSolver;
import club.raveland.core.security.TokenVerify;
import club.raveland.core.session.TenantSolver;
import club.raveland.core.utils.CommonUtils;
import club.raveland.web.config.BaseProperties;
import club.raveland.web.filter.CorsFilter;
import club.raveland.web.security.AuthContext;
import club.raveland.web.security.RavelandTokenSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shay
 * @date 2021/3/4
 */
@Configuration
@RequiredArgsConstructor
public class AuthConfig {
    private final BaseProperties config;
    private final AuthProperties authConfig;
    private final TokenVerify tokenVerify;

    @Bean
    public TokenSolver tokenSolver(AuthorizeHandler handler) {
        RavelandTokenSolver tokenSolver = new RavelandTokenSolver(config, tokenVerify);
        return new TokenSolver() {
            @Override
            public Token getToken(String group) {
                return handler.getToken();
            }

            @Override
            public String generateToken(Token token, String group, boolean single) {
                return tokenSolver.generateToken(token, group, single);
            }
        };
    }

    @Bean
    public TenantSolver tenantSolver() {
        return () -> {
            HttpServletRequest request = AuthContext.getRequest();
            if (request == null) {
                return null;
            }
            String poolId = request.getParameter(AuthConstants.PARAM_POOL_ID);
            if (CommonUtils.isEmpty(poolId)) {
                poolId = request.getHeader(AuthConstants.HEADER_POOL_ID);
            }
//            if (CommonUtils.isEmpty(poolId)) {
//                poolId = CookieUtil.get(AuthConstants.COOKIE_POOL_ID);
//            }
            if (CommonUtils.isEmpty(poolId)) {
                poolId = authConfig.getDefaultPool();
            }
            return poolId;
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(config);
    }
}
