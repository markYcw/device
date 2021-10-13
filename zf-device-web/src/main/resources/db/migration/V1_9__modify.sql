use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for km_listener
-- ----------------------------
DROP TABLE IF EXISTS `km_listener`;
CREATE TABLE `km_listener`
(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'KM设备消息订阅者数据库标识',
    `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT 'KM设备消息订阅者url',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'KM设备消息订阅者表' ROW_FORMAT = Dynamic;
