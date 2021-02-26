package com.yunzhicloud.auth;

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
    String HEADER_BEARER = "bearer";
    String PARAM_APP_ID = "app_id";
    String CLAIM_VERIFY = "verify";
}
