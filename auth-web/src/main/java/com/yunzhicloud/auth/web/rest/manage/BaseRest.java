package com.yunzhicloud.auth.web.rest.manage;

import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.dto.PoolDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.service.PoolService;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.filter.EnableAuth;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.base.BaseController;
import com.yunzhicloud.web.vo.Token;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/3/1
 */
@EnableAuth
public abstract class BaseRest extends BaseController {

    @Resource
    private AuthorizeHandler handler;

    @Resource
    private ApplicationService service;

    protected String currentPool() {
        return getHeader(AuthConstants.HEADER_POOL_ID);
    }

    @Override
    protected Token currentToken() {
        String authorization = getHeader(AuthConstants.HEADER_AUTHORIZATION);
        if (CommonUtils.isEmpty(authorization)) {
            return null;
        }
        String[] split = authorization.split("\\s", 2);
        if (split.length == 1 || !AuthConstants.HEADER_BEARER.equalsIgnoreCase(split[0])) {
            return null;
        }
        String appId = getRequest().getParameter(AuthConstants.PARAM_APP_ID);
        ApplicationDTO app = service.getAndCheck(appId);
        return handler.verifyToken(split[1], app);
    }
}
