ALTER TABLE "ums_new_group" ALTER COLUMN "group_id" TYPE  VARCHAR(512);
ALTER TABLE "ums_new_group" ALTER COLUMN "group_id" DROP NOT NULL;
ALTER TABLE "ums_new_group" ALTER COLUMN "group_id" SET DEFAULT NULL;
COMMENT ON COLUMN "ums_new_group"."group_id" IS '分组Id';

ALTER TABLE "ums_new_group" ALTER COLUMN "parent_id" TYPE  VARCHAR(512);
ALTER TABLE "ums_new_group" ALTER COLUMN "parent_id" DROP NOT NULL;
ALTER TABLE "ums_new_group" ALTER COLUMN "parent_id" SET DEFAULT NULL;
COMMENT ON COLUMN "ums_new_group"."parent_id" IS '父分组Id';

ALTER TABLE "ums_new_sub_device" ALTER COLUMN "group_id" TYPE  VARCHAR(1024);
ALTER TABLE "ums_new_sub_device" ALTER COLUMN "group_id" DROP NOT NULL;
ALTER TABLE "ums_new_sub_device" ALTER COLUMN "group_id" SET DEFAULT NULL;
COMMENT ON COLUMN "ums_new_sub_device"."group_id" IS '分组Id';

