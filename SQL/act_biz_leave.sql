/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : activiti

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-11-07 16:17:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_biz_leave
-- ----------------------------
DROP TABLE IF EXISTS `act_biz_leave`;
CREATE TABLE `act_biz_leave` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_TIME` timestamp NULL DEFAULT NULL,
  `DAYS` bigint(255) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `REASON` varchar(255) DEFAULT NULL,
  `UPDATOR` varchar(255) DEFAULT NULL,
  `UPDATE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of act_biz_leave
-- ----------------------------
INSERT INTO `act_biz_leave` VALUES ('1', '2017-11-06 17:20:10', '2', '2017-11-01 00:00:00', '2017-11-02 00:00:00', 'dadadada', null, '2017-11-06 17:20:10');
