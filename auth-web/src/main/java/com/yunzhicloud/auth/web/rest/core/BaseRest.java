package com.yunzhicloud.auth.web.rest.core;

import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.security.TokenSolver;
import com.yunzhicloud.web.security.YzAuth;
import com.yunzhicloud.web.vo.Token;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/3/12
 */
public abstract class BaseRest extends BaseController {
    @Resource
    private AuthorizeHandler handler;

    @Resource
    private YzSession session;

    @Resource
    private TokenSolver tokenSolver;

    @Resource
    private ApplicationService service;

    protected String currentPool() {
        return session.tenantIdAsString();
    }

    protected String currentUrl() {
        return UriUtil.currentUrl();
    }

    protected String currentAppId() {
        return handler.getAppId();
    }

    protected ApplicationDTO currentApp() {
        return handler.currentApp();
    }

    @Override
    protected Token currentToken() {
        return handler.getToken(false);
    }
}
