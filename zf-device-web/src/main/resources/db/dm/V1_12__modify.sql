ALTER TABLE km_listener ADD msg_type INT DEFAULT NULL ;
COMMENT ON COLUMN km_listener.msg_type IS '消息类型';

