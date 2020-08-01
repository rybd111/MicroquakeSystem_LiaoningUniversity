/*
Navicat MySQL Data Transfer

Source Server         : KS
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : ks

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-08-01 00:43:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mine_quack_5_results`
-- ----------------------------
DROP TABLE IF EXISTS `mine_quack_5_results`;
CREATE TABLE `mine_quack_5_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `xData` double DEFAULT NULL,
  `yData` double DEFAULT NULL,
  `zData` double DEFAULT NULL,
  `quackTime` datetime DEFAULT NULL,
  `quackGrade` double DEFAULT NULL,
  `Parrival` double DEFAULT NULL,
  `panfu` varchar(10) DEFAULT NULL,
  `duringGrade` double DEFAULT NULL,
  `nengliang` double DEFAULT NULL,
  `wenjianming` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mine_quack_5_results
-- ----------------------------
INSERT INTO `mine_quack_5_results` VALUES ('16', '41511450.301', '4600658.904', '141785.828', '2019-12-08 09:35:23', '1.18', null, 'zyuvw', '0', '0', 'D:/data/ConstructionData/5moti/z/zyuvw2019-12-08093524');
INSERT INTO `mine_quack_5_results` VALUES ('17', '41511897.044', '4600274.708', '65469.24', '2019-12-08 09:35:36', '1.1', null, 'zyuvw', '0', '0', 'D:/data/ConstructionData/5moti/z/zyuvw2019-12-08093533');
INSERT INTO `mine_quack_5_results` VALUES ('18', '41511450.301', '4600658.904', '141785.828', '2019-12-09 16:36:13', '1.3', null, 'zyuvw', '0', '0', 'D:/data/ConstructionData/5moti/z/zyuvw2019-12-09163611');
INSERT INTO `mine_quack_5_results` VALUES ('19', '41513383.388', '4614568.013', '-8041849.138', '2019-12-28 02:11:07', '2', null, 'zywsx', '0', '0', 'D:/data/ConstructionData/5moti/zywsx 2019-12-28021103.csv');
INSERT INTO `mine_quack_5_results` VALUES ('20', '41518596.483', '4597490.943', '-174969.077', '2019-12-28 11:20:55', '1.99', null, 'zywsx', '0', '0', 'D:/data/ConstructionData/5moti/zywsx 2019-12-28112052.csv');
INSERT INTO `mine_quack_5_results` VALUES ('21', '41516803.626', '4596693.337', '36216.929', '2019-12-30 05:15:12', '1.8', null, 'zywsx', '0', '0', 'D:/data/ConstructionData/5moti/zywsx 2019-12-30051508.csv');
INSERT INTO `mine_quack_5_results` VALUES ('22', '41517705.107', '4601099.505', '-560956.88', '2019-12-30 09:05:51', '2.28', null, 'zywsx', '0', '0', 'D:/data/ConstructionData/5moti/zywsx 2019-12-30090548.csv');
INSERT INTO `mine_quack_5_results` VALUES ('31', '41512441.065', '4602019.319', '-1573317.747', '2020-02-14 13:59:28', '2.04', null, '1234567', '0', '0', 'D:/data/ConstructionData/5moti/1234567 2020-02-14 13-59-34`49.csv');
INSERT INTO `mine_quack_5_results` VALUES ('34', '41515631.359', '4597580.865', '1155753.778', '2020-03-16 08:29:27', '1.45', null, 'stzuw', '0', '10000000000', 'D:/data/ConstructionData/5moti/stzuw 2020-03-16 08-29-27`69.csv');
INSERT INTO `mine_quack_5_results` VALUES ('35', '41516959.03', '4598034.341', '766129.493', '2020-05-20 13:52:54', '2.53', '-0.9953235742551336', '124637', '0', '14949000', '');
INSERT INTO `mine_quack_5_results` VALUES ('36', '41516959.03', '4598034.341', '766129.493', '2020-05-20 13:52:54', '2.53', '-0.9953235742551336', '124637', '0', '14949000', 'D:/data/ConstructionData/5moti/124637 2020-05-20 13-52-55`14.csv');
INSERT INTO `mine_quack_5_results` VALUES ('37', '41527188.901', '4585867.6', '339280.29', '2020-05-01 09:33:18', '1.86', '6.44670759249999', '162534', '0', '6630200', 'D:/data/ConstructionData/5moti/162534 2020-05-01 09-33-12`3.csv');
INSERT INTO `mine_quack_5_results` VALUES ('38', '41518814.137', '4596339.027', '396644.838', '2020-05-01 09:33:14', '1.85', '-1.2879433692079685', '25613', '0', '6447900', 'D:/data/ConstructionData/5moti/25613 2020-05-01 09-33-15`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('39', '41527188.901', '4585867.6', '339280.29', '2020-05-01 09:33:18', '1.86', '6.44670759249999', '162534', '0', '6630200', 'D:/data/ConstructionData/5moti/162534 2020-05-01 09-33-12`3.csv');
INSERT INTO `mine_quack_5_results` VALUES ('40', '41518814.137', '4596339.027', '396644.838', '2020-05-01 09:33:14', '1.85', '-1.2879433692079685', '25613', '0', '6447900', 'D:/data/ConstructionData/5moti/25613 2020-05-01 09-33-15`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('41', '41527188.901', '4585867.6', '339280.29', '2020-05-01 09:33:18', '1.86', '6.44670759249999', '162534', '0', '6630200', 'D:/data/ConstructionData/5moti/162534 2020-05-01 09-33-12`3.csv');
INSERT INTO `mine_quack_5_results` VALUES ('42', '41518814.137', '4596339.027', '396644.838', '2020-05-01 09:33:14', '1.85', '-1.2879433692079685', '25613', '0', '6447900', 'D:/data/ConstructionData/5moti/25613 2020-05-01 09-33-15`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('43', '41520029.953', '4595909.918', '-1683340.305', '2020-05-01 09:33:12', '1.86', '0.45611621523080803', 'sztwuv', '0', '6630200', 'D:/data/ConstructionData/5moti/sztwuv 2020-05-01 09-33-12`3.csv');
INSERT INTO `mine_quack_5_results` VALUES ('44', '41519440.493', '4596321.223', '-1337975.102', '2020-05-01 09:33:14', '1.85', '-0.296901011551591', 'twzsu', '0', '6447900', 'D:/data/ConstructionData/5moti/twzsu 2020-05-01 09-33-15`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('45', '41520029.953', '4595909.918', '-1683340.305', '2020-05-01 09:33:12', '1.86', '0.45611621523080803', 'sztwuv', '0', '6630200', 'D:/data/ConstructionData/5moti/sztwuv 2020-05-01 09-33-12`3.csv');
INSERT INTO `mine_quack_5_results` VALUES ('46', '41519440.493', '4596321.223', '-1337975.102', '2020-05-01 09:33:14', '1.85', '-0.296901011551591', 'twzsu', '0', '6447900', 'D:/data/ConstructionData/5moti/twzsu 2020-05-01 09-33-15`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('47', '41517405.437', '4598052.7', '17214.344', '2020-02-14 13:59:33', '2.06', '-0.7275462438512359', 'sytwzxu', '0', '144800000', 'D:/data/ConstructionData/5moti/sytwzxu 2020-02-14 13-59-34`42.csv');
INSERT INTO `mine_quack_5_results` VALUES ('48', '41518554.193', '4596202.719', '484532.824', '2020-02-14 13:59:41', '1.82', '0.50404270499255', 'wyusxzt', '0', '3407500', 'D:/data/ConstructionData/5moti/wyusxzt 2020-02-14 13-59-41`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('49', '41517197.518', '4598331.22', '62268.363', '2020-02-14 13:59:33', '2.04', '-0.9049629552637537', 'sytzxuw', '0', '144800000', 'D:/data/ConstructionData/5moti/sytzxuw 2020-02-14 13-59-34`42.csv');
INSERT INTO `mine_quack_5_results` VALUES ('50', '41518499.593', '4596235.509', '570984.033', '2020-02-14 13:59:41', '1.82', '0.7114817369749139', 'wyusxzt', '0', '3407500', 'D:/data/ConstructionData/5moti/wyusxzt 2020-02-14 13-59-41`09.csv');
INSERT INTO `mine_quack_5_results` VALUES ('51', '41517405.437', '4598052.7', '17214.344', '2020-02-14 13:59:33', '2.06', '-0.7275462438512359', 'sytwzxu', '0', '144800000', 'D:/data/ConstructionData/5moti/sytwzxu 2020-02-14 13-59-34`42.csv');
INSERT INTO `mine_quack_5_results` VALUES ('52', '41518554.193', '4596202.719', '484532.824', '2020-02-14 13:59:41', '1.82', '0.50404270499255', 'wyusxzt', '0', '3407500', 'D:/data/ConstructionData/5moti/wyusxzt 2020-02-14 13-59-41`05.csv');
INSERT INTO `mine_quack_5_results` VALUES ('53', '41518148.242', '4597469.364', '60104.761', '2020-06-18 04:19:20', '1.51', '-0.5713540160201966', 'yztwu', '0', '2152600', 'D:/data/ConstructionData/5moti/yztwu 2020-06-18 04-19-21`28.csv');
INSERT INTO `mine_quack_5_results` VALUES ('56', '41515729.074', '4597507.335', '-507344.962', '2020-07-11 08:57:05', '1.96', '-2.9948553516414975', 'ryzwxu', '0', '30471000', 'D:/data/ConstructionData/5moti/ryzwxu 2020-07-11 08-57-07`31.csv');
INSERT INTO `mine_quack_5_results` VALUES ('57', '41631012.549', '4575022.439', '29339876.046', '2020-07-11 09:10:11', '1.57', '122.52848977790336', 'ryzwxu', '0', '3806600', 'D:/data/ConstructionData/5moti/ryzwxu 2020-07-11 09-08-09`33.csv');
INSERT INTO `mine_quack_5_results` VALUES ('58', '41515729.074', '4597507.335', '-507344.962', '2020-07-11 08:57:05', '1.96', '-2.9948553516410072', 'ryzwxu', '0', '30471000', 'D:/data/ConstructionData/5moti/ryzwxu 2020-07-11 08-57-07`01.csv');
INSERT INTO `mine_quack_5_results` VALUES ('59', '41631012.549', '4575022.439', '29339876.046', '2020-07-11 09:10:11', '1.57', '122.52848977790336', 'ryzwxu', '0', '3806600', 'D:/data/ConstructionData/5moti/ryzwxu 2020-07-11 09-08-09`03.csv');
