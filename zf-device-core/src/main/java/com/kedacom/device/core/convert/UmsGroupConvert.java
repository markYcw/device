package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.GroupInfoEntity;
import com.kedacom.device.ums.DeviceGroupVo;
import com.kedacom.ums.responsedto.SelectChildUmsGroupResponseDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupItemQueryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
@Mapper
public interface UmsGroupConvert {

    UmsGroupConvert INSTANCE = Mappers.getMapper(UmsGroupConvert.class);

    List<UmsScheduleGroupItemQueryResponseDto> convertUmsScheduleGroupItemQueryResponseDtoList(List<GroupInfoEntity> groupInfoEntityList);

    List<SelectChildUmsGroupResponseDto> convertSelectChildUmsGroupResponseDtoList(List<GroupInfoEntity> groupInfoEntityList);

    @Mappings({@Mapping(source = "id", target = "groupId"),
            @Mapping(source = "name",target = "groupName"),
            @Mapping(target = "id", ignore = true)})
    GroupInfoEntity groupVOToInfo(DeviceGroupVo groupVo);

    List<GroupInfoEntity> convertGroupInfoEntityList(List<DeviceGroupVo> list);

}
