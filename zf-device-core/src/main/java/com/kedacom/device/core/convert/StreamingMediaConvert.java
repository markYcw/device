package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.StreamingMediaEntity;
import com.kedacom.streamingMedia.request.StreamingMediaVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
@Mapper(componentModel = "spring")
public interface StreamingMediaConvert {

    StreamingMediaConvert INSTANCE = Mappers.getMapper(StreamingMediaConvert.class);

    @Mappings({@Mapping(target = "smName", source = "name"),
            @Mapping(target = "smIp", source = "ip"),
            @Mapping(target = "smPort", source = "port")})
    StreamingMediaEntity convertToStreamingMediaEntity(StreamingMediaVo streamingMediaVo);

    @Mappings({@Mapping(target = "name", source = "smName"),
            @Mapping(target = "ip", source = "smIp"),
            @Mapping(target = "port", source = "smPort")})
    StreamingMediaVo convertToStreamingMediaVo(StreamingMediaEntity streamingMediaEntity);

    List<StreamingMediaVo> convertToStreamingMediaVoList(List<StreamingMediaEntity> streamingMediaEntityList);

}
