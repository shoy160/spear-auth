/*
 Navicat Premium Data Transfer

 Source Server         : business-center
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 192.168.2.211:32498
 Source Schema         : db_auth

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 20/10/2021 15:09:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_application
-- ----------------------------
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'App ID',
  `fd_secret` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'App Secret',
  `fd_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `fd_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用Logo',
  `fd_domain` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '认证地址',
  `fd_redirect_login` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录回调',
  `fd_redirect_logout` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登出回调',
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属认证池',
  `fd_access_state` tinyint(1) NOT NULL DEFAULT 1 COMMENT '访问控制：1.允许所有用户访问，2.拒绝所有用户访问',
  `fd_time_code` int(11) NOT NULL DEFAULT 600 COMMENT '授权码过期时间(秒）',
  `fd_time_access` int(11) NOT NULL DEFAULT 1209600 COMMENT 'access_token过期时间(秒)',
  `fd_time_refresh` int(11) NOT NULL DEFAULT 2592000 COMMENT 'refresh_token过期时间(秒)',
  `fd_time_cookie` int(11) NOT NULL DEFAULT 1209600 COMMENT 'cookie过期时间(秒)',
  `fd_state` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_application
-- ----------------------------
INSERT INTO `t_application` VALUES ('148c089410c21177', 'vlk90h1tlz6oiefv', '默认应用', 'https://file-hz-normal.raveland.club/image/raveland.png', NULL, 'http://127.0.0.1:8080', NULL, '148c083380021192', 1, 600, 1209600, 2592000, 1209600, 1, 0, '2021-10-20 06:00:16', '2021-10-20 06:57:58');

-- ----------------------------
-- Table structure for t_grant
-- ----------------------------
DROP TABLE IF EXISTS `t_grant`;
CREATE TABLE `t_grant`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池ID',
  `fd_namespace` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '命名空间(分组)',
  `fd_type` tinyint(4) NOT NULL COMMENT '授权类型',
  `fd_target_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权对象',
  `fd_target_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权对象名称',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_grant
-- ----------------------------

-- ----------------------------
-- Table structure for t_grant_policy
-- ----------------------------
DROP TABLE IF EXISTS `t_grant_policy`;
CREATE TABLE `t_grant_policy`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_grant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权ID',
  `fd_resource_type` tinyint(4) NOT NULL COMMENT '资源类型',
  `fd_resource_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源ID',
  `fd_resource_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `fd_policy` tinyint(4) NOT NULL COMMENT '授权策略：1.允许，2.拒绝',
  `fd_actions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权操作',
  `fd_condition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权条件',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授权策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_grant_policy
-- ----------------------------

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池Id',
  `fd_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称',
  `fd_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组标识',
  `fd_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组描述',
  `fd_type` tinyint(4) NOT NULL COMMENT '分组类型：1.用户分组,2.权限分组',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_group
-- ----------------------------

-- ----------------------------
-- Table structure for t_login
-- ----------------------------
DROP TABLE IF EXISTS `t_login`;
CREATE TABLE `t_login`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `fd_app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池ID',
  `fd_access_token` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AccessToken',
  `fd_refresh_token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RefreshToken',
  `fd_user_agent` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fd_expired_date` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `fd_expired_refresh_date` datetime(0) NULL DEFAULT NULL COMMENT 'RefreshToken过期时间',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_login
-- ----------------------------

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `fd_app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用ID',
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池ID',
  `fd_user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'UserAgent',
  `fd_ip_addr` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `fd_state` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1.成功,4.失败',
  `fd_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_pool
-- ----------------------------
DROP TABLE IF EXISTS `t_pool`;
CREATE TABLE `t_pool`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池ID',
  `fd_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池秘钥',
  `fd_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池名称',
  `fd_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池编码',
  `fd_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份池描述',
  `fd_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份池Logo',
  `fd_domain` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池域名',
  `fd_user_count` int(11) NOT NULL DEFAULT 0 COMMENT '用户数',
  `fd_state` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1.正常,4.删除',
  `fd_tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户ID(所属用户ID)',
  `fd_last_register_time` datetime(0) NULL DEFAULT NULL COMMENT '最后注册时间',
  `fd_last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `fd_enable_sso` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启单点登录',
  `fd_cors_domain` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安全域',
  `fd_jwt_expired` int(255) NOT NULL DEFAULT 1296000 COMMENT 'JWT有效时间(秒),默认1296000',
  `fd_fail_login_limit` tinyint(1) NOT NULL DEFAULT 0 COMMENT '登录失败次数限制',
  `fd_fail_login_time` int(11) NULL DEFAULT NULL COMMENT '登录失败时间段(秒)',
  `fd_fail_login_count` int(11) NULL DEFAULT NULL COMMENT '登录失败次数',
  `fd_verify_rule` tinyint(4) NOT NULL DEFAULT 0 COMMENT '验证规则：1.邮箱验证，2.手机验证',
  `fd_register_rule` tinyint(4) NOT NULL DEFAULT 0 COMMENT '注册控制：1.开启允许策略,2.开启禁止策略,4.禁止注册',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '认证池表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_pool
-- ----------------------------
INSERT INTO `t_pool` VALUES ('148c083380021192', 'hc0kdrja098i5kph', '默认用户池', 'auth', NULL, NULL, 'auth.raveland.club', 0, 1, '1b0963011b0206b', NULL, NULL, 0, NULL, 1296000, 0, NULL, NULL, 0, 0, 0, '2021-10-20 05:56:18', '2021-10-20 06:04:50');

-- ----------------------------
-- Table structure for t_register_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_register_rule`;
CREATE TABLE `t_register_rule`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池',
  `fd_type` tinyint(4) NOT NULL COMMENT '类型：1.邮箱，2.手机号，3.用户名',
  `fd_rule` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '允许注册' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_register_rule
-- ----------------------------

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池ID',
  `fd_namespace` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '命名空间(分组)',
  `fd_type` tinyint(4) NOT NULL COMMENT '类型',
  `fd_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `fd_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `fd_icon` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `fd_remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `fd_actions` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作列表',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_resource
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池ID',
  `fd_namespace` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '命名空间(分组)',
  `fd_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `fd_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色备注',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fd_pool_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户池ID',
  `fd_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `fd_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `fd_nick` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `fd_avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `fd_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `fd_email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `fd_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `fd_password_salt` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码盐',
  `fd_gender` tinyint(4) NULL DEFAULT NULL COMMENT '性别：1.男，2.女',
  `fd_verify_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '验证类型：0.未验证,1.手机验证,2.邮箱验证',
  `fd_register_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '注册方式：0.手动添加,1.手机号码注册,2.邮箱注册',
  `fd_last_login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `fd_last_login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `fd_login_count` int(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
  `fd_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `fd_create_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `fd_update_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1b0963011b0206b', '148c083380021192', '管理员', 'shoy', 'shoy', NULL, '18111111111', 'shoy160@qq.com', '9C2D135364516D8E773A9EE9EC331531', '123456', 1, 0, 0, '2021-10-20 15:04:49', '192.168.2.252', 4, 0, '2021-10-20 05:57:25', '2021-10-20 07:06:50');

SET FOREIGN_KEY_CHECKS = 1;
