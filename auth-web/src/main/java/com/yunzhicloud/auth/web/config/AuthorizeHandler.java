package com.yunzhicloud.auth.web.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.core.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.entity.dto.UserDTO;
import com.yunzhicloud.auth.service.ApplicationService;
import com.yunzhicloud.auth.web.utils.CookieUtil;
import com.yunzhicloud.auth.web.vo.AccessTokenVO;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.exception.BusinessException;
import com.yunzhicloud.core.session.YzSession;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.JsonUtils;
import com.yunzhicloud.web.security.AuthContext;
import com.yunzhicloud.web.vo.Token;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author shay
 * @date 2021/2/25
 */
@Component
@AllArgsConstructor
public class AuthorizeHandler {

    private final Cache<String, String> cache;
    private final ApplicationService appService;
    private final YzSession session;

    private final static String CACHE_KEY_VERIFY = "auth:%s:verify:%s:%s";
    private final static String CACHE_KEY_REFRESH = "auth:%s:refresh:%s";
    private final static String CACHE_KEY_CODE = "auth:%s:code:%s";
    private final static String SPLIT_AUTHORIZATION = "\\s";

    public String saveToken(Token token, ApplicationDTO app) {
        String verifyToken = IdUtil.simpleUUID();
        token.setClaimValue(AuthConstants.CLAIM_VERIFY, verifyToken);
        Date expireAt = new Date(System.currentTimeMillis() + app.getTimeAccess() * 1000);
        String accessToken = token.jwt(app.getTokenSecret(), expireAt);
        String cookieName = AuthConstants.COOKIE_ACCESS_TOKEN.concat("-").concat(app.getGroupId());
        CookieUtil.set(cookieName, accessToken, app.getTimeCookie());
        return accessToken;
    }

    public void removeToken(ApplicationDTO app, Token token) {
        if (token == null) {
            return;
        }
        String cookieName = AuthConstants.COOKIE_ACCESS_TOKEN;
        if (app != null) {
            AccessTokenVO tokenVO = currentAccessToken(token, app);
            String groupId = app.getGroupId();
            String verify = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
            String verifyKey = String.format(CACHE_KEY_VERIFY, groupId, token.getId(), verify);
            cache.remove(verifyKey);
            if (tokenVO != null) {
                String refreshKey = String.format(CACHE_KEY_REFRESH, groupId, tokenVO.getRefresh_token());
                cache.remove(refreshKey);
            }
            cookieName = cookieName.concat("-").concat(app.getGroupId());
        }
        CookieUtil.set(cookieName, null, -1);
    }

    public Token verifyToken(String accessToken, String secret, String groupId) {
        if (CommonUtils.isEmpty(accessToken)) {
            return null;
        }
        Token token = Token.verify(accessToken, secret);
        if (token == null || CommonUtils.isEmpty(token.getId())) {
            return null;
        }

        // verify token
        String verify = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
        String verifyKey = String.format(CACHE_KEY_VERIFY, groupId, token.getId(), verify);
        String value = cache.get(verifyKey);
        if (CommonUtils.isEmpty(value)) {
            return null;
        }
        return token;
    }

    public String saveCode(ApplicationDTO app) {
        String code = RandomUtil.randomString(15);
        String groupId = app.getGroupId();
        String key = String.format(CACHE_KEY_CODE, groupId, code);
        cache.put(key, "1", app.getTimeCode(), TimeUnit.SECONDS);
        return code;
    }

    public void checkCode(String groupId, String code) {
        String key = String.format(CACHE_KEY_CODE, groupId, code);
        String accessToken = cache.get(key);
        if (CommonUtils.isNotEmpty(accessToken)) {
            cache.remove(key);
        } else {
            throw new BusinessException("授权码已失效");
        }
    }

    public String saveRefreshToken(Token token, ApplicationDTO app) {
        String refreshToken = RandomUtil.randomString(32);
        String groupId = app.getGroupId();
        String cacheKey = String.format(CACHE_KEY_REFRESH, groupId, refreshToken);
        cache.put(cacheKey, JsonUtils.toJson(token), app.getTimeRefresh(), TimeUnit.SECONDS);
        return refreshToken;
    }

    public Token checkRefreshToken(ApplicationDTO app, String refreshToken) {
        String groupId = app.getGroupId();
        String cacheKey = String.format(CACHE_KEY_REFRESH, groupId, refreshToken);
        String tokenJson = cache.get(cacheKey);
        if (CommonUtils.isEmpty(tokenJson)) {
            return null;
        }
        cache.remove(cacheKey);
        return JsonUtils.json(tokenJson, Token.class);
    }

