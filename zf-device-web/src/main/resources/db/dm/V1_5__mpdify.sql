ALTER TABLE ums_new_group Modify group_id VARCHAR(512 CHAR) DEFAULT NULL;
COMMENT ON COLUMN ums_new_group.group_id IS '分组Id';

ALTER TABLE ums_new_group Modify parent_id VARCHAR(512 CHAR) DEFAULT NULL;
COMMENT ON COLUMN ums_new_group.parent_id IS '父分组Id';

ALTER TABLE ums_new_sub_device Modify group_id VARCHAR(1024 CHAR) DEFAULT NULL;
COMMENT ON COLUMN ums_new_sub_device.group_id IS '分组Id';

