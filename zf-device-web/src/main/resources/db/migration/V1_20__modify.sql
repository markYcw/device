
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for km_svr_device_type
-- ----------------------------
DROP TABLE IF EXISTS `km_svr_type`;
CREATE TABLE `km_svr_type` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'SVR设备类型数据库Id',
                                      `type` varchar(50) NOT NULL COMMENT 'SVR设备类型',
                                      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='SVR设备类型表';


