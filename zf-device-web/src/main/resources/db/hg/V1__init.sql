DROP TABLE IF EXISTS "ums_new_device";

CREATE TABLE "ums_new_device"(
"id" VARCHAR(32) NOT NULL ,
"session_id" VARCHAR(32) NOT NULL ,
"name" VARCHAR(128) NOT NULL ,
"device_type" VARCHAR(32) NOT NULL ,
"device_ip" VARCHAR(32) DEFAULT NULL ,
"device_port" INT4 DEFAULT NULL ,
"device_notify_ip" VARCHAR(32) DEFAULT NULL ,
"media_ip" VARCHAR(32) DEFAULT NULL ,
"media_port" INT4 DEFAULT NULL ,
"group_id" VARCHAR(32) DEFAULT NULL ,
"streaming_media_ip" VARCHAR(32) DEFAULT NULL ,
"streaming_media_port" INT4 DEFAULT NULL ,
"streaming_media_rec_port" INT4 DEFAULT NULL ,
"last_sync_third_device_time" VARCHAR(125) DEFAULT NULL ,
"create_time" TIMESTAMP DEFAULT NULL ,
"update_time" TIMESTAMP DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON COLUMN "ums_new_device"."id" IS '数据库Id';
COMMENT ON COLUMN "ums_new_device"."session_id" IS '会话';
COMMENT ON COLUMN "ums_new_device"."name" IS '设备名称';
COMMENT ON COLUMN "ums_new_device"."device_type" IS '设备类型，关联ums_device_type表';
COMMENT ON COLUMN "ums_new_device"."device_ip" IS '平台IP';
COMMENT ON COLUMN "ums_new_device"."device_port" IS '平台端口';
COMMENT ON COLUMN "ums_new_device"."device_notify_ip" IS '设备状态订阅IP';
COMMENT ON COLUMN "ums_new_device"."media_ip" IS '媒体调度服务IP';
COMMENT ON COLUMN "ums_new_device"."media_port" IS '媒体调度服务端口';
COMMENT ON COLUMN "ums_new_device"."group_id" IS '分组Id，关联ums_new_group表';
COMMENT ON COLUMN "ums_new_device"."streaming_media_ip" IS '流媒体服务IP';
COMMENT ON COLUMN "ums_new_device"."streaming_media_port" IS '流媒体服务端口';
COMMENT ON COLUMN "ums_new_device"."streaming_media_rec_port" IS '录像服务端口';
COMMENT ON COLUMN "ums_new_device"."last_sync_third_device_time" IS '最近一次同步第三方服务子设备时间';
COMMENT ON COLUMN "ums_new_device"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_device"."update_time" IS '修改时间';

DROP TABLE IF EXISTS "ums_new_device_type";

CREATE TABLE "ums_new_device_type"(
"id" VARCHAR(32) NOT NULL ,
"device_code" VARCHAR(5) DEFAULT NULL ,
"device_value" VARCHAR(30) DEFAULT NULL ,
"device_type_desc" VARCHAR(128) DEFAULT NULL ,
"create_time" TIMESTAMP DEFAULT NULL ,
"update_time" TIMESTAMP DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON COLUMN "ums_new_device_type"."id" IS '数据库Id';
COMMENT ON COLUMN "ums_new_device_type"."device_code" IS '类型编码';
COMMENT ON COLUMN "ums_new_device_type"."device_value" IS '设备类型';
COMMENT ON COLUMN "ums_new_device_type"."device_type_desc" IS '设备类型描述';
COMMENT ON COLUMN "ums_new_device_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_device_type"."update_time" IS '修改时间';

DROP TABLE IF EXISTS "ums_new_alarm_type";

CREATE TABLE "ums_new_alarm_type"(
"id" VARCHAR(32) NOT NULL ,
"alarm_code" VARCHAR(64) DEFAULT NULL ,
"alarm_desc" VARCHAR(128) DEFAULT NULL ,
"create_time" TIMESTAMP DEFAULT NULL ,
"update_time" TIMESTAMP DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON COLUMN "ums_new_alarm_type"."id" IS '数据库Id';
COMMENT ON COLUMN "ums_new_alarm_type"."alarm_code" IS '报警类型标识';
COMMENT ON COLUMN "ums_new_alarm_type"."alarm_desc" IS '报警类型描述';
COMMENT ON COLUMN "ums_new_alarm_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_alarm_type"."update_time" IS '修改时间';

DROP TABLE IF EXISTS "ums_new_group";

CREATE TABLE "ums_new_group"(
"id" VARCHAR(128) NOT NULL ,
"group_id" VARCHAR(128) DEFAULT NULL ,
"parent_id" VARCHAR(128) DEFAULT '0' ,
"group_name" VARCHAR(128) DEFAULT NULL ,
"group_devId" VARCHAR(64) DEFAULT NULL ,
"group_catalog" VARCHAR(128) DEFAULT NULL ,
"group_code" VARCHAR(128) DEFAULT NULL ,
"sort_index" INT4 DEFAULT '0' ,
"create_time" TIMESTAMP DEFAULT NULL ,
"update_time" TIMESTAMP DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON COLUMN "ums_new_group"."id" IS '数据库Id';
COMMENT ON COLUMN "ums_new_group"."group_id" IS '分组Id';
COMMENT ON COLUMN "ums_new_group"."parent_id" IS '父分组Id';
COMMENT ON COLUMN "ums_new_group"."group_name" IS '分组名称';
COMMENT ON COLUMN "ums_new_group"."group_devId" IS '分组所在设备Id';
COMMENT ON COLUMN "ums_new_group"."group_catalog" IS '分组目录';
COMMENT ON COLUMN "ums_new_group"."group_code" IS '分组码';
COMMENT ON COLUMN "ums_new_group"."sort_index" IS '分组排序';
COMMENT ON COLUMN "ums_new_group"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_group"."update_time" IS '修改时间';

DROP TABLE IF EXISTS "ums_new_sub_device";

CREATE TABLE "ums_new_sub_device"(
"id" VARCHAR(32) NOT NULL ,
"parent_id" VARCHAR(32) DEFAULT NULL ,
"name" VARCHAR(128) DEFAULT NULL ,
"device_type" VARCHAR(30) DEFAULT NULL ,
"gbid" VARCHAR(20) DEFAULT NULL ,
"device_ip" VARCHAR(32) DEFAULT NULL ,
"manufactor_name" VARCHAR(32) DEFAULT NULL ,
"manufactor_code" INT4 DEFAULT NULL ,
"group_id" VARCHAR(256) DEFAULT NULL ,
"device_status" INT4 DEFAULT '1' ,
"longitude" DECIMAL(10,7) DEFAULT NULL ,
"longitude_str" VARCHAR(32) DEFAULT NULL ,
"latitude" DECIMAL(10,7) DEFAULT NULL ,
"latitude_str" VARCHAR(32) DEFAULT NULL ,
"create_time" TIMESTAMP DEFAULT NULL ,
"device_mod" INT4 DEFAULT '0' ,
"device_model" VARCHAR(255) DEFAULT NULL ,
"civil_name" VARCHAR(255) DEFAULT NULL ,
"department_name" VARCHAR(255) DEFAULT NULL ,
"maintain_man" VARCHAR(255) DEFAULT NULL ,
"maintain_contact" VARCHAR(255) DEFAULT NULL ,
"address" VARCHAR(255) DEFAULT NULL ,
"install_date" TIMESTAMP DEFAULT NULL ,
"domain_id" VARCHAR(255) DEFAULT NULL ,
"pinyin" VARCHAR(255) DEFAULT NULL ,
"update_time" TIMESTAMP DEFAULT NULL ,
PRIMARY KEY ("id") 
);
COMMENT ON COLUMN "ums_new_sub_device"."id" IS '设备Id';
COMMENT ON COLUMN "ums_new_sub_device"."parent_id" IS '上级统一设备ID';
COMMENT ON COLUMN "ums_new_sub_device"."name" IS '设备名称';
COMMENT ON COLUMN "ums_new_sub_device"."device_type" IS '设备类型';
COMMENT ON COLUMN "ums_new_sub_device"."gbid" IS '国标ID';
COMMENT ON COLUMN "ums_new_sub_device"."device_ip" IS 'IP地址';
COMMENT ON COLUMN "ums_new_sub_device"."manufactor_name" IS '厂商名';
COMMENT ON COLUMN "ums_new_sub_device"."manufactor_code" IS '厂商代码';
COMMENT ON COLUMN "ums_new_sub_device"."group_id" IS '分组Id';
COMMENT ON COLUMN "ums_new_sub_device"."device_status" IS '设备状态';
COMMENT ON COLUMN "ums_new_sub_device"."longitude" IS '经度';
COMMENT ON COLUMN "ums_new_sub_device"."longitude_str" IS '经度字符串';
COMMENT ON COLUMN "ums_new_sub_device"."latitude" IS '纬度';
COMMENT ON COLUMN "ums_new_sub_device"."latitude_str" IS '纬度字符串';
COMMENT ON COLUMN "ums_new_sub_device"."create_time" IS '创建时间';
COMMENT ON COLUMN "ums_new_sub_device"."device_mod" IS '设备模式';
COMMENT ON COLUMN "ums_new_sub_device"."device_model" IS '型号';
COMMENT ON COLUMN "ums_new_sub_device"."civil_name" IS '行政区域';
COMMENT ON COLUMN "ums_new_sub_device"."department_name" IS '部门名称';
COMMENT ON COLUMN "ums_new_sub_device"."maintain_man" IS '维护人';
COMMENT ON COLUMN "ums_new_sub_device"."maintain_contact" IS '联系方式';
COMMENT ON COLUMN "ums_new_sub_device"."address" IS '安装地址';
COMMENT ON COLUMN "ums_new_sub_device"."install_date" IS '安装时间';
COMMENT ON COLUMN "ums_new_sub_device"."domain_id" IS '域Id';
COMMENT ON COLUMN "ums_new_sub_device"."pinyin" IS '设备名称(拼音+首字母)';
COMMENT ON COLUMN "ums_new_sub_device"."update_time" IS '修改时间';

