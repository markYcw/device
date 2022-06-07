package com.kedacom.device.core.convert;

import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.LanDevice;
import com.kedacom.power.entity.PowerDeviceEntity;
import com.kedacom.power.vo.PowerLanConfigVO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * @author hexijian
 * @date 2022/6/7
 */
@Mapper(componentModel = "spring")
public interface ControlPowerConvert {

    Set<LanDevice> convertDevices(Set<Device> devices);

    PowerLanConfigVO covertEntityToVO(PowerDeviceEntity entity);

}
