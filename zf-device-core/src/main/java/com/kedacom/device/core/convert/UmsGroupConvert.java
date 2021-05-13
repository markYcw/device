package com.kedacom.device.core.convert;

import com.kedacom.ums.entity.GroupInfoEntity;
import com.kedacom.ums.responsedto.SelectChildUmsGroupResponseDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupItemQueryResponseDto;
import org.mapstruct.Mapper;
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

}
