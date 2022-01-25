-- ----------------------------
-- Table structure for km_svr_device_type
-- ----------------------------

DROP TABLE IF EXISTS km_svr_type;

CREATE TABLE km_svr_type(
id INT NOT NULL IDENTITY(1, 1) ,
type VARCHAR(50 CHAR) NOT NULL ,
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modify_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE km_svr_type IS 'SVR设备类型表';
COMMENT ON COLUMN km_svr_type.id IS 'SVR设备类型数据库Id';
COMMENT ON COLUMN km_svr_type.type IS 'SVR设备类型';
COMMENT ON COLUMN km_svr_type.create_time IS '创建时间';
COMMENT ON COLUMN km_svr_type.modify_time IS '更新时间';

