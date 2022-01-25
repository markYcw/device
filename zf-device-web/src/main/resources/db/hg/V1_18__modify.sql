ALTER TABLE "ums_cu" ADD "user_no" VARCHAR(128) DEFAULT NULL ;
COMMENT ON COLUMN "ums_cu"."user_no" IS '用户编号';

ALTER TABLE "ums_cu" ADD "nat_ip" VARCHAR(128) DEFAULT NULL ;
COMMENT ON COLUMN "ums_cu"."nat_ip" IS 'NAT穿越IP';

ALTER TABLE "ums_cu" ADD "nat_port" INT4 DEFAULT NULL ;
COMMENT ON COLUMN "ums_cu"."nat_port" IS 'NAT穿越PORT';

