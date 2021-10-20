package club.raveland.auth.web.rest.manage;

import club.raveland.auth.service.ApplicationService;
import club.raveland.auth.web.config.AuthorizeHandler;
import club.raveland.auth.web.utils.UriUtil;
import club.raveland.core.security.Token;
import club.raveland.core.security.TokenSolver;
import club.raveland.core.session.RavelandSession;
import club.raveland.web.annotation.EnableAuth;
import club.raveland.web.base.BaseController;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/3/2
 */
@EnableAuth
public abstract class BaseRest extends BaseController {
    @Resource
    private AuthorizeHandler handler;

    @Resource
    private RavelandSession session;

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

    @Override
    protected Token currentToken() {
        return tokenSolver.getToken();
    }
}
