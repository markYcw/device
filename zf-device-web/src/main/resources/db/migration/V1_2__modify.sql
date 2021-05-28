use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_device ADD `msp_account` VARCHAR(128) COMMENT '拼控服务账号';
ALTER TABLE ums_new_device ADD `msp_password` VARCHAR(128) COMMENT '拼控服务密码';