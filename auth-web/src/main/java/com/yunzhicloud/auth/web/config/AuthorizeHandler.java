package com.yunzhicloud.auth.web.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.yunzhicloud.auth.AuthConstants;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.JsonUtils;
import com.yunzhicloud.web.vo.Token;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

    public String saveToken(Token token, ApplicationDTO app) {
        String verifyToken = IdUtil.fastUUID();
        token.setClaimValue(AuthConstants.CLAIM_VERIFY, verifyToken);
        String verifyKey = String.format(CACHE_KEY_VERIFY, app.getId(), token.getId(), verifyToken);
        cache.put(verifyKey, "1", app.getTimeAccess(), TimeUnit.SECONDS);
        Date expireAt = new Date(System.currentTimeMillis() + app.getTimeAccess() * 1000);
        return token.jwt(app.getSecret(), expireAt);
    }

    public Token verifyToken(String accessToken, ApplicationDTO app) {
        Token token = Token.verify(accessToken, app.getSecret());
        if (token == null || CommonUtils.isEmpty(token.getId())) {
            return null;
        }
        // verify token
        String verify = token.getClaimValue(AuthConstants.CLAIM_VERIFY, String.class);
        String verifyKey = String.format(CACHE_KEY_VERIFY, app.getId(), token.getId(), verify);
        String value = cache.get(verifyKey);
        if (CommonUtils.isEmpty(value)) {
            return null;
        }
        return token;
    }

    public String saveCode(String accessToken, ApplicationDTO app) {
        String code = RandomUtil.randomString(15);
        String key = String.format(CACHE_KEY_CODE, app.getId(), code);
        cache.put(key, accessToken, app.getTimeCode(), TimeUnit.SECONDS);
        return code;
    }

    public String checkCode(String appId, String code) {
        String key = String.format(CACHE_KEY_CODE, appId, code);
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
}
