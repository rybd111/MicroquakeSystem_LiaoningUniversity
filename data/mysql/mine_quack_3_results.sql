/*
Navicat MySQL Data Transfer

Source Server         : KS
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : ks

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-08-01 00:53:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mine_quack_3_results`
-- ----------------------------
DROP TABLE IF EXISTS `mine_quack_3_results`;
CREATE TABLE `mine_quack_3_results` (
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
) ENGINE=InnoDB AUTO_INCREMENT=6346 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mine_quack_3_results
-- ----------------------------
INSERT INTO `mine_quack_3_results` VALUES ('6293', '41518753.642', '4595409.714', '-3204.387', '2020-03-04 16:21:58', '0.47', null, 'zyu', '0', '8', 'D:/data/ConstructionData/3moti/zyu 2020-03-04 16-21-58`59.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6294', '41519754.457', '4597418.961', '5843.18', '2020-03-05 09:42:37', '0.36', null, 'zyu', '0', '8', 'D:/data/ConstructionData/3moti/zyu 2020-03-05 09-42-37`29.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6295', '41518557.138', '4596866.255', '2689.833', '2020-03-05 11:42:07', '0.56', null, 'zus', '0', '7', 'D:/data/ConstructionData/3moti/zus 2020-03-05 11-42-07`83.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6296', '41518680.064', '4595550.682', '-1437.635', '2020-03-05 12:25:57', '0.67', null, 'zyu', '0', '7', 'D:/data/ConstructionData/3moti/zyu 2020-03-05 12-25-57`35.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6297', '41515828.93', '4595143.694', '-8824.607', '2020-03-05 13:35:39', '0.37', null, 'zyu', '0', '8', 'D:/data/ConstructionData/3moti/zyu 2020-03-05 13-35-40`51.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6298', '41520089.792', '4596175.695', '3348.334', '2020-03-05 22:16:16', '0.37', null, 'zyu', '0', '9', 'D:/data/ConstructionData/3moti/zyu 2020-03-05 22-16-17`21.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6299', '41519475.219', '4596963.797', '3156.468', '2020-03-06 08:20:30', '0.7', null, 'zwu', '0', '1', 'D:/data/ConstructionData/3moti/zwu 2020-03-06 08-20-30`17.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6300', '41518262.026', '4594740.281', '-3578.034', '2020-03-06 08:35:25', '0.83', null, 'zus', '0', '1', 'D:/data/ConstructionData/3moti/zus 2020-03-06 08-35-25`53.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6301', '41518530.593', '4595369', '-2667.436', '2020-03-06 08:35:35', '0.62', null, 'zus', '0', '2', 'D:/data/ConstructionData/3moti/zus 2020-03-06 08-35-35`31.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6302', '41520616.874', '4596769.605', '4120.564', '2020-03-06 12:46:03', '0.57', null, 'zyu', '0', '3', 'D:/data/ConstructionData/3moti/zyu 2020-03-06 12-46-04`29.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6303', '41521684.092', '4598355.913', '7691.061', '2020-03-07 06:20:57', '0.83', null, 'zws', '0', '4', 'D:/data/ConstructionData/3moti/zws 2020-03-07 06-20-58`33.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6304', '41517865.919', '4594256.505', '-6604.56', '2020-03-07 06:59:42', '0.4', null, 'zyu', '0', '2', 'D:/data/ConstructionData/3moti/zyu 2020-03-07 06-59-43`09.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6305', '41519116.837', '4595542.631', '1635.152', '2020-03-07 13:59:13', '0.52', null, 'zyu', '0', '3', 'D:/data/ConstructionData/3moti/zyu 2020-03-07 13-59-13`31.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6306', '41519948.874', '4597632.586', '-494.245', '2020-03-08 11:16:20', '1.09', null, 'zyw', '0', '4', 'D:/data/ConstructionData/3moti/zyw 2020-03-08 11-16-20`75.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6307', '41518638.642', '4595357.558', '-412.692', '2020-03-09 11:40:03', '0.68', null, 'zus', '0', '8', 'D:/data/ConstructionData/3moti/zus 2020-03-09 11-40-03`25.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6308', '41519158.818', '4596182.74', '4359.548', '2020-03-09 13:23:28', '0.7', null, 'zyu', '0', '9', 'D:/data/ConstructionData/3moti/zyu 2020-03-09 13-23-28`11.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6309', '41519404.37', '4597481.667', '2106.353', '2020-03-12 08:38:02', '0.3', null, 'zwu', '0', '5', 'D:/data/ConstructionData/3moti/zwu 2020-03-12 08-38-03`05.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6310', '41519394.57', '4595323.259', '1553.346', '2020-03-13 09:08:43', '0.56', null, 'zwu', '0', '4', 'D:/data/ConstructionData/3moti/zwu 2020-03-13 09-08-43`41.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6311', '41518775.434', '4595762.001', '-3363.413', '2020-03-13 10:40:21', '0.5', null, 'zwu', '0', '3', 'D:/data/ConstructionData/3moti/zwu 2020-03-13 10-40-21`17.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6312', '41518035.964', '4595039.993', '-2677.12', '2020-03-13 12:09:22', '0.55', null, 'zwu', '0', '2', 'D:/data/ConstructionData/3moti/zwu 2020-03-13 12-09-22`89.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6313', '41520049.257', '4597172.931', '8629.172', '2020-05-20 13:52:54', '2.74', '-0.5960270170425657', '124637', '0', '65064000', '');
INSERT INTO `mine_quack_3_results` VALUES ('6315', '41519263.88', '4595507.842', '-2966.084', '2020-05-01 09:33:12', '1.86', '-0.28117992928040036', '162534', '0', '30173000', 'D:/data/ConstructionData/5moti/162534 2020-05-01 09-33-12`3.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6316', '41519068.841', '4596809.824', '726.716', '2020-05-01 09:33:14', '1.86', '-0.48126456521667565', '25613', '0', '19657000', 'D:/data/ConstructionData/5moti/25613 2020-05-01 09-33-15`05.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6325', '41516579.978', '4593833.437', '-3603.006', '2020-06-11 15:39:37', '0.93', '-0.4489440734697015', 'uzs', '0', '561620', 'D:/data/ConstructionData/3moti/uzs 2020-06-11 15-39-37`5.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6326', '41518693.072', '4596595.67', '2926.937', '2020-02-14 13:59:33', '2.07', '-0.5368432223501302', 'sytwzxu', '0', '144800000', 'D:/data/ConstructionData/5moti/sytwzxu 2020-02-14 13-59-34`42.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6328', '41515247.275', '4591242.444', '-7705.9', '2020-02-14 13:59:39', '1.83', '-2.3438318997591274', 'wyusxzt', '0', '3407500', 'D:/data/ConstructionData/5moti/wyusxzt 2020-02-14 13-59-41`09.csv');
INSERT INTO `mine_quack_3_results` VALUES ('6335', '41519292.174', '4596668.456', '-1765.085', '2020-06-29 23:50:55', '1.12', '-0.24972541753936442', 'ywzt', '0', '424580', '');
INSERT INTO `mine_quack_3_results` VALUES ('6345', '41520619.948', '4597700.532', '947.864', '2020-07-07 02:42:59', '0.81', '-0.2341524467801683', 'yzxw', '0', '82934', 'D:/data/ConstructionData/5moti/yzxw 2020-07-07 02-43-00`08.csv');
