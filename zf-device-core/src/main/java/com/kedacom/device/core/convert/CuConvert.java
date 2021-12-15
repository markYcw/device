package com.kedacom.device.core.convert;

import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.*;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.*;
import com.kedacom.device.cu.request.CuLoginRequest;
import com.kedacom.deviceListener.notify.cu.CuAlarmDTO;
import com.kedacom.deviceListener.notify.cu.RecVo;
import com.kedacom.deviceListener.notify.cu.SrcChsVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author ycw
 * @date: 2021/09/06 13:46
 * @description cu参数转换接口
 */
@Mapper(componentModel = "spring")
public interface CuConvert {

    @Mappings({@Mapping(target = "user",source = "username")})
    CuLoginRequest convertToCuLoginRequest(CuEntity cuEntity);

    @Mappings({@Mapping(target = "domainId",source = "modelType")})
    DevEntityVo convertToDevEntityVo(CuEntity cuEntity);

    CuEntity convertToCuEntity(DevEntityVo devEntityVo);

    ControlPtzDto convertControlPtzRequestDto(ControlPtzRequestDto controlPtzDto);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    PlayRecDto convertPlayRecDto(PlatRecStartVo platRecStartVo);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    PlayRecDto convertPlayRecDto(PlatRecStopVo platRecStopVo);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    PlayRecDto convertPlayRecDto(PuRecStartVo puRecStartVo);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    PlayRecDto convertPlayRecDto(PuRecStopVo puRecStopVo);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    OperateLockingRecDto convertOperateLockDto(OpenLockingRecRequestDto openLockingRequestDto);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    OperateLockingRecDto convertOperateLockDto(CancelLockingRecRequestDto cancelLockingRequestDto);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    QueryVideoCalendarDto convertQueryVideoCalendarDto(QueryVideoCalendarRequestDto queryVideoCalendarRequestDto);

    @Mappings({@Mapping(target = "chn",source = "chnId")})
    QueryVideoDto convertQueryVideoDto(QueryVideoRequestDto queryVideoRequestDto);

    CuGroupVo covertToCuGroupVo(PGroup pGroup);

    CuDeviceVo covertToCuDeviceVo(PDevice pDevice);

    CuChannelVo convertToCuChannelVo(SrcChn pChannel);

    CuAlarmDTO convertToCuAlarmDTO(Alarm alarm);

    SrcChsVo convertToSrcChsVo(SrcChns srcChns);

    RecVo convertToRecVo(Rec rec);

    QueryCuVideoVo convertToQueryCuVideoVo(QueryVideoInsideNotify notify);
}
