package com.yunzhicloud.auth.web.rest.core;

import com.sun.org.apache.bcel.internal.generic.RET;
import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.utils.CookieUtil;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.vo.Token;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author shay
 * @date 2021/3/2
 */
public abstract class BaseRest extends BaseController {
    @Resource
    private AuthorizeHandler handler;

    @Resource
    private ApplicationService service;

    private String appId;

    protected void setAppId(String appId) {
        this.appId = appId;
    }

    protected String currentPool() {
        return getHeader(AuthConstants.HEADER_POOL_ID);
    }

    protected String currentUrl() {
        HttpServletRequest request = getRequest();
        String uri = request.getRequestURL().toString();
        Map<String, String[]> map = request.getParameterMap();
        return UriUtil.buildParams(uri, map);
    }

    protected String currentAppId() {
        if (CommonUtils.isEmpty(this.appId)) {
            this.appId = getRequest().getParameter(AuthConstants.PARAM_APP_ID);
        }
        return this.appId;
    }

    protected ApplicationDTO currentApp() {
        return currentApp(null);
    }

    protected ApplicationDTO currentApp(String appId) {
        HttpSession session = getRequest().getSession();
        String key = AuthConstants.SESSION_APP;
        if (CommonUtils.isNotEmpty(appId)) {
            key = key.concat("-").concat(appId);
        } else {
            appId = currentAppId();
        }
        Object value = session.getAttribute(key);
        if (value != null) {
            return (ApplicationDTO) value;
        }
        ApplicationDTO app = service.getAndCheck(appId);
        session.setAttribute(key, app);
        return app;
    }

    protected String currentAccessToken() {
        String token = CookieUtil.get("auth_token");
        if (CommonUtils.isNotEmpty(token)) {
            return token;
        }
        String authorization = getHeader(AuthConstants.HEADER_AUTHORIZATION);
        if (CommonUtils.isNotEmpty(authorization)) {
            String[] split = authorization.split("\\s", 2);
            if (split.length == 2 && AuthConstants.HEADER_BEARER.equalsIgnoreCase(split[0])) {
                return split[1];
            }
        }
        return null;
    }

    @Override
    protected Token currentToken() {
        String accessToken = currentAccessToken();
        ApplicationDTO app = currentApp();
        return handler.verifyToken(accessToken, app);
    }

    protected Token currentToken(String appId) {
        String accessToken = currentAccessToken();
        if (CommonUtils.isEmpty(accessToken)) {
            return null;
        }
        ApplicationDTO app = currentApp(appId);
        return handler.verifyToken(accessToken, app);
    }
}
