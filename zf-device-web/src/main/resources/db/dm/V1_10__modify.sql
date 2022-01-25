ALTER TABLE km_listener Modify url VARCHAR(250 CHAR) DEFAULT NULL;
COMMENT ON COLUMN km_listener.url IS 'KM设备消息订阅者url';

