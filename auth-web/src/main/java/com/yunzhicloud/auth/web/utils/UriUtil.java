package com.yunzhicloud.auth.web.utils;

import cn.hutool.core.util.URLUtil;
import com.yunzhicloud.auth.entity.dto.ApplicationDTO;
import com.yunzhicloud.auth.web.config.AuthProperties;
import com.yunzhicloud.core.utils.CommonUtils;
import com.yunzhicloud.core.utils.MapUtils;
import com.yunzhicloud.web.security.AuthContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shay
 * @date 2021/2/24
 */
public final class UriUtil {
    private final static String QUERY_START = "?";
    private final static String QUERY_START_REG = "\\?";
    private final static String QUERY_CONCAT = "&";
    private final static String QUERY_EQUALS = "=";

    private static Map<String, Object> getParameters(String query) {
        Map<String, Object> map = new HashMap<>();
        String[] params = query.split(QUERY_CONCAT);
        for (String str : params) {
            String[] items = str.split(QUERY_EQUALS, 2);
            map.put(items[0], URLUtil.decode(items[1]));
        }
        return map;
    }

    public static String buildParams(String uri, Map<String, String[]> params) {
        Map<String, Object> map = new HashMap<>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (CommonUtils.isEmpty(values)) {
                continue;
            }
            if (values.length == 1) {
                map.put(key, values[0]);
            } else {
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    map.put(String.format("%s[%d]", key, i), value);
                }
            }
        }
        return build(uri, map);
    }

    public static String build(String uri, Map<String, Object> params) {
        if (CommonUtils.isEmpty(uri) || CommonUtils.isEmpty(params)) {
            return uri;
        }
        Map<String, Object> sourceMap;
        String[] array = uri.split(QUERY_START_REG, 2);
        if (array.length == 1) {
            sourceMap = new HashMap<>();
        } else {
            sourceMap = getParameters(array[1]);
        }
        for (String key : params.keySet()) {
            if (sourceMap.containsKey(key)) {
                sourceMap.replace(key, params.get(key));
            } else {
                sourceMap.put(key, params.get(key));
            }
        }
        String query = MapUtils.toUrl(sourceMap, "utf-8", false);
        return array[0].concat(QUERY_START).concat(query);
    }

    public static Map<String, Object> getParams(String uri) {
        Map<String, Object> map = new HashMap<>();
        if (CommonUtils.isEmpty(uri)) {
            return map;
        }
        String[] array = uri.split(QUERY_START_REG, 2);
        if (array.length == 1) {
            return map;
        }
        return getParameters(array[1]);
    }

    public static String currentUrl() {
        HttpServletRequest request = AuthContext.getRequest();
        if (request == null) {
            return null;
        }
        String uri = request.getRequestURL().toString();
        Map<String, String[]> map = request.getParameterMap();
        return UriUtil.buildParams(uri, map);
    }

    public static void redirectToLogin(AuthProperties config, ApplicationDTO app) {
        HttpServletResponse response = AuthContext.getResponse();
        //跳转
        String loginSite = config.getLoginSite();
        Map<String, Object> map = new HashMap<>();
        map.put("redirect_uri", currentUrl());
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
