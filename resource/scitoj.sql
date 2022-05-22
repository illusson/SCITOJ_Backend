/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100607
 Source Host           : localhost:3306
 Source Schema         : scitoj

 Target Server Type    : MariaDB
 Target Server Version : 100607
 File Encoding         : 65001

 Date: 22/05/2022 11:47:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class_chart
-- ----------------------------
DROP TABLE IF EXISTS `class_chart`;
CREATE TABLE `class_chart`  (
  `f_id` smallint(6) NOT NULL,
  `s_id` smallint(6) NOT NULL,
  `c_id` tinyint(4) NOT NULL,
  `grade` int(10) NOT NULL,
  `c_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`f_id`, `s_id`, `c_id`, `grade`) USING BTREE,
  UNIQUE INDEX `class_chart`(`c_name`, `f_id`, `s_id`, `c_id`, `grade`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for contest_problem
-- ----------------------------
DROP TABLE IF EXISTS `contest_problem`;
CREATE TABLE `contest_problem`  (
  `con_id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL,
  PRIMARY KEY (`con_id`, `p_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contests
-- ----------------------------
DROP TABLE IF EXISTS `contests`;
CREATE TABLE `contests`  (
  `con_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `con_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `con_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `con_start_time` datetime NULL DEFAULT NULL,
  `con_duration` time NULL DEFAULT NULL,
  PRIMARY KEY (`con_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for faculty_chart
-- ----------------------------
DROP TABLE IF EXISTS `faculty_chart`;
CREATE TABLE `faculty_chart`  (
  `f_id` smallint(6) NOT NULL,
  `f_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  UNIQUE INDEX `faculty_chart`(`f_id`, `f_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for problem_detail
-- ----------------------------
DROP TABLE IF EXISTS `problem_detail`;
CREATE TABLE `problem_detail`  (
  `p_id` int(10) UNSIGNED NOT NULL,
  `p_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `p_sample` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `p_hint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_submition
-- ----------------------------
DROP TABLE IF EXISTS `problem_submition`;
CREATE TABLE `problem_submition`  (
  `sub_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `u_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `p_id` int(11) UNSIGNED NOT NULL,
  `sub_code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sub_code_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sub_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_tag_chart
-- ----------------------------
DROP TABLE IF EXISTS `problem_tag_chart`;
CREATE TABLE `problem_tag_chart`  (
  `t_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `t_name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_tags
-- ----------------------------
DROP TABLE IF EXISTS `problem_tags`;
CREATE TABLE `problem_tags`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_id` int(10) UNSIGNED NOT NULL,
  `t_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `p_id`(`p_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problem_test_case
-- ----------------------------
DROP TABLE IF EXISTS `problem_test_case`;
CREATE TABLE `problem_test_case`  (
  `p_id` int(10) UNSIGNED NOT NULL,
  `p_case_id` int(11) NOT NULL,
  `p_input` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `p_output` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`p_id`, `p_case_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for problems
-- ----------------------------
DROP TABLE IF EXISTS `problems`;
CREATE TABLE `problems`  (
  `p_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `p_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `p_sample` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `p_hint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `p_from` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `p_create_time` datetime NOT NULL,
  `p_create_user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `p_edit_user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `p_edit_time` datetime NULL DEFAULT NULL,
  `p_show_guest` tinyint(1) NOT NULL DEFAULT 1,
  `p_show_public` tinyint(1) NOT NULL DEFAULT 0,
  `p_daily` date NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE,
  UNIQUE INDEX `problem`(`p_id`, `p_create_time`, `p_create_user`, `p_edit_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for specialty_chart
-- ----------------------------
DROP TABLE IF EXISTS `specialty_chart`;
CREATE TABLE `specialty_chart`  (
  `s_id` smallint(6) NOT NULL,
  `s_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `f_id` smallint(6) NOT NULL,
  PRIMARY KEY (`s_id`, `f_id`) USING BTREE,
  INDEX `specialty_chart`(`s_name`, `s_id`, `f_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `u_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `u_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `u_name` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `u_identify` tinyint(2) NOT NULL DEFAULT 0,
  `u_role` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 0,
  `u_faculty` smallint(6) NOT NULL DEFAULT 0,
  `u_specialty` smallint(6) NOT NULL DEFAULT 0,
  `u_class` tinyint(4) NOT NULL DEFAULT 0,
  `u_grade` smallint(6) NOT NULL DEFAULT 0,
  `u_info_expired` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`u_id`) USING BTREE,
  UNIQUE INDEX `user_info`(`u_id`, `u_identify`, `u_faculty`, `u_specialty`, `u_class`, `u_grade`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_session
-- ----------------------------
DROP TABLE IF EXISTS `user_session`;
CREATE TABLE `user_session`  (
  `u_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `u_password` varchar(600) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `u_session` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `u_route` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `u_session_expired` int(11) NOT NULL,
  `u_cookie` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `u_cookie_expired` int(10) NOT NULL,
  `u_token_effective` tinyint(1) NOT NULL,
  PRIMARY KEY (`u_id`) USING BTREE,
  UNIQUE INDEX `user_token`(`u_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
