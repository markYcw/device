use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE km_listener ADD msg_type INT ( 11 ) DEFAULT NULL COMMENT '消息类型';