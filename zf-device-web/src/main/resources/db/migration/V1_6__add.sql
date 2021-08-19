use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_mcu`;
CREATE TABLE `ums_mcu` (
	`id` BIGINT ( 20 ) NOT NULL COMMENT '会议平台id',
	`ssid` VARCHAR ( 32 ) DEFAULT NULL COMMENT '登录成功后会话id',
	`name` VARCHAR ( 128 ) DEFAULT NULL COMMENT '会议平台名称',
	`ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT '会议平台IP',
	`port` INT ( 11 ) DEFAULT NULL COMMENT '会议平台端口',
	`user` VARCHAR ( 128 ) DEFAULT NULL COMMENT '登录会议平台用户名',
	`password` VARCHAR ( 128 ) DEFAULT NULL COMMENT '登录会议平台密码',
	`mcu_key` VARCHAR ( 32 ) DEFAULT NULL COMMENT '获取token使用',
	`secret` VARCHAR ( 32 ) DEFAULT NULL COMMENT '获取token使用',
	`mcu_type` INT ( 11 ) DEFAULT NULL COMMENT '会议平台版本',
	`mcu_version` VARCHAR ( 128 ) DEFAULT NULL COMMENT '会议平台版本名称',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT = '会议平台';