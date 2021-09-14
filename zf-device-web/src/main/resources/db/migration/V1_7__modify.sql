use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_svr`;
CREATE TABLE `ums_svr` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'svr数据库标识',
	`ssid` INT ( 20 ) DEFAULT NULL COMMENT '登录成功后会话id',
	`name` VARCHAR ( 128 ) DEFAULT NULL COMMENT 'svr名称',
	`ip` VARCHAR ( 50 ) DEFAULT NULL COMMENT 'svr的IP',
    `model_type` VARCHAR ( 50 ) DEFAULT NULL COMMENT 'svr型号类型',
	`port` INT ( 11 ) DEFAULT NULL COMMENT 'svr端口',
	`username` VARCHAR ( 128 ) DEFAULT NULL COMMENT '登录svr用户名',
	`password` VARCHAR ( 128 ) DEFAULT NULL COMMENT '登录svr密码',
	`dev_type` INT ( 11 ) DEFAULT NULL COMMENT 'svr类型',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT = '会议平台';