
use bmp_new_udms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE ums_new_ai_box ADD ab_min_face int(11) NOT NULL COMMENT '能识别的人脸最小像素值(默认为60)' After ab_password;
ALTER TABLE ums_new_ai_box ADD ab_max_face int(11) NOT NULL COMMENT '能识别的人脸最大像素值(默认为400)' After ab_min_face;