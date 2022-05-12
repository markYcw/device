DROP TABLE IF EXISTS ums_data;

CREATE TABLE ums_data(
id INT NOT NULL IDENTITY(1, 1) ,
dc INT DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_data IS '数据迁移表';
COMMENT ON COLUMN ums_data.id IS '数据迁移ID';
COMMENT ON COLUMN ums_data.dc IS '数据迁移标识';

