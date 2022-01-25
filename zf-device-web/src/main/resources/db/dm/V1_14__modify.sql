ALTER TABLE ums_new_ai_box ADD ab_min_face INT NOT NULL ;
COMMENT ON COLUMN ums_new_ai_box.ab_min_face IS '能识别的人脸最小像素值(默认为60)';

ALTER TABLE ums_new_ai_box ADD ab_max_face INT NOT NULL ;
COMMENT ON COLUMN ums_new_ai_box.ab_max_face IS '能识别的人脸最大像素值(默认为400)';

