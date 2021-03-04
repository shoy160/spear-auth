package com.yunzhicloud.auth.web.rest.core;

import com.sun.org.apache.bcel.internal.generic.RET;
import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.utils.CookieUtil;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.core.session.YzSession;
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

    @Resource
    private YzSession session;

    protected String currentPool() {
        return session.getTenantId(String.class, null);
    }

    protected String currentUrl() {
        return UriUtil.currentUrl(getRequest());
    }

    protected String currentAppId() {
        return handler.getAppId(getRequest());
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
        return handler.getAccessToken(getRequest());
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
