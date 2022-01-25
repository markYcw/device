-- ----------------------------
-- Table structure for km_listener
-- ----------------------------

DROP TABLE IF EXISTS "km_listener";

CREATE SEQUENCE IF NOT EXISTS "km_listener_id";
CREATE TABLE "km_listener"(
"id" INT4 NOT NULL DEFAULT NEXTVAL('km_listener_id'::REGCLASS) ,
"url" VARCHAR(50 CHAR) DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "km_listener" IS 'KM设备消息订阅者表';
COMMENT ON COLUMN "km_listener"."id" IS 'KM设备消息订阅者数据库标识';
COMMENT ON COLUMN "km_listener"."url" IS 'KM设备消息订阅者url';

