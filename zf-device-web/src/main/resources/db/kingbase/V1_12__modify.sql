ALTER TABLE "km_listener" ADD "msg_type" INT4 DEFAULT NULL ;
COMMENT ON COLUMN "km_listener"."msg_type" IS '消息类型';

