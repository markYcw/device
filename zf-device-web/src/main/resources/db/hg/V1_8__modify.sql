ALTER TABLE "ums_svr" ADD "web_port" INT4 DEFAULT NULL ;
COMMENT ON COLUMN "ums_svr"."web_port" IS 'svr的websocket端口，一般是9766';

