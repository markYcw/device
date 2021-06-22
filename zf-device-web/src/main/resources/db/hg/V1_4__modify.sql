ALTER TABLE "ums_new_device" ALTER COLUMN "device_notify_ip" TYPE  VARCHAR(128);
ALTER TABLE "ums_new_device" ALTER COLUMN "device_notify_ip" DROP NOT NULL;
ALTER TABLE "ums_new_device" ALTER COLUMN "device_notify_ip" SET DEFAULT NULL;
COMMENT ON COLUMN "ums_new_device"."device_notify_ip" IS '设备状态订阅IP';

