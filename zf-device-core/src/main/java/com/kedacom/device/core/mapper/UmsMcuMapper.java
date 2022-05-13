package com.kedacom.device.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kedacom.mp.mcu.entity.McuEntity;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会议平台
 * 
 * @author hxj
 * @email hexijian@kedacom.com
 * @date 2021-08-12 10:19:30
 */
@Mapper
public interface UmsMcuMapper extends BaseMapper<McuEntity> {
	
}
