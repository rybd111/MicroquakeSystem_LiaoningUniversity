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
) ENGINE = FEDERATED 
CONNECTION='mysql://yhy:071139@192.168.11.100:3306/ks/mine_quack_stationcondition';