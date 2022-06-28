ALTER TABLE "ums_new_media" ADD "pre_address" VARCHAR(128 CHAR) DEFAULT NULL ;
COMMENT ON COLUMN "ums_new_media"."pre_address" IS '接口地址前缀';

ALTER TABLE "ums_new_media" ADD "ex_ip" VARCHAR(128 CHAR) DEFAULT NULL ;
COMMENT ON COLUMN "ums_new_media"."ex_ip" IS 'EX服务IP';

ALTER TABLE "ums_new_media" ADD "ex_port" INT4 DEFAULT NULL ;
COMMENT ON COLUMN "ums_new_media"."ex_port" IS 'EX服务端口';

