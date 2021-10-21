package com.kedacom.device.core.convert;

import com.kedacom.aiBox.request.AddOrUpdateRequestDto;
import com.kedacom.aiBox.response.QueryListResponseDto;
import com.kedacom.aiBox.response.SelectPageResponseDto;
import com.kedacom.device.core.entity.AiBoxEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/15
 */
@Mapper(componentModel = "spring")
public interface AiBoxConvert {

    AiBoxConvert INSTANCE = Mappers.getMapper(AiBoxConvert.class);

    /**
     * 转换分页查询AI-Box设备信息列表返回参数类
     * @param aiBoxEntity
     * @return
     */
    SelectPageResponseDto convertSelectPageResponseDto(AiBoxEntity aiBoxEntity);

    /**
     * 转换分页查询AI-Box设备信息列表返回参数类集合
     * @param aiBoxEntityList
     * @return
     */
    List<SelectPageResponseDto> convertSelectPageResponseDtoList(List<AiBoxEntity> aiBoxEntityList);

    /**
     * 转换查询AI-Box设备信息集合返回参数类
     * @param aiBoxEntity
     * @return
     */
    QueryListResponseDto convertQueryListResponseDto(AiBoxEntity aiBoxEntity);

    /**
     * 转换查询AI-Box设备信息集合返回参数类集合
     * @param aiBoxEntityList
     * @return
     */
    List<QueryListResponseDto> convertQueryListResponseDtoList(List<AiBoxEntity> aiBoxEntityList);

    /**
     * 新增/修改AI-Box设备信息请求参数类转换实体类
     * @param requestDto
     * @return
     */
    AiBoxEntity convertAiBoxEntity(AddOrUpdateRequestDto requestDto);

}
