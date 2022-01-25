DROP TABLE IF EXISTS ums_cu;

CREATE TABLE ums_cu(
id INT NOT NULL IDENTITY(1, 1) ,
type INT DEFAULT NULL ,
ssid INT DEFAULT NULL ,
name VARCHAR(128 CHAR) DEFAULT NULL ,
ip VARCHAR(50 CHAR) DEFAULT NULL ,
model_type VARCHAR(50 CHAR) DEFAULT NULL ,
port INT DEFAULT NULL ,
username VARCHAR(128 CHAR) DEFAULT NULL ,
password VARCHAR(128 CHAR) DEFAULT NULL ,
create_time DATETIME(6) DEFAULT NULL ,
modify_time DATETIME(6) DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_cu IS '监控平台';
COMMENT ON COLUMN ums_cu.id IS 'cu数据库标识';
COMMENT ON COLUMN ums_cu.type IS 'cu类型';
COMMENT ON COLUMN ums_cu.ssid IS '登录成功后会话id';
COMMENT ON COLUMN ums_cu.name IS 'svr名称';
COMMENT ON COLUMN ums_cu.ip IS 'cu的IP';
COMMENT ON COLUMN ums_cu.model_type IS 'svr型号类型';
COMMENT ON COLUMN ums_cu.port IS 'cu端口';
COMMENT ON COLUMN ums_cu.username IS '登录cu用户名';
COMMENT ON COLUMN ums_cu.password IS '登录cu密码';
COMMENT ON COLUMN ums_cu.create_time IS '创建时间';
COMMENT ON COLUMN ums_cu.modify_time IS '修改时间';

