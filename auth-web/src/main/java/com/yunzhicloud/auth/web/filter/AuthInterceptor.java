package com.yunzhicloud.auth.web.filter;

import cn.hutool.core.util.ArrayUtil;
import com.yunzhicloud.auth.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.config.AuthProperties;
import com.yunzhicloud.auth.web.config.AuthorizeHandler;
import com.yunzhicloud.auth.web.utils.UriUtil;
import com.yunzhicloud.core.enums.ResultCode;
import com.yunzhicloud.core.exception.BusinessException;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.web.vo.Token;
import lombok.AllArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shay
 * @date 2021/2/25
 */
@AllArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final AuthProperties config;
    private final ApplicationService service;
    private final AuthorizeHandler authorizeHandler;

    private final static String SPLIT_AUTHORIZATION = "\\s";
    private final static String SPLIT_REG = "[,;，；]";

    private String currentUrl(HttpServletRequest request) {
        String uri = request.getRequestURL().toString();
        Map<String, String[]> map = request.getParameterMap();
        return UriUtil.buildParams(uri, map);
    }

    private void handleUnAuthorized(EnableAuth auth, HttpServletRequest request, HttpServletResponse response, ApplicationDTO app) {
        if (auth == null || !auth.redirect()) {
            throw new BusinessException(ResultCode.UN_AUTHORIZED);
        } else {
            //跳转
            String loginSite = config.getLoginSite();
            Map<String, Object> map = new HashMap<>();
            map.put("redirect_uri", currentUrl(request));
            if (app != null) {
                map.put("pool", app.getPoolId());
                map.put("app_id", app.getId());
            }
            String uri = UriUtil.build(loginSite, map);
            try {
                response.sendRedirect(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        //获取注解
        EnableAuth auth = method.getMethodAnnotation(EnableAuth.class);
        if (auth == null) {
            Class<?> clazz = method.getBeanType();
            auth = clazz.getAnnotation(EnableAuth.class);
            while (auth == null && Object.class != (clazz = clazz.getSuperclass())) {
                auth = clazz.getAnnotation(EnableAuth.class);
            }
        }
        if (auth == null || auth.anonymous()) {
            return true;
        }
        String authorization = request.getHeader(AuthConstants.HEADER_AUTHORIZATION);
        if (CommonUtils.isEmpty(authorization)) {
            handleUnAuthorized(auth, request, response, null);
        }
        String[] split = authorization.split(SPLIT_AUTHORIZATION, 2);
        if (split.length == 1 || !AuthConstants.HEADER_BEARER.equalsIgnoreCase(split[0])) {
            handleUnAuthorized(auth, request, response, null);
        }
        String appId = request.getParameter(AuthConstants.PARAM_APP_ID);
        ApplicationDTO app = service.getAndCheck(appId);
        Token token = authorizeHandler.verifyToken(split[1], app);
        if (token == null || CommonUtils.isEmpty(token.getId())) {
            handleUnAuthorized(auth, request, response, app);
        } else {
            // verify token
            String verify = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
            if (CommonUtils.isNotEmpty(auth.roles())) {
                if (CommonUtils.isEmpty(token.getRole())) {
                    handleUnAuthorized(auth, request, response, app);
                }
                String[] roles = token.getRole().split(SPLIT_REG);
                if (!ArrayUtil.containsAny(roles, auth.roles())) {
                    handleUnAuthorized(auth, request, response, app);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
