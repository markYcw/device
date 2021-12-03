
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_mt`;
CREATE TABLE `ums_mt`
(
      `id`               int(11) NOT NULL COMMENT '终端标识',
      `name`             varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci default NULL COMMENT '终端名称',
      `ip`               varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端IP',
      `port`             int(11) NULL DEFAULT NULL COMMENT '终端端口',
      `username`         varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端登录账号',
      `password`         varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '终端登录密码',
      `mtid`             int(11) DEFAULT NULL COMMENT '注册返回ID',
      `devtype`          int(11) DEFAULT NULL COMMENT '终端设备版本，0:3.0; 1:5.0',
      `mtname`           varchar(128) DEFAULT NULL COMMENT '终端设备类型名称',
      `e164`             varchar(128) DEFAULT NULL COMMENT '终端e164号',
      `upuname`          varchar(128) DEFAULT NULL COMMENT 'upu名称',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '终端信息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ums_mt_type`;
CREATE TABLE `ums_mt_type`
(
      `id`               varchar(32)  NOT NULL COMMENT 'id',
      `mt_type`          int(11) NOT NULL COMMENT '终端设备类型',
      `mt_name`          varchar(128) NOT NULL COMMENT '终端设备名称',
      `mt_version`       int(3) NOT NULL COMMENT '终端设备版本，0:3.0; 1:5.0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '终端类型表' ROW_FORMAT = Dynamic;

INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('1', 1, 'PCMT桌面终端', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('2', 2, '8010', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('3', 3, '8010A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('4', 4, '8010Aplus', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('5', 5, '8010C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('6', 6, '8010C1', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('7', 7, 'IMT', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('8', 8, 'TS6610', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('9', 9, '8220B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('10', 10, '8220C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('11', 11, '8620A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('12', 12, 'TS6610E', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('13', 13, 'TS6210', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('14', 14, '8010A_2', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('15', 15, '8010A_4', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('16', 16, '8010A_8', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('17', 17, '7210', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('18', 18, '7610', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('19', 19, 'TS5610', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('20', 20, '8220A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('21', 21, '7810', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('22', 22, '7910', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('23', 23, '7620_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('24', 24, '7620_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('25', 25, '7820_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('26', 26, '7820_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('27', 27, '7920_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('28', 28, '7920_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('29', 29, 'KDV1000', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('30', 30, '7921_L', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('31', 31, '7921_H', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('32', 32, 'H600_LB', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('33', 33, 'H600_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('34', 34, 'H600_C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('35', 35, 'H700_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('36', 36, 'H700_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('37', 37, 'H700_C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('38', 38, 'H900_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('39', 39, 'H900_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('40', 40, 'H900_C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('41', 41, 'H600_LC', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('42', 42, 'H800_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('43', 43, 'H800_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('44', 44, 'H800_C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('45', 45, 'H800_TP', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('46', 46, 'H850_A', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('47', 47, 'H850_B', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('48', 48, 'H850_C', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('49', 49, 'H600_L_TP', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('50', 50, 'H600_TP', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('51', 51, 'H700_TP', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('52', 52, 'H850_TP', 0);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('53', 53, 'H900_TP', 0);

INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('54', 1, '桌面终端 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('55', 2, '移动终端ipad 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('56', 3, '移动终端iphone 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('59', 6, '8010C1', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('60', 7, '移动终端androidpad 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('61', 8, '移动终端androidphone 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('62', 9, '硬终端X500 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('63', 10, '硬终端X500 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('64', 11, '硬终端X500 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('65', 12, '硬终端X500 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('66', 13, '硬终端X500 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('67', 14, '硬终端X500 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('68', 15, '硬终端X500 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('69', 16, '桌面终端 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('70', 17, '移动终端ipad 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('71', 18, '移动终端iphone 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('72', 19, '移动终端androidphone 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('73', 20, '移动终端androidpad 租赁', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('74', 21, '硬终端 自建', 1);
INSERT INTO `ums_mt_type`(`id`, `mt_type`, `mt_name`, `mt_version`) VALUES ('75', 22, '移动终端TV盒子 租赁', 1);