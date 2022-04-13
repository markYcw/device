package com.kedacom.device.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kedacom.power.entity.PowerDeviceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 电源设备信息表 Mapper 接口
 * </p>
 *
 * @author zhanglongfei
 * @since 2021-05-27
 */
@Mapper
public interface PowerDeviceMapper extends BaseMapper<PowerDeviceEntity> {

    /**
     * @Description 修改电源设备状态
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    void updateStatus(@Param(value = "ids") List<Integer> ids, @Param(value = "state") int state);

}
