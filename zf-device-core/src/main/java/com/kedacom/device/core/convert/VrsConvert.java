package com.kedacom.device.core.convert;


import com.kedacom.device.vs.request.VsLoginRequest;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.VrsVo;
import org.mapstruct.Mapper;

/**
 * @author ycw
 * @create 2021/05/17 15:53
 */
@Mapper(componentModel = "spring")
public interface VrsConvert {

    VsEntity convertToVrsEntity(VrsVo vrsVo);

    VrsVo convertToVrsVo(VsEntity vrsEntity);

    VsLoginRequest convertToVsLogin(VsEntity entity);

}
