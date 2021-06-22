ALTER TABLE ums_new_device Modify device_notify_ip VARCHAR(128 CHAR) DEFAULT NULL;
COMMENT ON COLUMN ums_new_device.device_notify_ip IS '设备状态订阅IP';

