DROP TABLE IF EXISTS ums_svr;

CREATE TABLE ums_svr(
id INT NOT NULL IDENTITY(1, 1) ,
ssid INT DEFAULT NULL ,
name VARCHAR(128 CHAR) DEFAULT NULL ,
ip VARCHAR(50 CHAR) DEFAULT NULL ,
model_type VARCHAR(50 CHAR) DEFAULT NULL ,
port INT DEFAULT NULL ,
username VARCHAR(128 CHAR) DEFAULT NULL ,
password VARCHAR(128 CHAR) DEFAULT NULL ,
dev_type INT DEFAULT NULL ,
create_time DATETIME(6) DEFAULT NULL ,
modify_time DATETIME(6) DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_svr IS '会议平台';
COMMENT ON COLUMN ums_svr.id IS 'svr数据库标识';
COMMENT ON COLUMN ums_svr.ssid IS '登录成功后会话id';
COMMENT ON COLUMN ums_svr.name IS 'svr名称';
COMMENT ON COLUMN ums_svr.ip IS 'svr的IP';
COMMENT ON COLUMN ums_svr.model_type IS 'svr型号类型';
COMMENT ON COLUMN ums_svr.port IS 'svr端口';
COMMENT ON COLUMN ums_svr.username IS '登录svr用户名';
COMMENT ON COLUMN ums_svr.password IS '登录svr密码';
COMMENT ON COLUMN ums_svr.dev_type IS 'svr类型';
COMMENT ON COLUMN ums_svr.create_time IS '创建时间';
COMMENT ON COLUMN ums_svr.modify_time IS '修改时间';

