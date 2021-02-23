/*
 Navicat Premium Data Transfer

 Source Server         : yhy
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : ks

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 15/11/2020 16:36:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mine_quack_stationcondition
-- ----------------------------
DROP TABLE IF EXISTS `mine_quack_stationcondition`;
CREATE TABLE `mine_quack_stationcondition`  (
  `day` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `panfu` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `xData` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `yData` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zData` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unused` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `used` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `total` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `netspeed` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`day`, `panfu`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mine_quack_stationcondition
-- ----------------------------
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'R:', '大棚', '4.15172900374E7', '4599537.3261', '24.5649', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'S:', '蒿子屯', '4.1516836655E7', '4596627.472', '21.545', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'T:', '杨甸子', '4.1518099807E7', '4595388.504', '22.776', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'U:', '树碑子', '4.1518060298E7', '4594304.927', '21.926', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'V:', '南风井', '4.151670744E7', '4593163.619', '22.564', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'W:', '北青堆子', '4.1520207356E7', '4597983.404', '22.661', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'X:', '矿上车队', '4.1520815875E7', '4597384.576', '25.468', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'Y:', '火药库', '4.1519926476E7', '4597275.978', '20.705', 'offline', 'null', 'null', 'null', 'null');
INSERT INTO `mine_quack_stationcondition` VALUES ('2020-11-15', 'Z:', '工业广场', '4.1519304125E7', '4595913.485', '23.921', 'offline', 'null', 'null', 'null', 'null');

SET FOREIGN_KEY_CHECKS = 1;
