use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_group modify COLUMN group_id varchar(512) DEFAULT NULL COMMENT '分组Id';
ALTER TABLE ums_new_group modify COLUMN parent_id varchar(512) DEFAULT NULL COMMENT '父分组Id';
ALTER TABLE ums_new_sub_device modify COLUMN group_id varchar(1024) DEFAULT NULL COMMENT '分组Id';