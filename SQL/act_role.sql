/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : activiti

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-11-07 16:17:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_role
-- ----------------------------
DROP TABLE IF EXISTS `act_role`;
CREATE TABLE `act_role` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of act_role
-- ----------------------------
INSERT INTO `act_role` VALUES ('1', 'ADMIN');
INSERT INTO `act_role` VALUES ('2', 'CUSTOM');
