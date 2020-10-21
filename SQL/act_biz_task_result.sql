/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : activiti

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-11-07 16:16:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_biz_task_result
-- ----------------------------
DROP TABLE IF EXISTS `act_biz_task_result`;
CREATE TABLE `act_biz_task_result` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `BIZ_ID` bigint(11) DEFAULT NULL,
  `PROC_ID` varchar(11) DEFAULT NULL,
  `TASK_ID` varchar(11) DEFAULT NULL,
  `TASK_NAME` varchar(255) DEFAULT NULL,
  `ASSIGNEE` varchar(255) DEFAULT NULL,
  `DESCRIBTION` varchar(255) DEFAULT NULL,
  `CREATE_TIME` timestamp NULL DEFAULT NULL,
  `IS_AGREE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of act_biz_task_result
-- ----------------------------
