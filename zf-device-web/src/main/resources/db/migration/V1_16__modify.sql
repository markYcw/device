
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_ai_box DROP COLUMN ab_min_face;
ALTER TABLE ums_new_ai_box DROP COLUMN ab_max_face;

DROP TABLE IF EXISTS `ums_new_ai_box_threshold`;
CREATE TABLE `ums_new_ai_box_threshold`
(
    `id`                          varchar(32)  NOT NULL COMMENT 'id',
    `ab_id`                       varchar(32) NOT NULL COMMENT '设备ID',
    `ab_min_face`                 int(11) NOT NULL COMMENT '能识别的人脸最小像素值(默认为60)',
    `ab_max_face`                 int(11) NOT NULL COMMENT '能识别的人脸最大像素值(默认为60)',
    `create_time`                 datetime(0)  DEFAULT NULL COMMENT '创建时间',
    `update_time`                 datetime(0)  DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AI_Box信息表' ROW_FORMAT = Dynamic;