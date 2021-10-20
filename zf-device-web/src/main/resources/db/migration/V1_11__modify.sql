use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_new_ai_box`;
CREATE TABLE `ums_new_ai_box`
(
    `id`                          varchar(32)  NOT NULL COMMENT 'id',
    `ab_name`                     varchar(128) NOT NULL COMMENT '设备名称',
    `ab_ip`                       varchar(32)  NOT NULL COMMENT 'IP',
    `ab_port`                     int          NOT NULL COMMENT '端口',
    `ab_pinyin`                   varchar(255) DEFAULT NULL COMMENT '设备名称拼音(拼音+首字母)',
    `ab_desc`                     varchar(125) DEFAULT NULL COMMENT '描述',
    `create_time`                 datetime(0)  DEFAULT NULL COMMENT '创建时间',
    `update_time`                 datetime(0)  DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AI_Box信息表' ROW_FORMAT = Dynamic;