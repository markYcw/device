
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_ai_box ADD ab_username varchar(128) NOT NULL COMMENT '登录账户' After ab_port;
ALTER TABLE ums_new_ai_box ADD ab_password varchar(128) NOT NULL COMMENT '登录密码' After ab_username;