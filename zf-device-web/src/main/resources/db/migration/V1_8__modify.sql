use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_svr ADD web_port INT ( 11 ) DEFAULT NULL COMMENT 'svr的websocket端口，一般是9766';