use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE km_listener modify COLUMN url varchar(250) DEFAULT NULL COMMENT 'KM设备消息订阅者url';
