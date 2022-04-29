use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_data`;
CREATE TABLE `ums_data` (
    `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据迁移ID',
	`dc` INT ( 11 ) DEFAULT NULL COMMENT '数据迁移标识',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic COMMENT = '数据迁移表';