package com.kedacom.device.core.convert;

import com.kedacom.newMedia.dto.NewMediaLoginDto;
import com.kedacom.newMedia.entity.NewMediaEntity;
import com.kedacom.newMedia.pojo.NMDevice;
import com.kedacom.ums.responsedto.UmsSubDeviceInfoQueryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * @author ycw
 * @date: 2022/04/02 13:46
 * @description newMedia参数转换接口
 */
@Mapper(componentModel = "spring")
public interface NewMediaConvert {


    NewMediaLoginDto convertToNewMediaLoginDto(NewMediaEntity entity);

    @Mappings({@Mapping(target = "deviceId",source = "id"),@Mapping(target ="gbid",source = "gbId"),@Mapping(target ="deviceIp",source = "ipv4"),@Mapping(target ="manufactorName",source = "manufactorCodeName"),@Mapping(target ="longitude",source = "latitude"),@Mapping(target ="longitudeStr",source = "latitudeStr"),@Mapping(target ="latitude",source = "longitude"),@Mapping(target ="latitudeStr",source = "longitudeStr"),@Mapping(target ="civilName",source = "civilCodeName"),@Mapping(target ="departmentName",source = "departmentCodeName"),@Mapping(target ="maintainMan",source = "mgtMan"),@Mapping(target ="maintainContact",source = "mgtUnitContact"),@Mapping(target ="maintainContact",source = "mgtUnitContact")})
    UmsSubDeviceInfoQueryResponseDto convertToPage(NMDevice device);



}