    public AccessTokenVO generateToken(UserDTO dto, ApplicationDTO app) {
        Token token = new Token();
        token.setId(dto.getId());
        String name = dto.getName();
        if (CommonUtils.isEmpty(name)) {
            name = dto.getAccount();
        }
        token.setName(name);
        token.setRole("");
        return generateToken(token, app);
    }

    public AccessTokenVO generateToken(Token token, ApplicationDTO app) {
        String accessToken = saveToken(token, app);
        AccessTokenVO tokenVO = new AccessTokenVO();
        tokenVO.setAccess_token(accessToken);
        tokenVO.setExpires_in(app.getTimeAccess());
        String refreshToken = saveRefreshToken(token, app);
        tokenVO.setRefresh_token(refreshToken);
        tokenVO.setToken_type(AuthConstants.HEADER_BEARER);
        // todo 获取scope
        tokenVO.setScope("");
        tokenVO.setUser_id(token.getId());
        String verifyToken = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
        String verifyKey = String.format(CACHE_KEY_VERIFY, app.getGroupId(), token.getId(), verifyToken);
        cache.put(verifyKey, JsonUtils.toJson(tokenVO), app.getTimeAccess(), TimeUnit.SECONDS);
        return tokenVO;
    }

    public AccessTokenVO currentAccessToken(Token token, ApplicationDTO app) {
        String verifyToken = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
        String verifyKey = String.format(CACHE_KEY_VERIFY, app.getGroupId(), token.getId(), verifyToken);
        String tokenJson = cache.get(verifyKey);
        if (CommonUtils.isEmpty(tokenJson)) {
            return null;
        }
        return JsonUtils.json(tokenJson, AccessTokenVO.class);
    }

    private String getAccessTokenFromCookie() {
        String groupId = session.tenantIdAsString();
        ApplicationDTO app = currentApp();
        if (app != null) {
            groupId = app.getGroupId();
        }
        String cookieName = AuthConstants.COOKIE_ACCESS_TOKEN.concat("-").concat(groupId);
        return CookieUtil.get(cookieName);
    }

    private String getAccessTokenFromHeader() {
        HttpServletRequest request = AuthContext.getRequest();
        Object ignore = request.getAttribute(AuthConstants.SESSION_IGNORE_HEADER);
        if (ignore != null && ignore.equals(true)) {
            return null;
        }
        String authorization = request.getHeader(AuthConstants.HEADER_AUTHORIZATION);
        if (CommonUtils.isEmpty(authorization)) {
            return null;
        }
        String[] split = authorization.split(SPLIT_AUTHORIZATION, 2);
        if (split.length == 1 || !AuthConstants.HEADER_BEARER.equalsIgnoreCase(split[0])) {
            return null;
        }
        return split[1];
    }

    public String getAccessToken() {
        String accessToken = getAccessTokenFromHeader();
        if (CommonUtils.isEmpty(accessToken)) {
            accessToken = getAccessTokenFromCookie();
        }
        return accessToken;
    }

    public String getAppId() {
        HttpServletRequest request = AuthContext.getRequest();
        String appId = request.getParameter(AuthConstants.PARAM_APP_ID);
        if (CommonUtils.isNotEmpty(appId)) {
            return appId;
        }
        appId = request.getParameter(AuthConstants.PARAM_CLIENT_ID);
        if (CommonUtils.isNotEmpty(appId)) {
            return appId;
        }
        appId = request.getHeader(AuthConstants.HEADER_APP_ID);
        if (CommonUtils.isNotEmpty(appId)) {
            return appId;
        }
        Object value = request.getAttribute(AuthConstants.HEADER_APP_ID);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public ApplicationDTO currentApp() {
        return currentApp(null);
    }

    public ApplicationDTO currentApp(String appId) {
        HttpServletRequest request = AuthContext.getRequest();
        String key = AuthConstants.SESSION_APP;
        if (CommonUtils.isEmpty(appId)) {
            appId = getAppId();
        } else {
            key = key.concat("-").concat(appId);
        }
        Object value = request.getAttribute(key);
        if (value != null) {
            return (ApplicationDTO) value;
        }
        if (CommonUtils.isEmpty(appId)) {
            return null;
        }
        ApplicationDTO app = appService.getAndCheck(appId);
        request.setAttribute(key, app);
        return app;
    }

}
