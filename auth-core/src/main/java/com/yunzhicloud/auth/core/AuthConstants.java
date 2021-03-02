package com.yunzhicloud.auth.core;

/**
 * @author shay
 * @date 2021/2/24
 */
public interface AuthConstants {
    String SERVICE_NAME = "yzcloud-auth";
    String VERSION = "1.0.0";
    String MAPPER_PACKAGE = "com.yunzhicloud.auth.dao";
    String REST_PACKAGE = "com.yunzhicloud.auth.web.rest";

    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_BEARER = "Bearer";
    String HEADER_POOL_ID = "yz-auth-pool-id";
    String PARAM_APP_ID = "app_id";
    String CLAIM_VERIFY = "verify";

    String CLAIM_USER_ID = "user-id";
    String CLAIM_TENANT_ID = "tenant-id";
    String CLAIM_USERNAME = "user-name";
    String CLAIM_ROLE = "role";
    String HEADER_ACCESS_TOKEN = "access-token";

    String SESSION_APP = "auth-app";

}
