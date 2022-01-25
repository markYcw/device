DROP TABLE IF EXISTS ums_mt;

CREATE TABLE ums_mt(
id INT NOT NULL IDENTITY(1, 1) ,
name VARCHAR(12 CHAR) DEFAULT NULL ,
ip VARCHAR(50 CHAR) NOT NULL ,
port INT DEFAULT NULL ,
username VARCHAR(24 CHAR) NOT NULL ,
password VARCHAR(24 CHAR) NOT NULL ,
mtid INT DEFAULT NULL ,
devtype INT DEFAULT NULL ,
mtname VARCHAR(128 CHAR) DEFAULT NULL ,
e164 VARCHAR(128 CHAR) DEFAULT NULL ,
upuname VARCHAR(128 CHAR) DEFAULT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_mt IS '终端信息表';
COMMENT ON COLUMN ums_mt.id IS '终端标识';
COMMENT ON COLUMN ums_mt.name IS '终端名称';
COMMENT ON COLUMN ums_mt.ip IS '终端IP';
COMMENT ON COLUMN ums_mt.port IS '终端端口';
COMMENT ON COLUMN ums_mt.username IS '终端登录账号';
COMMENT ON COLUMN ums_mt.password IS '终端登录密码';
COMMENT ON COLUMN ums_mt.mtid IS '注册返回ID';
COMMENT ON COLUMN ums_mt.devtype IS '终端设备版本，0:3.0;1:5.0';
COMMENT ON COLUMN ums_mt.mtname IS '终端设备类型名称';
COMMENT ON COLUMN ums_mt.e164 IS '终端e164号';
COMMENT ON COLUMN ums_mt.upuname IS 'upu名称';

DROP TABLE IF EXISTS ums_mt_type;

CREATE TABLE ums_mt_type(
id INT NOT NULL ,
mt_type INT NOT NULL ,
mt_name VARCHAR(128 CHAR) NOT NULL ,
mt_version INT NOT NULL ,
PRIMARY KEY (id) 
);
COMMENT ON TABLE ums_mt_type IS '终端类型表';
COMMENT ON COLUMN ums_mt_type.id IS 'id';
COMMENT ON COLUMN ums_mt_type.mt_type IS '终端设备类型';
COMMENT ON COLUMN ums_mt_type.mt_name IS '终端设备名称';
COMMENT ON COLUMN ums_mt_type.mt_version IS '终端设备版本，0:3.0;1:5.0';

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (1, 1, 'PCMT桌面终端', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (2, 2, '8010', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (3, 3, '8010A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (4, 4, '8010Aplus', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (5, 5, '8010C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (6, 6, '8010C1', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (7, 7, 'IMT', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (8, 8, 'TS6610', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (9, 9, '8220B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (10, 10, '8220C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (11, 11, '8620A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (12, 12, 'TS6610E', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (13, 13, 'TS6210', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (14, 14, '8010A_2', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (15, 15, '8010A_4', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (16, 16, '8010A_8', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (17, 17, '7210', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (18, 18, '7610', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (19, 19, 'TS5610', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (20, 20, '8220A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (21, 21, '7810', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (22, 22, '7910', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (23, 23, '7620_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (24, 24, '7620_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (25, 25, '7820_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (26, 26, '7820_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (27, 27, '7920_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (28, 28, '7920_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (29, 29, 'KDV1000', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (30, 30, '7921_L', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (31, 31, '7921_H', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (32, 32, 'H600_LB', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (33, 33, 'H600_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (34, 34, 'H600_C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (35, 35, 'H700_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (36, 36, 'H700_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (37, 37, 'H700_C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (38, 38, 'H900_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (39, 39, 'H900_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (40, 40, 'H900_C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (41, 41, 'H600_LC', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (42, 42, 'H800_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (43, 43, 'H800_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (44, 44, 'H800_C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (45, 45, 'H800_TP', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (46, 46, 'H850_A', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (47, 47, 'H850_B', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (48, 48, 'H850_C', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (49, 49, 'H600_L_TP', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (50, 50, 'H600_TP', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (51, 51, 'H700_TP', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (52, 52, 'H850_TP', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (53, 53, 'H900_TP', 0);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (54, 1, '桌面终端 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (55, 2, '移动终端ipad 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (56, 3, '移动终端iphone 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (59, 6, '8010C1', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (60, 7, '移动终端androidpad 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (61, 8, '移动终端androidphone 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (62, 9, '硬终端X500 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (63, 10, '硬终端X500 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (64, 11, '硬终端X500 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (65, 12, '硬终端X500 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (66, 13, '硬终端X500 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (67, 14, '硬终端X500 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (68, 15, '硬终端X500 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (69, 16, '桌面终端 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (70, 17, '移动终端ipad 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (71, 18, '移动终端iphone 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (72, 19, '移动终端androidphone 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (73, 20, '移动终端androidpad 租赁', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (74, 21, '硬终端 自建', 1);

INSERT INTO ums_mt_type(id, mt_type, mt_name, mt_version) VALUES (75, 22, '移动终端TV盒子 租赁', 1);

