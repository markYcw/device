use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_media ADD pre_address varchar(128) default NULL COMMENT '接口地址前缀';
ALTER TABLE ums_new_media ADD ex_ip varchar(128) default NULL COMMENT 'EX服务IP';
ALTER TABLE ums_new_media ADD ex_port int(64) default NULL COMMENT 'EX服务端口';

