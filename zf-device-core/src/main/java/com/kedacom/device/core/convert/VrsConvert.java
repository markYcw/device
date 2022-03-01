package com.kedacom.device.core.convert;


import com.kedacom.device.vs.request.VsLoginRequest;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.RecInfoHttpVo;
import com.kedacom.vs.vo.RecInfoVo;
import com.kedacom.vs.vo.VrsVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author ycw
 * @create 2021/05/17 15:53
 */
@Mapper(componentModel = "spring")
public interface VrsConvert {

    VsEntity convertToVrsEntity(VrsVo vrsVo);

    VrsVo convertToVrsVo(VsEntity vrsEntity);

    VsLoginRequest convertToVsLogin(VsEntity entity);

    @Mappings({@Mapping(target = "url",source = "httpUrl"),@Mapping(target = "starttime",source = "createTime")})
    RecInfoHttpVo convertToRecInfoHttpVo(RecInfoVo vo);

}
