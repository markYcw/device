DROP TABLE IF EXISTS ums_new_media;

CREATE TABLE ums_new_media(
id INT NOT NULL IDENTITY(1, 1) ,
ssid INT DEFAULT NULL ,
name VARCHAR(128 CHAR) DEFAULT NULL ,
dev_ip VARCHAR(50 CHAR) DEFAULT NULL ,
dev_port INT DEFAULT NULL ,
notify_ip VARCHAR(50 CHAR) DEFAULT NULL ,
media_schedule_ip VARCHAR(50 CHAR) DEFAULT NULL ,
media_schedule_port INT DEFAULT NULL ,
nmedia_ip VARCHAR(50 CHAR) DEFAULT NULL ,
nmedia_port INT DEFAULT NULL ,
rec_port INT DEFAULT NULL ,
dev_type INT DEFAULT NULL ,
msp_account VARCHAR(128 CHAR) DEFAULT NULL ,
msp_password VARCHAR(128 CHAR) DEFAULT NULL ,
create_time DATETIME(6) DEFAULT NULL ,
modify_time DATETIME(6) DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_new_media IS '新媒体平台';
COMMENT ON COLUMN ums_new_media.id IS '新媒体数据库标识';
COMMENT ON COLUMN ums_new_media.ssid IS '登录成功后会话id';
COMMENT ON COLUMN ums_new_media.name IS '新媒体名称';
COMMENT ON COLUMN ums_new_media.dev_ip IS '一机一档ip';
COMMENT ON COLUMN ums_new_media.dev_port IS '一机一档端口';
COMMENT ON COLUMN ums_new_media.notify_ip IS '一机一档设备状态订阅ip+端口';
COMMENT ON COLUMN ums_new_media.media_schedule_ip IS '媒体调度服务IP';
COMMENT ON COLUMN ums_new_media.media_schedule_port IS '媒体调度服务端口';
COMMENT ON COLUMN ums_new_media.nmedia_ip IS '流媒体服务IP';
COMMENT ON COLUMN ums_new_media.nmedia_port IS '流媒体服务端口';
COMMENT ON COLUMN ums_new_media.rec_port IS '录像服务端口';
COMMENT ON COLUMN ums_new_media.dev_type IS '新媒体类型';
COMMENT ON COLUMN ums_new_media.msp_account IS '拼控服务账号';
COMMENT ON COLUMN ums_new_media.msp_password IS '拼控服务账号';
COMMENT ON COLUMN ums_new_media.create_time IS '创建时间';
COMMENT ON COLUMN ums_new_media.modify_time IS '修改时间';

