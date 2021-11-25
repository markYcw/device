
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_streaming_media`;
CREATE TABLE `ums_streaming_media`  (
    `id`                          bigint(20) NOT NULL COMMENT 'id',
    `sm_name`                     varchar(128) NOT NULL COMMENT '流媒体服务器名称',
    `sm_ip`                       varchar(50) NOT NULL COMMENT '流媒体服务器IP',
    `sm_port`                     int(11) DEFAULT NULL COMMENT '流媒体服务器端口',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流媒体服务配置' ROW_FORMAT = Dynamic;