use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_new_media`;
CREATE TABLE `ums_new_media` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '新媒体数据库标识',
	`ssid` INT ( 11 ) DEFAULT NULL COMMENT '登录成功后会话id',
	`name` VARCHAR ( 128 ) DEFAULT NULL COMMENT '新媒体名称',
	`dev_ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT '一机一档ip',
	`dev_port` INT ( 11 ) DEFAULT NULL COMMENT '一机一档端口',
    `notify_ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT '一机一档设备状态订阅ip+端口',
	`media_schedule_ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT '媒体调度服务IP',
    `media_schedule_port` INT ( 11 ) DEFAULT NULL COMMENT '媒体调度服务端口',
    `nmedia_ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT '流媒体服务IP',
    `nmedia_port` INT ( 11 ) DEFAULT NULL COMMENT '流媒体服务端口',
    `rec_port` INT ( 11 ) DEFAULT NULL COMMENT '录像服务端口',
	`dev_type` INT ( 11 ) DEFAULT NULL COMMENT '新媒体类型',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT = '新媒体平台';