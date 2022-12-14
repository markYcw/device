package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.ums.requestdto.UmsDeviceInfoAddRequestDto;
import com.kedacom.ums.requestdto.UmsDeviceInfoUpdateRequestDto;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectByIdResponseDto;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectResponseDto;
import com.kedacom.ums.responsedto.UmsSubDeviceInfoQueryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Mapper(componentModel = "spring")
public interface UmsDeviceConvert {

    UmsDeviceConvert INSTANCE = Mappers.getMapper(UmsDeviceConvert.class);

    /* ******** 请求参数的转换 ******** */

    /**
     * 新增统一平台信息
     *
     * @param requestDto
     * @return
     */
    DeviceInfoEntity convertDeviceInfoEntityForAdd(UmsDeviceInfoAddRequestDto requestDto);

    /**
     * 更新统一平台信息
     *
     * @param requestDto
     * @return
     */
    DeviceInfoEntity convertDeviceInfoEntityForUpdate(UmsDeviceInfoUpdateRequestDto requestDto);

    LoginRequest convertUmsDeviceInfoAddRequestVo(UmsDeviceInfoAddRequestDto requestDto);

    LoginRequest convertUmsDeviceInfoUpdateRequestVo(UmsDeviceInfoUpdateRequestDto requestDto);

    /* ******** 转换成响应参数 ******** */

    /**
     * ums实体转换响应参数类
     *
     * @param deviceInfoEntityList
     * @return
     */
    List<UmsDeviceInfoSelectResponseDto> convertUmsDeviceInfoSelectResponseDtoList(List<DeviceInfoEntity> deviceInfoEntityList);

    UmsDeviceInfoSelectByIdResponseDto concertUmsDeviceInfoSelectByIdResponseDto(DeviceInfoEntity deviceInfoEntity);

    @Mappings({@Mapping(source = "deviceStatus",target = "status"),
    @Mapping(source = "deviceModel",target = "model")})
    UmsSubDeviceInfoQueryResponseDto covertSubDeviceInfoQueryResponseDto(SubDeviceInfoEntity subDeviceInfoEntity);

    /**
     * 子设备信息转换响应参数类
     *
     * @param subDeviceInfoEntityList
     * @return
     */
    List<UmsSubDeviceInfoQueryResponseDto> convertUmsSubDeviceInfoQueryResponseDtoList(List<SubDeviceInfoEntity> subDeviceInfoEntityList);

    LoginRequest convertDeviceInfo(DeviceInfoEntity deviceInfoEntity);


}
