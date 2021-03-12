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
    @Resource
    private AuthorizeHandler handler;
    @Resource
    private PoolService poolService;
    @Resource
    private ApplicationService appService;


    private Token getToken(HttpServletRequest request) {
        try {
            Object value = request.getAttribute(AuthConstants.HEADER_ACCESS_TOKEN);
            if (value != null) {
                return (Token) value;
            }
            String accessToken = handler.getAccessToken();
            if (CommonUtils.isEmpty(accessToken)) {
                return null;
            }
            String secret = "", groupId = "";
            String appId = handler.getAppId();
            if (appId != null) {
                ApplicationDTO app = appService.detail(appId);
                if (app != null) {
                    secret = app.getTokenSecret();
                    groupId = app.getGroupId();
                }
            } else {
                Object tenantId = tenantSolver().getTenantId();
                if (tenantId != null) {
                    groupId = tenantId.toString();
                    PoolDTO pool = poolService.detail(groupId);
                    if (pool != null) {
                        secret = pool.getSecret();
                    }
                }
            }
            Token token = handler.verifyToken(accessToken, secret, groupId);
            request.setAttribute(AuthConstants.HEADER_ACCESS_TOKEN, token);
            return token;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Bean
    public TokenSolver tokenSolver() {
        return () -> {
            HttpServletRequest request = AuthContext.getRequest();
            return getToken(request);
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
                poolId = config.getDefaultPool();
            }
            return poolId;
        };
    }
}
