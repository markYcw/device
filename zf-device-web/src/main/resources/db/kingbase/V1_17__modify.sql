DROP TABLE IF EXISTS "ums_streaming_media";

CREATE TABLE "ums_streaming_media"(
"id" INT8 NOT NULL ,
"sm_name" VARCHAR(128 CHAR) NOT NULL ,
"sm_ip" VARCHAR(50 CHAR) NOT NULL ,
"sm_port" INT4 DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "ums_streaming_media" IS '流媒体服务配置';
COMMENT ON COLUMN "ums_streaming_media"."id" IS 'id';
COMMENT ON COLUMN "ums_streaming_media"."sm_name" IS '流媒体服务器名称';
COMMENT ON COLUMN "ums_streaming_media"."sm_ip" IS '流媒体服务器IP';
COMMENT ON COLUMN "ums_streaming_media"."sm_port" IS '流媒体服务器端口';

