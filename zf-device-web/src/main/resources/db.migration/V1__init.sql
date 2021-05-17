use bmp_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ums_new_device`;
CREATE TABLE `ums_new_device` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库Id',
  `session_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会话',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名称',
  `device_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备类型，关联ums_device_type表',
  `device_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '平台IP',
  `device_port` int(11) NULL DEFAULT NULL COMMENT '平台端口',
  `device_notify_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备状态订阅IP',
  `media_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '媒体调度服务IP',
  `media_port` int(11) NULL DEFAULT NULL COMMENT '媒体调度服务端口',
  `group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组Id，关联ums_new_group表',
  `kafka_addr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备状态订阅信息',
  `streaming_media_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流媒体服务IP',
  `streaming_media_port` int(11) NULL DEFAULT NULL COMMENT '流媒体服务端口',
  `streaming_media_rec_port` int(11) NULL DEFAULT NULL COMMENT '录像服务端口',
  `last_sync_third_device_time` varchar(125) DEFAULT NULL COMMENT '最近一次同步第三方服务子设备时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '统一设备平台信息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ums_new_device_type`;
CREATE TABLE `ums_new_device_type` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库Id',
  `code` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型编码',
  `value` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备类型',
  `device_type_desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备类型描述',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备类型表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ums_new_alarm_type`;
CREATE TABLE `ums_new_alarm_type`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库Id',
  `alarm_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报警类型标识',
  `alarm_desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报警类型描述',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '报警类型信息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ums_new_group`;
CREATE TABLE `ums_new_group`  (
  `id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库Id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组Id',
  `parent_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '父分组Id',
  `group_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组名称',
  `group_devid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组所在设备Id',
  `sort_index` int(11) DEFAULT '0' COMMENT '分组排序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分组信息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `ums_new_sub_device`;
CREATE TABLE `ums_new_sub_device`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备Id',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上级统一设备ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备名称',
  `device_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备类型',
  `gbid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '国标ID',
  `device_ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `manufactor_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '厂商名',
  `manufactor_code` int(11) DEFAULT NULL COMMENT '厂商代码',
  `group_id` varchar(256) DEFAULT NULL COMMENT '分组Id',
  `status` int(2) DEFAULT '1' COMMENT '设备状态',
  `longitude` decimal(10,7) DEFAULT NULL COMMENT '经度',
  `longitude_str` varchar(32) DEFAULT NULL COMMENT '经度字符串',
  `latitude` decimal(10,7) DEFAULT NULL COMMENT '纬度',
  `latitude_str` varchar(32) DEFAULT NULL COMMENT '纬度字符串',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `device_mod` int(2) DEFAULT '0' COMMENT '设备模式',
  `model` varchar(255) DEFAULT NULL COMMENT '型号',
  `civil_name` varchar(255) DEFAULT NULL COMMENT '行政区域',
  `department_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `maintain_man` varchar(255) DEFAULT NULL COMMENT '维护人',
  `maintain_contact` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `address` varchar(255) DEFAULT NULL COMMENT '安装地址',
  `install_date` datetime DEFAULT NULL COMMENT '安装时间',
  `domain_id` varchar(255) DEFAULT NULL COMMENT '域Id',
  `pinyin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备名称(拼音+首字母)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '子设备信息表' ROW_FORMAT = Dynamic;
