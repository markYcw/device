ALTER TABLE "ums_new_device" ADD "msp_account" VARCHAR(128) ;
COMMENT ON COLUMN "ums_new_device"."msp_account" IS '拼控服务账号';

ALTER TABLE "ums_new_device" ADD "msp_password" VARCHAR(128) ;
COMMENT ON COLUMN "ums_new_device"."msp_password" IS '拼控服务密码';

