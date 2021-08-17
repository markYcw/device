package com.kedacom.device.core.convert;

import com.kedacom.acl.network.ums.responsevo.UmsAlarmTypeQueryResponseVo;
import com.kedacom.device.core.entity.AlarmTypeEntity;
import com.kedacom.ums.responsedto.UmsAlarmTypeQueryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Mapper(componentModel = "spring")
public interface UmsAlarmTypeConvert {

    UmsAlarmTypeConvert INSTANCE = Mappers.getMapper(UmsAlarmTypeConvert.class);

    List<UmsAlarmTypeQueryResponseDto> convertUmsAlarmTypeQueryResponseDtoList(List<AlarmTypeEntity> alarmTypeEntityList);

    List<UmsAlarmTypeQueryResponseDto> convertUmsAlarmTypeQueryResponseVoList(List<UmsAlarmTypeQueryResponseVo> alarmTypeQueryResponseVoList);

    List<AlarmTypeEntity> convertAlarmTypeEntityList(List<UmsAlarmTypeQueryResponseVo> alarmTypeQueryResponseVoList);

}
