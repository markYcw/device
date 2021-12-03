
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