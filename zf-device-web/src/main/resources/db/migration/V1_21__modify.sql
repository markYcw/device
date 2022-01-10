
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for km_vrs
-- ----------------------------
DROP TABLE IF EXISTS `km_vrs`;
CREATE TABLE `km_vrs`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'vrs标识',
                           `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT 'VRS名称',
                           `vrsname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT 'VRS设备类型',
                           `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'VRS的IP',
                           `port` int(11) NULL DEFAULT NULL COMMENT '终端端口',
                           `username` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'VRS登录账号',
                           `password` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'VRS登录密码',
                           `version` int(11)  default NULL COMMENT 'VRS版本',
                           `ssid` int(11)  default NULL COMMENT '注册返回ID',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'vrs' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for km_vs
-- ----------------------------
DROP TABLE IF EXISTS `km_vs`;
CREATE TABLE `km_vs`  (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'vrs标识',
                           `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT 'VRS名称',
                           `vrsname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT 'VRS设备类型',
                           `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'VRS的IP',
                           `username` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'VRS登录账号',
                           `password` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'VRS登录密码',
                           `version` int(11)  default NULL COMMENT 'VRS版本',
                           `ssid` int(11)  default NULL COMMENT '注册返回ID',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'vrs' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `km_vrs_name`;
CREATE TABLE `km_vrs_name`
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'VRS设备名称数据库标识',
    `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT 'VRS设备名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'VRS设备名称表' ROW_FORMAT = Dynamic;

INSERT INTO `km_vrs_name`(`id`, `name`) VALUES (1, 'VRS2100');
INSERT INTO `km_vrs_name`(`id`, `name`) VALUES (2, 'VRS4100');
INSERT INTO `km_vrs_name`(`id`, `name`) VALUES (3, 'VRS2000B-S');

