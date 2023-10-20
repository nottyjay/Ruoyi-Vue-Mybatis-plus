/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : d3code

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 21/10/2023 01:52:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bpm_form
-- ----------------------------
DROP TABLE IF EXISTS `bpm_form`;
CREATE TABLE `bpm_form`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `conf` json NULL,
  `fields` json NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpm_form
-- ----------------------------

-- ----------------------------
-- Table structure for bpm_process_definition_ext
-- ----------------------------
DROP TABLE IF EXISTS `bpm_process_definition_ext`;
CREATE TABLE `bpm_process_definition_ext`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `process_definition_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `model_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `form_type` int NULL DEFAULT NULL,
  `form_id` int NULL DEFAULT NULL,
  `form_conf` json NULL,
  `form_fields` json NULL,
  `form_custom_create_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `form_custom_view_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpm_process_definition_ext
-- ----------------------------

-- ----------------------------
-- Table structure for bpm_process_instance_ext
-- ----------------------------
DROP TABLE IF EXISTS `bpm_process_instance_ext`;
CREATE TABLE `bpm_process_instance_ext`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `start_user_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process_definition_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `result` int NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `form_variables` json NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpm_process_instance_ext
-- ----------------------------

-- ----------------------------
-- Table structure for bpm_task_assign_rule
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_assign_rule`;
CREATE TABLE `bpm_task_assign_rule`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `model_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process_definition_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `task_definition_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `options` json NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpm_task_assign_rule
-- ----------------------------

