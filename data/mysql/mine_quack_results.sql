/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : ks

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-09-26 11:11:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mine_quack_results`
-- ----------------------------
DROP TABLE IF EXISTS `mine_quack_results`;
CREATE TABLE `mine_quack_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kind` varchar(100) DEFAULT NULL,
  `xData` double DEFAULT NULL,
  `yData` double DEFAULT NULL,
  `zData` double DEFAULT NULL,
  `quackTime` varchar(100) DEFAULT NULL,
  `quackGrade` double DEFAULT NULL,
  `Parrival` double DEFAULT NULL,
  `panfu` varchar(10) DEFAULT NULL,
  `duringGrade` double DEFAULT NULL,
  `nengliang` double DEFAULT NULL,
  `wenjianming` varchar(100) DEFAULT NULL,
  `tensor` double DEFAULT NULL,
  `b_value` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mine_quack_results
-- ----------------------------
