package com.kedacom.device.core.convert;

import com.kedacom.power.entity.PowerConfigEntity;
import com.kedacom.power.entity.PowerDeviceEntity;
import com.kedacom.power.vo.PowerDeviceListRspVo;
import com.kedacom.power.vo.PowerPortListVo;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * @ClassName PowerPortConvert
 * @Description TODO
 * @Author zlf
 * @Date 2021/5/26 11:28
 */
@Mapper(componentModel = "spring")
public interface PowerPortConvert {

    List<PowerPortListVo> convertToPowerPortListVo(List<PowerConfigEntity> powerEntity);

    List<PowerDeviceListRspVo> convertToPowerDeviceListRspVo(List<PowerDeviceEntity> powerDeviceEntities);

    PowerDeviceListRspVo convertToPowerDeviceListRspVo(PowerDeviceEntity powerDeviceEntities);

}
