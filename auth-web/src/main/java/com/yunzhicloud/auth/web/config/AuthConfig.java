package com.yunzhicloud.auth.web.config;

import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.core.session.TenantSolver;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.security.AuthContext;
import com.yunzhicloud.web.security.TokenSolver;
import com.yunzhicloud.web.vo.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shay
 * @date 2021/3/4
 */
@Configuration
public class AuthConfig {
    @Resource
    private AuthProperties config;

    @Bean
    public TokenSolver tokenSolver(AuthorizeHandler handler) {
        return () -> handler.getToken(true);
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
                poolId = config.getDefaultPool();
            }
            return poolId;
        };
    }
}
