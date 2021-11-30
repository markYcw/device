
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_mt_type`;
CREATE TABLE `ums_mt_type`
(
    `id`                          varchar(32)  NOT NULL COMMENT 'id',
    `mt_type`                     int(11) NOT NULL COMMENT '终端设备类型',
    `mt_name`                     varchar(128) NOT NULL COMMENT '终端设备名称',
    `mt_version`                  int(3) NOT NULL COMMENT '终端设备版本，0:3.0; 1:5.0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '终端类型表' ROW_FORMAT = Dynamic;