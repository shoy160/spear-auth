package com.yunzhicloud.auth.core;

/**
 * @author shay
 * @date 2021/2/24
 */
public interface AuthConstants {
    String SERVICE_NAME = "business-auth";
    String VERSION = "1.0.0";
    String MAPPER_PACKAGE = "com.yunzhicloud.auth.dao";
    String REST_PACKAGE = "com.yunzhicloud.auth.web.rest";

    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_BEARER = "Bearer";
    String HEADER_POOL_ID = "yz-auth-pool-id";
    String HEADER_APP_ID = "yz-auth-app-id";
    String HEADER_ACCESS_TOKEN = "access-token";

    String PARAM_POOL_ID = "pool_id";
    String PARAM_APP_ID = "app_id";
    String PARAM_CLIENT_ID = "client_id";

    String CLAIM_VERIFY = "verify";
    String CLAIM_USER_ID = "user-id";
    String CLAIM_TENANT_ID = "tenant-id";
    String CLAIM_USERNAME = "user-name";
    String CLAIM_ROLE = "role";

    String SESSION_APP = "auth-app";
    String SESSION_IGNORE_HEADER = "ignore-token";

    String COOKIE_ACCESS_TOKEN = "auth_token";
    String COOKIE_POOL_ID = "auth_pool_id";
}
