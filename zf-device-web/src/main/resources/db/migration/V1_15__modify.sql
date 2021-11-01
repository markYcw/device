use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_cu`;
CREATE TABLE `ums_cu` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'cu数据库标识',
    `type` INT ( 11 ) DEFAULT NULL COMMENT 'cu类型',
	`ssid` INT ( 20 ) DEFAULT NULL COMMENT '登录成功后会话id',
	`name` VARCHAR ( 128 ) DEFAULT NULL COMMENT 'svr名称',
	`ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT 'cu的IP',
    `model_type` VARCHAR ( 50 ) DEFAULT NULL COMMENT 'svr型号类型',
	`port` INT ( 11 ) DEFAULT NULL COMMENT 'cu端口',
	`username` VARCHAR ( 128 ) DEFAULT NULL COMMENT '登录cu用户名',
	`password` VARCHAR ( 128 ) DEFAULT NULL COMMENT '登录cu密码',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT = '监控平台';