package com.kedacom.device.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kedacom.data.entity.DataEntity;
import com.kedacom.vs.entity.VsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * data数据迁移服务
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2022-04-28 17:12:24
 */
@Mapper
public interface DataMapper extends BaseMapper<DataEntity> {
}
