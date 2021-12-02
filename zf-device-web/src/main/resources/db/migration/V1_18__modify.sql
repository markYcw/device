
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_cu ADD user_no varchar(128) default NULL COMMENT '用户编号';
ALTER TABLE ums_cu ADD nat_ip varchar(128) default NULL COMMENT 'NAT穿越IP';
ALTER TABLE ums_cu ADD nat_port int(64) default NULL COMMENT 'NAT穿越PORT';