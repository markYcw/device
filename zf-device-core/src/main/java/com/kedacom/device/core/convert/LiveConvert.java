package com.kedacom.device.core.convert;

import com.kedacom.cu.dto.LiveStreamDto;
import com.kedacom.cu.dto.StreamDto;
import com.kedacom.cu.dto.VodStreamDto;
import com.kedacom.cu.vo.LiveStreamRequestVo;
import com.kedacom.cu.vo.StreamRequestVo;
import com.kedacom.cu.vo.VodStreamRequestVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Mapper(componentModel = "spring")
public interface LiveConvert {

    LiveConvert INSTANCE = Mappers.getMapper(LiveConvert.class);

    LiveStreamDto convertLiveStreamDto(LiveStreamRequestVo requestVo);

    VodStreamDto convertVodStreamDto(VodStreamRequestVo requestVo);

    StreamDto convertStreamDto(StreamRequestVo requestVo);

}
