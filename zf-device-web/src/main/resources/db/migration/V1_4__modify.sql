use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_device modify COLUMN device_notify_ip varchar(128) DEFAULT NULL COMMENT '设备状态订阅IP';
