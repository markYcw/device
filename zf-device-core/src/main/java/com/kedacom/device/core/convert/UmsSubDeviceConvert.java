package com.kedacom.device.core.convert;

import com.kedacom.acl.network.ums.responsevo.SubDeviceInfoResponseVo;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.ums.request.SetMediaRequest;
import com.kedacom.ums.requestdto.UmsScheduleGroupSetMediaRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Mapper
public interface UmsSubDeviceConvert {

    UmsSubDeviceConvert INSTANCE = Mappers.getMapper(UmsSubDeviceConvert.class);

    List<SubDeviceInfoEntity> convertSubDeviceInfoEntityList(List<SubDeviceInfoResponseVo> responseVoList);

    SetMediaRequest convertSetMediaRequest(UmsScheduleGroupSetMediaRequestDto requestDto);

}