-- ----------------------------
-- Table structure for bpm_task_ext
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_ext`;
CREATE TABLE `bpm_task_ext`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `assignee_user_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `result` int NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `process_definition_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpm_task_ext
-- ----------------------------

-- ----------------------------
-- Table structure for bpm_user_group
-- ----------------------------
DROP TABLE IF EXISTS `bpm_user_group`;
CREATE TABLE `bpm_user_group`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `member_user_ids` json NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpm_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1, 'sys_attachment', '附件', NULL, NULL, 'SysAttachment', 'crud', 'com.alphay.boot.attachment', 'attachment', 'attachment', '文件管理', 'd3code', '0', '/', '{\"parentMenuId\":1}', 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55', NULL);
INSERT INTO `gen_table` VALUES (2, 'sys_oss_bucket', '存储桶配置', NULL, NULL, 'SysOssBucket', 'crud', 'com.alphay.boot.attachment', 'bucket', 'bucket', '存储桶', 'd3code', '0', '/', '{\"parentMenuId\":2027}', 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42', NULL);
INSERT INTO `gen_table` VALUES (3, 'sys_oss_config', 'OSS配置', NULL, NULL, 'SysOssConfig', 'crud', 'com.alphay.boot.attachment', 'oss', 'oss_config', '存储配置', 'd3code', '0', '/', '{\"parentMenuId\":2027}', 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52', NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (1, '1', 'id', '序号', 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (2, '1', 'create_time', NULL, 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 2, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (3, '1', 'create_by', '创建人', 'varchar(30)', 'String', 'createBy', '0', '0', NULL, '1', NULL, '1', '1', 'EQ', 'input', '', 3, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (4, '1', 'update_time', NULL, 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 4, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (5, '1', 'update_by', NULL, 'varchar(30)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 5, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (6, '1', 'deleted', NULL, 'tinyint', 'Long', 'deleted', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 6, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (7, '1', 'name', '文件名称', 'varchar(255)', 'String', 'name', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 7, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (8, '1', 'storage_type', '存储方式', 'varchar(30)', 'String', 'storageType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 8, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (9, '1', 'path', '存储路径', 'varchar(1000)', 'String', 'path', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'textarea', '', 9, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (10, '1', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 10, 'admin', '2023-10-12 21:35:36', '', '2023-10-12 21:37:55');
INSERT INTO `gen_table_column` VALUES (11, '2', 'id', '序号', 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (12, '2', 'create_time', NULL, 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 2, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (13, '2', 'create_by', NULL, 'varchar(30)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 3, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (14, '2', 'update_time', NULL, 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 4, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (15, '2', 'update_by', NULL, 'varchar(30)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 5, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (16, '2', 'deleted', NULL, 'tinyint', 'Long', 'deleted', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 6, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (17, '2', 'oss_config_id', '存储方式', 'int', 'Long', 'ossConfigId', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'input', '', 7, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (18, '2', 'bucket', '桶名', 'varchar(100)', 'String', 'bucket', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'input', '', 8, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (19, '2', 'p_id', '上级桶', 'int', 'Long', 'pId', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'input', '', 9, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (20, '2', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 10, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:04:42');
INSERT INTO `gen_table_column` VALUES (21, '3', 'id', '序号', 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (22, '3', 'create_time', NULL, 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 2, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (23, '3', 'create_by', NULL, 'varchar(30)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 3, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (24, '3', 'update_time', NULL, 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 4, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (25, '3', 'update_by', NULL, 'varchar(30)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 5, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (26, '3', 'deleted', NULL, 'tinyint', 'Long', 'deleted', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 6, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (27, '3', 'name', '配置名称', 'varchar(30)', 'String', 'name', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 7, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (28, '3', 'oss_type', '存储类型', 'varchar(30)', 'String', 'ossType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 8, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (29, '3', 'config', '存储内容', 'varchar(1000)', 'String', 'config', '0', '0', NULL, '0', '0', '0', '0', 'EQ', 'textarea', '', 9, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');
INSERT INTO `gen_table_column` VALUES (30, '3', 'remark', '备注', 'varchar(255)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'input', '', 10, 'admin', '2023-10-13 22:59:32', '', '2023-10-13 23:02:52');

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `storage_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `config_id` int NULL DEFAULT NULL,
  `bucket_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `extension` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
INSERT INTO `sys_attachment` VALUES (1, '2023-10-15 20:58:54', NULL, NULL, NULL, 0, '微信截图_20231011171447.png', 'tencent_cos', '2023/10/15/d07be5bb-e953-4d9f-9025-9a7aaf86c2b4_20231015205853A001.png', 'https://default-1319610221.cos.ap-shanghai.myqcloud.com/2023/10/15/d07be5bb-e953-4d9f-9025-9a7aaf86c2b4_20231015205853A001.png', 3, NULL, NULL, NULL);
INSERT INTO `sys_attachment` VALUES (2, '2023-10-15 21:11:09', NULL, NULL, NULL, 0, '微信截图_20231011171447.png', 'tencent_cos', '2023/10/15/84cde012-d258-4dfb-ad17-99361e0e77a7_20231015211108A001.png', 'https://default-1319610221.cos.ap-shanghai.myqcloud.com/2023/10/15/84cde012-d258-4dfb-ad17-99361e0e77a7_20231015211108A001.png', 3, NULL, NULL, NULL);
INSERT INTO `sys_attachment` VALUES (3, '2023-10-20 15:36:18', NULL, NULL, NULL, 0, '微信截图_20231011171447', 'LOCAL', '2023/10/20/548a2093-dce4-4d20-a043-9b87d3836bdf_20231020153617A001.png', '/profile/2023/10/20/548a2093-dce4-4d20-a043-9b87d3836bdf_20231020153617A001.png', 4, '/', NULL, 'png');
INSERT INTO `sys_attachment` VALUES (4, '2023-10-20 16:50:58', NULL, '2023-10-20 20:00:34', NULL, 1, '微信截图_20231011171447', 'LOCAL', '2023/10/20/7bc57b0b-35ea-435d-a8e1-59183577d046_20231020165057A001.png', '/profile/2023/10/20/7bc57b0b-35ea-435d-a8e1-59183577d046_20231020165057A001.png', 4, '/', NULL, 'png');
INSERT INTO `sys_attachment` VALUES (5, '2023-10-20 16:54:21', NULL, '2023-10-20 19:55:44', NULL, 1, '微信截图_20231011171447', 'LOCAL', '2023/10/20/752c7034-efce-4114-b4c0-e9dd25a2d7c7_20231020165421A001.png', '/profile/2023/10/20/752c7034-efce-4114-b4c0-e9dd25a2d7c7_20231020165421A001.png', 4, '/', NULL, 'png');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-05-19 13:04:00', '', '2023-05-19 13:04:00', '初始化密码 123456', 0);
INSERT INTO `sys_config` VALUES (2, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '是否开启验证码功能（true开启，false关闭）', 0);
INSERT INTO `sys_config` VALUES (3, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'true', 'Y', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '是否开启注册用户功能（true开启，false关闭）', 0);
INSERT INTO `sys_config` VALUES (4, '系统主题配置', 'sys.theme.setting', '{\n            \"topNav\":false,\n            \"tagsView\":true,\n            \"fixedHeader\":true,\n            \"sidebarLogo\":true,\n            \"dynamicTitle\":true,\n            \"sideTheme\":\"theme-light\",\n            \"theme\":\"#F53C0D\"\n          }', 'Y', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', NULL, 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_user_id` int NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '安惠科技', 0, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '南通总公司', 1, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '苏州分公司', 2, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-20 11:49:58', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, 1, '15888888888', 'ry@qq.com', '0', 0, 'admin', '2023-05-19 13:04:00', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (200, 100, '0,100', '测试', 3, NULL, '18651373578', '111111@11.com', '0', 1, 'admin', '2023-05-20 11:50:27', '', '2023-10-13 21:11:31', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '停用状态');
INSERT INTO `sys_dict_data` VALUES (30, 0, '默认', '0', 'bpm_model_category', NULL, 'primary', 'Y', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程分类 - 默认');
INSERT INTO `sys_dict_data` VALUES (31, 1, 'OA', '1', 'bpm_model_category', NULL, 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程分类 - OA');
INSERT INTO `sys_dict_data` VALUES (32, 3, '流程发起人的二级领导', '21', 'bpm_task_assign_script', NULL, 'default', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配自定义脚本 - 流程发起人的二级领导');
INSERT INTO `sys_dict_data` VALUES (33, 2, '流程发起人的一级领导', '20', 'bpm_task_assign_script', NULL, 'default', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配自定义脚本 - 流程发起人的一级领导');
INSERT INTO `sys_dict_data` VALUES (34, 1, '流程发起人', '10', 'bpm_task_assign_script', NULL, 'default', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配自定义脚本 - 流程发起人');
INSERT INTO `sys_dict_data` VALUES (35, 1, '角色', '10', 'bpm_task_assign_rule_type', NULL, 'info', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 角色');
INSERT INTO `sys_dict_data` VALUES (36, 2, '部门的成员', '20', 'bpm_task_assign_rule_type', NULL, 'primary', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 部门的成员');
INSERT INTO `sys_dict_data` VALUES (37, 3, '部门的负责人', '21', 'bpm_task_assign_rule_type', NULL, 'primary', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 部门的负责人');
INSERT INTO `sys_dict_data` VALUES (38, 4, '岗位', '22', 'bpm_task_assign_rule_type', NULL, 'success', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 岗位');
INSERT INTO `sys_dict_data` VALUES (39, 5, '用户', '30', 'bpm_task_assign_rule_type', NULL, 'info', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 用户');
INSERT INTO `sys_dict_data` VALUES (40, 6, '用户组', '40', 'bpm_task_assign_rule_type', NULL, 'warning', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 用户组');
INSERT INTO `sys_dict_data` VALUES (41, 7, '自定义脚本', '50', 'bpm_task_assign_rule_type', NULL, 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型 - 自定义脚本');
INSERT INTO `sys_dict_data` VALUES (42, 1, '流程表单', '10', 'bpm_model_form_type', NULL, 'default', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程的表单类型 - 流程表单');
INSERT INTO `sys_dict_data` VALUES (43, 2, '业务表单', '20', 'bpm_model_form_type', NULL, 'default', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程的表单类型 - 业务表单');
INSERT INTO `sys_dict_data` VALUES (44, 0, '处理中', '1', 'bpm_process_instance_result', NULL, 'primary', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的结果 - 处理中');
INSERT INTO `sys_dict_data` VALUES (45, 2, '通过', '2', 'bpm_process_instance_result', NULL, 'success', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的结果 - 通过');
INSERT INTO `sys_dict_data` VALUES (46, 3, '不通过', '3', 'bpm_process_instance_result', NULL, 'danger', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的结果 - 不通过');
INSERT INTO `sys_dict_data` VALUES (47, 4, '已取消', '4', 'bpm_process_instance_result', NULL, 'info', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的结果 - 已取消');
INSERT INTO `sys_dict_data` VALUES (48, 1, '进行中', '1', 'bpm_process_instance_status', NULL, 'primary', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的状态 - 进行中');
INSERT INTO `sys_dict_data` VALUES (49, 2, '已完成', '2', 'bpm_process_instance_status', NULL, 'success', 'N', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的状态 - 已完成');
INSERT INTO `sys_dict_data` VALUES (100, 0, '本地文件', 'local', 'storage_type', NULL, 'info', 'N', '0', 'admin', '2023-10-14 07:53:24', 'admin', '2023-10-14 07:54:39', 0, NULL);
INSERT INTO `sys_dict_data` VALUES (101, 1, '亚马逊S3', 'amazon_s3', 'storage_type', NULL, 'success', 'N', '0', 'admin', '2023-10-14 07:54:05', '', NULL, 0, NULL);
INSERT INTO `sys_dict_data` VALUES (102, 2, '阿里云OSS', 'aliyun_oss', 'storage_type', NULL, 'primary', 'N', '0', 'admin', '2023-10-14 07:54:24', '', NULL, 0, NULL);
INSERT INTO `sys_dict_data` VALUES (103, 3, '腾讯云COS', 'tencent_cos', 'storage_type', NULL, 'danger', 'N', '0', 'admin', '2023-10-14 11:37:02', 'admin', '2023-10-14 11:38:31', 0, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (11, '流程分类', 'bpm_model_category', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程分类列表');
INSERT INTO `sys_dict_type` VALUES (12, '任务分配自定义脚本', 'bpm_task_assign_script', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配自定义脚本列表');
INSERT INTO `sys_dict_type` VALUES (13, '任务分配规则的类型', 'bpm_task_assign_rule_type', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '任务分配规则的类型列表');
INSERT INTO `sys_dict_type` VALUES (14, '流程的表单类型', 'bpm_model_form_type', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程的表单类型列表');
INSERT INTO `sys_dict_type` VALUES (15, '流程实例的结果', 'bpm_process_instance_result', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的结果列表');
INSERT INTO `sys_dict_type` VALUES (16, '流程实例的状态', 'bpm_process_instance_status', '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '流程实例的状态列表');
INSERT INTO `sys_dict_type` VALUES (105, '存储桶方案', 'storage_type', '0', 'admin', '2023-10-14 07:46:44', 'admin', '2023-10-14 07:53:02', 0, '存储桶的解决方案');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2023-05-19 13:04:00', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2023-05-19 13:04:00', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-05-19 13:04:00', '', NULL, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-10-16 10:08:57', '2023-10-16 10:08:57', NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_logininfor` VALUES (2, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-10-16 10:58:01', '2023-10-16 10:58:01', NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_logininfor` VALUES (3, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-10-20 14:51:25', '2023-10-20 14:51:25', NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_logininfor` VALUES (4, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-10-20 16:48:18', '2023-10-20 16:48:18', NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_logininfor` VALUES (5, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-10-20 19:51:06', '2023-10-20 19:51:06', NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件名称',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2040 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 96, NULL, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '', 0);
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 97, NULL, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '', 0);
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 98, NULL, 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '', 0);
INSERT INTO `sys_menu` VALUES (4, '安惠官网', 0, 99, NULL, 'https://www.alphay.com', NULL, '', 0, 0, 'M', '0', '0', '', 'guide', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '', 0);
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, NULL, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2023-05-19 13:04:00', '', NULL, '用户管理菜单', 0);
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, NULL, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2023-05-19 13:04:00', '', NULL, '角色管理菜单', 0);
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, NULL, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2023-05-19 13:04:00', '', NULL, '菜单管理菜单', 0);
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, NULL, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2023-05-19 13:04:00', '', NULL, '部门管理菜单', 0);
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, NULL, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2023-05-19 13:04:00', '', NULL, '岗位管理菜单', 0);
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, NULL, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2023-05-19 13:04:00', '', NULL, '字典管理菜单', 0);
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 10, NULL, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2023-05-19 13:04:00', 'admin', '2023-05-19 13:04:00', '', 0);
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, NULL, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2023-05-19 13:04:00', '', NULL, '通知公告菜单', 0);
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, NULL, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2023-05-19 13:04:00', '', NULL, '日志管理菜单', 0);
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, NULL, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2023-05-19 13:04:00', '', NULL, '在线用户菜单', 0);
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, NULL, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2023-05-19 13:04:00', '', NULL, '定时任务菜单', 0);
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, NULL, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2023-05-19 13:04:00', '', NULL, '数据监控菜单', 0);
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, NULL, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2023-05-19 13:04:00', '', NULL, '服务监控菜单', 0);
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, NULL, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2023-05-19 13:04:00', '', NULL, '缓存监控菜单', 0);
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, NULL, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2023-05-19 13:04:00', '', NULL, '缓存列表菜单', 0);
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, NULL, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2023-05-19 13:04:00', '', NULL, '表单构建菜单', 0);
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, NULL, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2023-05-19 13:04:00', '', NULL, '代码生成菜单', 0);
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, NULL, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2023-05-19 13:04:00', '', NULL, '系统接口菜单', 0);
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, NULL, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2023-05-19 13:04:00', '', NULL, '操作日志菜单', 0);
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, NULL, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2023-05-19 13:04:00', '', NULL, '登录日志菜单', 0);
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, NULL, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, NULL, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2000, '动态表单构建', 3, 4, NULL, 'build2', 'tool/build2/index', NULL, 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2001, '系统设置', 1, 7, NULL, 'setting', 'system/setting/index', NULL, 1, 0, 'C', '0', '0', 'system:config:list', 'system', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2002, '工作流程', 0, 95, NULL, 'flowable', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'tool', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2003, '业务流表单', 2010, 1, NULL, 'bpmForm', 'bpm/bpmForm/index', NULL, 1, 0, 'C', '0', '0', 'bpm:bpmForm:list', 'dict', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2004, '业务流表单查询', 2003, 1, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bpm:bpmForm:query', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2005, '业务流表单新增', 2003, 2, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bpm:bpmForm:add', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2006, '业务流表单修改', 2003, 3, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bpm:bpmForm:edit', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2007, '业务流表单删除', 2003, 4, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bpm:bpmForm:remove', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2008, '业务流表单导出', 2003, 5, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bpm:bpmForm:export', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2009, '流程表单设计', 2010, 6, 'BpmFormDesign', 'design/:id', 'bpm/bpmForm/edit', NULL, 1, 0, 'C', '1', '0', 'bpm:bpmForm:add,bpm:bpmForm:edit', 'build', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2010, '流程管理', 2002, 1, NULL, 'bpm', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'nested', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2011, '流程模型', 2010, 2, NULL, 'model', 'bpm/model/index', NULL, 1, 0, 'C', '0', '0', 'bpm:bpmModel:list', 'guide', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2012, '流程定义', 2010, 7, NULL, 'definition', 'bpm/definition/index', NULL, 1, 0, 'C', '1', '0', 'bpm:definition:list', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2013, '流程设计', 2010, 8, NULL, 'model/design', 'bpm/model/modelEditor', NULL, 1, 0, 'C', '1', '0', 'bpm:bpmModel:design', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2014, '用户分组', 2010, 3, NULL, 'userGroup', 'bpm/group/index', NULL, 1, 0, 'C', '0', '0', 'bpm:userGroup:list', 'peoples', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2015, '任务管理', 2002, 2, NULL, 'task', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'tree-table', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2016, '我的流程', 2015, 1, NULL, 'my', 'bpm/processInstance/index', NULL, 1, 0, 'C', '0', '0', NULL, 'people', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2017, '待办', 2015, 2, NULL, 'todo', 'bpm/task/todo', NULL, 1, 0, 'C', '0', '0', NULL, 'eye-open', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2018, '已完结', 2015, 3, NULL, 'done', 'bpm/task/done', NULL, 1, 0, 'C', '0', '0', NULL, 'eye', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2019, '发起流程', 2015, 4, NULL, 'create', 'bpm/processInstance/create', NULL, 1, 0, 'C', '1', '0', 'bpm:processInstance:create', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2020, '流程信息', 2015, 5, NULL, 'detail', 'bpm/processInstance/detail', NULL, 1, 0, 'C', '1', '0', 'bpm:processInstance:info', '#', 'admin', '2023-05-19 13:04:00', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2021, '文件管理', 2027, 1, 'Attachment', 'attachment', 'attachment/attachment/index', NULL, 1, 0, 'C', '0', '0', 'attachment:attachment:list', 'folder', 'admin', '2023-10-13 21:13:37', 'admin', '2023-10-13 21:34:44', '文件管理菜单', 0);
INSERT INTO `sys_menu` VALUES (2022, '文件管理查询', 2021, 1, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'attachment:attachment:query', '#', 'admin', '2023-10-13 21:13:37', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2023, '文件管理新增', 2021, 2, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'attachment:attachment:add', '#', 'admin', '2023-10-13 21:13:37', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2024, '文件管理修改', 2021, 3, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'attachment:attachment:edit', '#', 'admin', '2023-10-13 21:13:37', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2025, '文件管理删除', 2021, 4, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'attachment:attachment:remove', '#', 'admin', '2023-10-13 21:13:37', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2026, '文件管理导出', 2021, 5, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'attachment:attachment:export', '#', 'admin', '2023-10-13 21:13:37', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2027, '附件管理', 1, 11, NULL, 'attachment', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'excel', 'admin', '2023-10-13 23:01:53', 'admin', '2023-10-13 23:02:03', '', 0);
INSERT INTO `sys_menu` VALUES (2028, '存储配置', 2027, 1, NULL, 'oss_config', 'attachment/oss_config/index', NULL, 1, 0, 'C', '0', '0', 'oss:oss_config:list', 'oss', 'admin', '2023-10-13 23:07:18', 'admin', '2023-10-16 11:30:29', '存储配置菜单', 0);
INSERT INTO `sys_menu` VALUES (2029, '存储配置查询', 2028, 1, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'oss:oss_config:query', '#', 'admin', '2023-10-13 23:07:18', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2030, '存储配置新增', 2028, 2, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'oss:oss_config:add', '#', 'admin', '2023-10-13 23:07:18', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2031, '存储配置修改', 2028, 3, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'oss:oss_config:edit', '#', 'admin', '2023-10-13 23:07:18', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2032, '存储配置删除', 2028, 4, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'oss:oss_config:remove', '#', 'admin', '2023-10-13 23:07:18', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2033, '存储配置导出', 2028, 5, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'oss:oss_config:export', '#', 'admin', '2023-10-13 23:07:18', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2034, '存储桶', 2027, 1, NULL, 'bucket', 'attachment/bucket/index', NULL, 1, 0, 'C', '0', '0', 'bucket:bucket:list', 'bitbucket', 'admin', '2023-10-13 23:07:33', 'admin', '2023-10-16 11:30:18', '存储桶菜单', 0);
INSERT INTO `sys_menu` VALUES (2035, '存储桶查询', 2034, 1, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bucket:bucket:query', '#', 'admin', '2023-10-13 23:07:33', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2036, '存储桶新增', 2034, 2, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bucket:bucket:add', '#', 'admin', '2023-10-13 23:07:33', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2037, '存储桶修改', 2034, 3, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bucket:bucket:edit', '#', 'admin', '2023-10-13 23:07:33', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2038, '存储桶删除', 2034, 4, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bucket:bucket:remove', '#', 'admin', '2023-10-13 23:07:33', '', NULL, '', 0);
INSERT INTO `sys_menu` VALUES (2039, '存储桶导出', 2034, 5, NULL, '#', '', NULL, 1, 0, 'F', '0', '0', 'bucket:bucket:export', '#', 'admin', '2023-10-13 23:07:33', '', NULL, '', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2023-05-19 13:04:00', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2023-05-19 13:04:00', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oss_bucket
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_bucket`;
CREATE TABLE `sys_oss_bucket`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `oss_config_id` int NULL DEFAULT NULL,
  `bucket` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `p_id` int NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss_bucket
-- ----------------------------
INSERT INTO `sys_oss_bucket` VALUES (1, '2023-10-20 15:04:41', NULL, NULL, NULL, 0, 4, NULL, NULL, NULL);
INSERT INTO `sys_oss_bucket` VALUES (2, '2023-10-20 15:15:44', NULL, NULL, NULL, 0, 4, '/', NULL, NULL);

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `oss_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `config` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
INSERT INTO `sys_oss_config` VALUES (3, '2023-10-14 22:04:10', 'admin', '2023-10-20 17:21:51', 'admin', 0, '腾讯云测试', 'tencent_cos', '{\"appId\":\"1319610221\",\"region\":\"ap-shanghai\",\"secretId\":\"AKIDGPNSL1WnskPr7IH5g4GEpCMI0UlnaAWB\",\"secretKey\":\"d1CAuPgjaMxZ0FJCvVv64pRs8D8sOgmd\",\"bucketName\":\"default\"}', NULL, '1');
INSERT INTO `sys_oss_config` VALUES (4, '2023-10-20 14:53:20', 'admin', '2023-10-20 19:51:29', 'admin', 0, '本地存储', 'local', '{\"domain\":\"http://localhost:8080\",\"bucketName\":\"/\",\"filePath\":\"./uploadPath\"}', NULL, '0');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', 'admin', '2023-05-19 13:04:00', '', NULL, 0, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', 'admin', '2023-05-19 13:04:00', 'admin', '2023-10-20 20:34:56', 0, '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `deleted` tinyint NULL DEFAULT NULL COMMENT '删除标志',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '2023-05-19 13:04:00', 'admin', '2023-10-20 19:51:06', '', 0, 103, 'admin', '安惠科技', '00', 'ah@q163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '127.0.0.1', '2023-10-20 19:51:06', '管理员');
INSERT INTO `sys_user` VALUES (2, '2023-05-19 13:04:00', 'admin', NULL, '', 0, 105, 'ah', '安惠生物', '00', 'ah@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '127.0.0.1', '2023-05-19 13:04:00', '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for test_table
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of test_table
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
