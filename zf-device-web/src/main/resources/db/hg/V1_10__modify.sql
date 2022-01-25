ALTER TABLE "km_listener" ALTER COLUMN "url" TYPE  VARCHAR(250);
ALTER TABLE "km_listener" ALTER COLUMN "url" DROP NOT NULL;
ALTER TABLE "km_listener" ALTER COLUMN "url" SET DEFAULT NULL;
COMMENT ON COLUMN "km_listener"."url" IS 'KM设备消息订阅者url';

