package com.kedacom.device.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Mapper
public interface DeviceMapper extends BaseMapper<DeviceInfoEntity> {
}
