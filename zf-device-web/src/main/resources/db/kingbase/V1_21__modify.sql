-- ----------------------------
-- Table structure for km_vrs
-- ----------------------------

DROP TABLE IF EXISTS "km_vrs";

CREATE SEQUENCE IF NOT EXISTS "km_vrs_id";
CREATE TABLE "km_vrs"(
"id" INT4 NOT NULL DEFAULT NEXTVAL('km_vrs_id'::REGCLASS) ,
"name" VARCHAR(64 CHAR) DEFAULT NULL ,
"vrsname" VARCHAR(64 CHAR) DEFAULT NULL ,
"ip" VARCHAR(50 CHAR) NOT NULL ,
"port" INT4 DEFAULT NULL ,
"username" VARCHAR(24 CHAR) NOT NULL ,
"password" VARCHAR(24 CHAR) NOT NULL ,
"version" INT4 DEFAULT NULL ,
"ssid" INT4 DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "km_vrs" IS 'vrs';
COMMENT ON COLUMN "km_vrs"."id" IS 'vrs标识';
COMMENT ON COLUMN "km_vrs"."name" IS 'VRS名称';
COMMENT ON COLUMN "km_vrs"."vrsname" IS 'VRS设备类型';
COMMENT ON COLUMN "km_vrs"."ip" IS 'VRS的IP';
COMMENT ON COLUMN "km_vrs"."port" IS '终端端口';
COMMENT ON COLUMN "km_vrs"."username" IS 'VRS登录账号';
COMMENT ON COLUMN "km_vrs"."password" IS 'VRS登录密码';
COMMENT ON COLUMN "km_vrs"."version" IS 'VRS版本';
COMMENT ON COLUMN "km_vrs"."ssid" IS '注册返回ID';

-- ----------------------------
-- Table structure for km_vs
-- ----------------------------

DROP TABLE IF EXISTS "km_vs";

CREATE SEQUENCE IF NOT EXISTS "km_vs_id";
CREATE TABLE "km_vs"(
"id" INT4 NOT NULL DEFAULT NEXTVAL('km_vs_id'::REGCLASS) ,
"name" VARCHAR(64 CHAR) DEFAULT NULL ,
"vrsname" VARCHAR(64 CHAR) DEFAULT NULL ,
"ip" VARCHAR(50 CHAR) NOT NULL ,
"username" VARCHAR(24 CHAR) NOT NULL ,
"password" VARCHAR(24 CHAR) NOT NULL ,
"version" INT4 DEFAULT NULL ,
"ssid" INT4 DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "km_vs" IS 'vrs';
COMMENT ON COLUMN "km_vs"."id" IS 'vrs标识';
COMMENT ON COLUMN "km_vs"."name" IS 'VRS名称';
COMMENT ON COLUMN "km_vs"."vrsname" IS 'VRS设备类型';
COMMENT ON COLUMN "km_vs"."ip" IS 'VRS的IP';
COMMENT ON COLUMN "km_vs"."username" IS 'VRS登录账号';
COMMENT ON COLUMN "km_vs"."password" IS 'VRS登录密码';
COMMENT ON COLUMN "km_vs"."version" IS 'VRS版本';
COMMENT ON COLUMN "km_vs"."ssid" IS '注册返回ID';

DROP TABLE IF EXISTS "km_vrs_name";

CREATE SEQUENCE IF NOT EXISTS "km_vrs_name_id";
CREATE TABLE "km_vrs_name"(
"id" INT4 NOT NULL DEFAULT NEXTVAL('km_vrs_name_id'::REGCLASS) ,
"name" VARCHAR(128 CHAR) DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON TABLE "km_vrs_name" IS 'VRS设备名称表';
COMMENT ON COLUMN "km_vrs_name"."id" IS 'VRS设备名称数据库标识';
COMMENT ON COLUMN "km_vrs_name"."name" IS 'VRS设备名称';

INSERT INTO "km_vrs_name"("id", "name") VALUES (1, 'VRS2100');

INSERT INTO "km_vrs_name"("id", "name") VALUES (2, 'VRS4100');

INSERT INTO "km_vrs_name"("id", "name") VALUES (3, 'VRS2000B-S');

