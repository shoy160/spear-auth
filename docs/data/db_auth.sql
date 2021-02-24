/*
 Navicat Premium Data Transfer

 Source Server         : test-yz
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 60.255.161.101:33061
 Source Schema         : db_auth

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 24/02/2021 14:36:39
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
  `fd_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '认证地址',
  `fd_redirect_login` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录回调',
  `fd_redirect_logout` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登出回调',
  `fd_pool_id` int(11) NOT NULL COMMENT '所属认证池',
  `fd_time_code` int(11) NOT NULL DEFAULT 600 COMMENT '授权码过期时间(秒）',
  `fd_time_access` int(11) NOT NULL DEFAULT 1209600 COMMENT 'access_token过期时间(秒)',
  `fd_time_refresh` int(11) NOT NULL DEFAULT 2592000 COMMENT 'refresh_token过期时间(秒)',
  `fd_time_cookie` int(11) NOT NULL DEFAULT 1209600 COMMENT 'cookie过期时间(秒)',
  `fd_create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `fd_state` tinyint(4) NOT NULL COMMENT '状态',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_grant
-- ----------------------------
DROP TABLE IF EXISTS `t_grant`;
CREATE TABLE `t_grant`  (
  `fd_id` int(11) NOT NULL,
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group`  (
  `fd_id` int(11) NOT NULL,
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_pool
-- ----------------------------
DROP TABLE IF EXISTS `t_pool`;
CREATE TABLE `t_pool`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池ID',
  `fd_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池名称',
  `fd_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份池描述',
  `fd_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份池Logo',
  `fd_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池域名',
  `fd_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池秘钥',
  `fd_user_count` int(11) NOT NULL DEFAULT 0 COMMENT '用户数',
  `fd_state` tinyint(4) NOT NULL COMMENT '状态：1.正常,4.删除',
  `fd_create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `fd_tenant_id` int(11) NOT NULL COMMENT '租户ID',
  `fd_last_regist_time` datetime(0) NULL DEFAULT NULL COMMENT '最后注册时间',
  `fd_last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '认证池表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_pool_config
-- ----------------------------
DROP TABLE IF EXISTS `t_pool_config`;
CREATE TABLE `t_pool_config`  (
  `fd_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份池Id',
  `fd_cors_domain` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安全域',
  `fd_jwt_expired` int(255) NOT NULL DEFAULT 1296000 COMMENT 'JWT有效时间(秒),默认1296000',
  `fd_fail_login_limit` tinyint(1) NOT NULL DEFAULT 0 COMMENT '登录失败次数限制',
  `fd_fail_login_time` int(11) NULL DEFAULT NULL COMMENT '登录失败时间段(秒)',
  `fd_fail_login_count` int(11) NULL DEFAULT NULL COMMENT '登录失败次数',
  `fd_verify_email` tinyint(1) NOT NULL DEFAULT 1 COMMENT '验证邮箱',
  `fd_forbidden_regist` tinyint(1) NOT NULL DEFAULT 0 COMMENT '禁止注册',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '认证池配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_regist_allow
-- ----------------------------
DROP TABLE IF EXISTS `t_regist_allow`;
CREATE TABLE `t_regist_allow`  (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '允许注册' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource`  (
  `fd_id` int(11) NOT NULL,
  `fd_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `fd_id` int(11) NOT NULL,
  PRIMARY KEY (`fd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
