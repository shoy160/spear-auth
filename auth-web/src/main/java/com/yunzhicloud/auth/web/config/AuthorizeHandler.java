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
import com.yunzhicloud.core.lang.Func;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.JsonUtils;
import com.yunzhicloud.web.security.AuthContext;
import com.yunzhicloud.web.vo.Token;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

    private final static String CACHE_KEY_VERIFY = "auth:verify:%s:%s:%s";
    private final static String CACHE_KEY_REFRESH = "auth:refresh:%s:%s";
    private final static String CACHE_KEY_CODE = "auth:code:%s:%s";
    private final static String COOKIE_NAME = "auth_token";
    private final static String SPLIT_AUTHORIZATION = "\\s";

    public String saveToken(Token token, ApplicationDTO app) {
        String verifyToken = IdUtil.simpleUUID();
        token.setClaimValue(AuthConstants.CLAIM_VERIFY, verifyToken);
        String verifyKey = String.format(CACHE_KEY_VERIFY, app.getPoolId(), token.getId(), verifyToken);
        cache.put(verifyKey, "1", app.getTimeAccess(), TimeUnit.SECONDS);
        Date expireAt = new Date(System.currentTimeMillis() + app.getTimeAccess() * 1000);
        String accessToken = token.jwt(app.getPoolSecret(), expireAt);
        CookieUtil.set(COOKIE_NAME, accessToken, app.getTimeCookie());
        return accessToken;
    }

    public void removeToken(String appId, Token token) {
        if (token == null) {
            return;
        }
        if (CommonUtils.isNotEmpty(appId)) {
            String verify = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
            String verifyKey = String.format(CACHE_KEY_VERIFY, appId, token.getId(), verify);
            cache.remove(verifyKey);
        }
        CookieUtil.set(COOKIE_NAME, null, -1);
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

    public String saveCode(String accessToken, ApplicationDTO app) {
        String code = RandomUtil.randomString(15);
        String key = String.format(CACHE_KEY_CODE, app.getPoolId(), code);
        cache.put(key, accessToken, app.getTimeCode(), TimeUnit.SECONDS);
        return code;
    }

    public String checkCode(String groupId, String code) {
        String key = String.format(CACHE_KEY_CODE, groupId, code);
        String accessToken = cache.get(key);
        if (CommonUtils.isNotEmpty(accessToken)) {
            cache.remove(key);
        }
        return accessToken;
    }

    public String saveRefreshToken(Token token, ApplicationDTO app) {
        String refreshToken = RandomUtil.randomString(32);
        String cacheKey = String.format(CACHE_KEY_REFRESH, app.getId(), refreshToken);
        cache.put(cacheKey, JsonUtils.toJson(token), app.getTimeRefresh(), TimeUnit.SECONDS);
        return refreshToken;
    }

    public Token checkRefreshToken(String appId, String refreshToken) {
        String cacheKey = String.format(CACHE_KEY_REFRESH, appId, refreshToken);
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
        tokenVO.setScope("");
        tokenVO.setUser_id(token.getId());
        return tokenVO;
    }

    private String getAccessTokenFromCookie() {
        return CookieUtil.get(AuthConstants.COOKIE_ACCESS_TOKEN);
    }

    private String getAccessTokenFromHeader() {
        HttpServletRequest request = AuthContext.getRequest();
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

    public ApplicationDTO currentApp(String appId, Func<ApplicationDTO, String> appFunc) {
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
        ApplicationDTO app = appFunc.invoke(appId);
        request.setAttribute(key, app);
        return app;
    }

}
