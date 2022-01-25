ALTER TABLE "ums_new_ai_box" DROP COLUMN "ab_min_face";

ALTER TABLE "ums_new_ai_box" DROP COLUMN "ab_max_face";

DROP TABLE IF EXISTS "ums_new_ai_box_threshold";

CREATE TABLE "ums_new_ai_box_threshold"(
"id" VARCHAR(32 CHAR) NOT NULL ,
"ab_id" VARCHAR(32 CHAR) NOT NULL ,
"ab_min_face" INT4 NOT NULL ,
"ab_max_face" INT4 NOT NULL ,
"create_time" TIMESTAMP(6) DEFAULT NULL ,
"update_time" TIMESTAMP(6) DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "ums_new_ai_box_threshold" IS 'AI_Box信息表';
COMMENT ON COLUMN "ums_new_ai_box_threshold"."id" IS 'id';
COMMENT ON COLUMN "ums_new_ai_box_threshold"."ab_id" IS '设备ID';
COMMENT ON COLUMN "ums_new_ai_box_threshold"."ab_min_face" IS '能识别的人脸最小像素值(默认为60)';
COMMENT ON COLUMN "ums_new_ai_box_threshold"."ab_max_face" IS '能识别的人脸最大像素值(默认为60)';
COMMENT ON COLUMN "ums_new_ai_box_threshold"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_ai_box_threshold"."update_time" IS '修改时间';

