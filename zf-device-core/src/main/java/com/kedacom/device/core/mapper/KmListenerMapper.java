package com.kedacom.device.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kedacom.device.core.entity.KmListenerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * km设备订阅通知
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-19 17:12:24
 */
@Mapper
public interface KmListenerMapper extends BaseMapper<KmListenerEntity> {

    @Select("select * from km_listener where id > 0")
    List<KmListenerEntity> getAll();
}
