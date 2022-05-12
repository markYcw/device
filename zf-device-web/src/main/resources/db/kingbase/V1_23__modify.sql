DROP TABLE IF EXISTS "ums_data";

CREATE SEQUENCE IF NOT EXISTS "ums_data_id";
CREATE TABLE "ums_data"(
"id" INT4 NOT NULL DEFAULT NEXTVAL('ums_data_id'::REGCLASS) ,
"dc" INT4 DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "ums_data" IS '数据迁移表';
COMMENT ON COLUMN "ums_data"."id" IS '数据迁移ID';
COMMENT ON COLUMN "ums_data"."dc" IS '数据迁移标识';

