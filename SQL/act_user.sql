/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : activiti

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-11-07 16:17:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_user
-- ----------------------------
DROP TABLE IF EXISTS `act_user`;
CREATE TABLE `act_user` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ROLE_ID` bigint(11) DEFAULT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of act_user
-- ----------------------------
INSERT INTO `act_user` VALUES ('1', 'xiangdong', '123456', '2', 'xiangdong');
INSERT INTO `act_user` VALUES ('2', 'admin', '123456', '1', '管理员');
INSERT INTO `act_user` VALUES ('3', 'wangwu', '123456', '2', '王五');
INSERT INTO `act_user` VALUES ('4', 'lisi', '123456', '2', '李四');
INSERT INTO `act_user` VALUES ('5', 'zhangsan', '123456', '2', '张三');
