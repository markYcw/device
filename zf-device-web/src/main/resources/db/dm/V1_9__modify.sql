-- ----------------------------
-- Table structure for km_listener
-- ----------------------------

DROP TABLE IF EXISTS km_listener;

CREATE TABLE km_listener(
id INT NOT NULL IDENTITY(1, 1) ,
url VARCHAR(50 CHAR) DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE km_listener IS 'KM设备消息订阅者表';
COMMENT ON COLUMN km_listener.id IS 'KM设备消息订阅者数据库标识';
COMMENT ON COLUMN km_listener.url IS 'KM设备消息订阅者url';

