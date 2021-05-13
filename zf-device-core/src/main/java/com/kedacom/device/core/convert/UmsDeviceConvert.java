package com.kedacom.device.core.convert;

import com.kedacom.acl.network.ums.requestvo.LoginPlatformRequestVo;
import com.kedacom.ums.entity.DeviceInfoEntity;
import com.kedacom.ums.entity.SubDeviceInfoEntity;
import com.kedacom.ums.requestdto.UmsDeviceInfoAddRequestDto;
import com.kedacom.ums.requestdto.UmsDeviceInfoUpdateRequestDto;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectResponseDto;
import com.kedacom.ums.responsedto.UmsSubDeviceInfoQueryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Mapper
public interface UmsDeviceConvert {

    UmsDeviceConvert INSTANCE = Mappers.getMapper(UmsDeviceConvert.class);

    /* ******** 请求参数的转换 ******** */
    /**
     * 新增统一平台信息
     * @param requestDto
     * @return
     */
    DeviceInfoEntity convertDeviceInfoEntityForAdd(UmsDeviceInfoAddRequestDto requestDto);

    /**
     * 更新统一平台信息
     * @param requestDto
     * @return
     */
    DeviceInfoEntity convertDeviceInfoEntityForUpdate(UmsDeviceInfoUpdateRequestDto requestDto);

    LoginPlatformRequestVo convertUmsDeviceInfoAddRequestVo(UmsDeviceInfoAddRequestDto requestDto);

    LoginPlatformRequestVo convertUmsDeviceInfoUpdateRequestVo(UmsDeviceInfoUpdateRequestDto requestDto);

    /* ******** 转换成响应参数 ******** */
    /**
     * ums实体转换响应参数类
     * @param deviceInfoEntityList
     * @return
     */
    List<UmsDeviceInfoSelectResponseDto> convertUmsDeviceInfoSelectResponseDtoList(List<DeviceInfoEntity> deviceInfoEntityList);

    /**
     * 子设备信息转换响应参数类
     * @param subDeviceInfoEntityList
     * @return
     */
    List<UmsSubDeviceInfoQueryResponseDto> convertUmsSubDeviceInfoQueryResponseDtoList(List<SubDeviceInfoEntity> subDeviceInfoEntityList);

}
