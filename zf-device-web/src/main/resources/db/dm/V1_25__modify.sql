-- ----------------------------
-- Table structure for km_power_type
-- ----------------------------

DROP TABLE IF EXISTS km_power_type;

CREATE TABLE km_power_type(
id INT NOT NULL IDENTITY(1, 1) ,
dev_type VARCHAR(50 CHAR) NOT NULL ,
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE km_power_type IS '电源设备类型表';
COMMENT ON COLUMN km_power_type.id IS '电源设备类型数据库Id';
COMMENT ON COLUMN km_power_type.dev_type IS '电源设备类型';
COMMENT ON COLUMN km_power_type.create_time IS '创建时间';
COMMENT ON COLUMN km_power_type.update_time IS '更新时间';

-- ----------------------------
-- Records of km_power_type
-- ----------------------------

INSERT INTO km_power_typeVALUES (1, 'RK100', '2021-06-09 09:25:31', '2021-06-09 09:25:31');

INSERT INTO km_power_typeVALUES (2, 'Bwant-IPM-08', '2021-06-09 09:25:31', '2021-06-09 09:25:31');

-- ----------------------------
-- Table structure for km_power_config
-- ----------------------------

DROP TABLE IF EXISTS km_power_config;

CREATE TABLE km_power_config(
id INT NOT NULL IDENTITY(1, 1) ,
port INT DEFAULT NULL ,
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (id) 
);
CREATE INDEX port_unique_index ON km_power_config(port);
COMMENT ON TABLE km_power_config IS '电源配置信息表';
COMMENT ON COLUMN km_power_config.id IS '电源配置数据库Id';
COMMENT ON COLUMN km_power_config.port IS '设备监听端口号';
COMMENT ON COLUMN km_power_config.create_time IS '创建时间';
COMMENT ON COLUMN km_power_config.update_time IS '更新时间';

-- ----------------------------
-- Records of km_power_config
-- ----------------------------

INSERT INTO km_power_configVALUES (1, 9998, '2021-06-04 08:58:13', '2022-06-07 13:39:06');

-- ----------------------------
-- Table structure for km_power_device
-- ----------------------------

DROP TABLE IF EXISTS km_power_device;

CREATE TABLE km_power_device(
id INT NOT NULL IDENTITY(1, 1) ,
name VARCHAR(64 CHAR) DEFAULT NULL ,
ip VARCHAR(15 CHAR) DEFAULT NULL ,
mac VARCHAR(128 CHAR) DEFAULT NULL ,
channels INT DEFAULT NULL ,
state TINYINT NOT NULL DEFAULT 1 ,
type TINYINT NOT NULL DEFAULT 2 ,
port INT DEFAULT NULL ,
device_sn VARCHAR(64 CHAR) DEFAULT NULL ,
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (id) 
);
CREATE INDEX mac_ip_group_unique_index ON km_power_device(mac,ip);
COMMENT ON TABLE km_power_device IS '电源设备信息表';
COMMENT ON COLUMN km_power_device.id IS '电源设备数据库Id';
COMMENT ON COLUMN km_power_device.name IS '电源设备名称';
COMMENT ON COLUMN km_power_device.ip IS '设备IP地址';
COMMENT ON COLUMN km_power_device.mac IS '设备MAC地址';
COMMENT ON COLUMN km_power_device.channels IS '电源通道数量';
COMMENT ON COLUMN km_power_device.state IS '使用状态（0：关；1：开）';
COMMENT ON COLUMN km_power_device.type IS '设备类型（1：RK100；2：Bwant-IPM-08）';
COMMENT ON COLUMN km_power_device.port IS '端口号';
COMMENT ON COLUMN km_power_device.device_sn IS 'RK100设备序列号';
COMMENT ON COLUMN km_power_device.create_time IS '创建时间';
COMMENT ON COLUMN km_power_device.update_time IS '更新时间';

