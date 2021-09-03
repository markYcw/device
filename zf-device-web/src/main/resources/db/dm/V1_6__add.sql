DROP TABLE IF EXISTS ums_mcu;

CREATE TABLE ums_mcu(
id BIGINT NOT NULL ,
ssid VARCHAR(32 CHAR) DEFAULT NULL ,
mcu_name VARCHAR(128 CHAR) DEFAULT NULL ,
mcu_ip VARCHAR(50 CHAR) DEFAULT NULL ,
mcu_port INT DEFAULT NULL ,
mcu_user VARCHAR(128 CHAR) DEFAULT NULL ,
mcu_password VARCHAR(128 CHAR) DEFAULT NULL ,
mcu_key VARCHAR(32 CHAR) DEFAULT NULL ,
mcu_secret VARCHAR(32 CHAR) DEFAULT NULL ,
mcu_type INT DEFAULT NULL ,
mcu_version VARCHAR(128 CHAR) DEFAULT NULL ,
create_time DATETIME(6) DEFAULT NULL ,
modify_time DATETIME(6) DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_mcu IS '会议平台';
COMMENT ON COLUMN ums_mcu.id IS '会议平台id';
COMMENT ON COLUMN ums_mcu.ssid IS '登录成功后会话id';
COMMENT ON COLUMN ums_mcu.mcu_name IS '会议平台名称';
COMMENT ON COLUMN ums_mcu.mcu_ip IS '会议平台IP';
COMMENT ON COLUMN ums_mcu.mcu_port IS '会议平台端口';
COMMENT ON COLUMN ums_mcu.mcu_user IS '登录会议平台用户名';
COMMENT ON COLUMN ums_mcu.mcu_password IS '登录会议平台密码';
COMMENT ON COLUMN ums_mcu.mcu_key IS '获取token使用';
COMMENT ON COLUMN ums_mcu.mcu_secret IS '获取token使用';
COMMENT ON COLUMN ums_mcu.mcu_type IS '会议平台版本';
COMMENT ON COLUMN ums_mcu.mcu_version IS '会议平台版本名称';
COMMENT ON COLUMN ums_mcu.create_time IS '创建时间';
COMMENT ON COLUMN ums_mcu.modify_time IS '修改时间';

