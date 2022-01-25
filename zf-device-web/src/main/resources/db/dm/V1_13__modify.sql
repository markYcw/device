ALTER TABLE ums_new_ai_box ADD ab_username VARCHAR(128 CHAR) NOT NULL ;
COMMENT ON COLUMN ums_new_ai_box.ab_username IS '登录账户';

ALTER TABLE ums_new_ai_box ADD ab_password VARCHAR(128 CHAR) NOT NULL ;
COMMENT ON COLUMN ums_new_ai_box.ab_password IS '登录密码';

