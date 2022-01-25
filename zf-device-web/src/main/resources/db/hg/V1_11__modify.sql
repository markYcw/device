DROP TABLE IF EXISTS "ums_new_ai_box";

CREATE TABLE "ums_new_ai_box"(
"id" VARCHAR(32) NOT NULL ,
"ab_name" VARCHAR(128) NOT NULL ,
"ab_ip" VARCHAR(32) NOT NULL ,
"ab_port" INT4 NOT NULL ,
"ab_pinyin" VARCHAR(255) DEFAULT NULL ,
"ab_desc" VARCHAR(125) DEFAULT NULL ,
"create_time" TIMESTAMP(6) DEFAULT NULL ,
"update_time" TIMESTAMP(6) DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "ums_new_ai_box" IS 'AI_Box信息表';
COMMENT ON COLUMN "ums_new_ai_box"."id" IS 'id';
COMMENT ON COLUMN "ums_new_ai_box"."ab_name" IS '设备名称';
COMMENT ON COLUMN "ums_new_ai_box"."ab_ip" IS 'IP';
COMMENT ON COLUMN "ums_new_ai_box"."ab_port" IS '端口';
COMMENT ON COLUMN "ums_new_ai_box"."ab_pinyin" IS '设备名称拼音(拼音+首字母)';
COMMENT ON COLUMN "ums_new_ai_box"."ab_desc" IS '描述';
COMMENT ON COLUMN "ums_new_ai_box"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_ai_box"."update_time" IS '修改时间';

