use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `km_power_config`;
CREATE TABLE `km_power_config`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '电源配置数据库Id',
    `port`        int(11)      NULL     DEFAULT NULL COMMENT '设备监听端口号',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `port_unique_index` (`port`) USING BTREE COMMENT '监听端口号唯一索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '电源配置信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of km_power_config
-- ----------------------------
INSERT INTO `km_power_config`
VALUES (1, 9998, '2021-06-04 08:58:13', '2022-06-07 13:39:06');

-- ----------------------------
-- Table structure for km_power_device
-- ----------------------------
DROP TABLE IF EXISTS `km_power_device`;
CREATE TABLE `km_power_device`
(
    `id`          int(11)                                                 NOT NULL AUTO_INCREMENT COMMENT '电源设备数据库Id',
    `name`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '电源设备名称',
    `ip`          varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '设备IP地址',
    `mac`         varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '设备MAC地址',
    `channels`    int(11)                                                 NULL     DEFAULT NULL COMMENT '电源通道数量',
    `state`       tinyint(2)                                              NOT NULL DEFAULT 1 COMMENT '使用状态（0：关；1：开）',
    `type`        tinyint(2)                                              NOT NULL DEFAULT 2 COMMENT '设备类型（1：RK100；2：Bwant-IPM-08）',
    `port`        int(11)                                                 NULL     DEFAULT NULL COMMENT '端口号',
    `device_sn`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT 'RK100设备序列号',
    `create_time` timestamp(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` timestamp(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `mac_ip_group_unique_index` (`mac`, `ip`) USING BTREE COMMENT '设备MAC地址IP地址组合唯一索引'
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '电源设备信息表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for km_power_type
-- ----------------------------
DROP TABLE IF EXISTS `km_power_type`;
CREATE TABLE `km_power_type`
(
    `id`          int(11)                                                NOT NULL AUTO_INCREMENT COMMENT '电源设备类型数据库Id',
    `dev_type`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电源设备类型',
    `create_time` timestamp(0)                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `update_time` timestamp(0)                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '电源设备类型表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of km_power_type
-- ----------------------------
INSERT INTO `km_power_type`
VALUES (1, 'RK100', '2021-06-09 09:25:31', '2021-06-09 09:25:31');
INSERT INTO `km_power_type`
VALUES (2, 'Bwant-IPM-08', '2021-06-09 09:25:31', '2021-06-09 09:25:31');

SET FOREIGN_KEY_CHECKS = 1;

