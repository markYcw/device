package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.deviceListener.RegisterListenerVo;
import org.mapstruct.Mapper;

/**
 * @author ycw
 * @create 2021/06/19 10:30
 */
@Mapper(componentModel = "spring")
public interface RegisterListenerConvert {

    KmListenerEntity convertToKmListenerEntity(RegisterListenerVo vo);

    RegisterListenerVo convertToVo(KmListenerEntity entity);
}
