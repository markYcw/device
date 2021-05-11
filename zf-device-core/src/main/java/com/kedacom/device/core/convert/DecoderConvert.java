package com.kedacom.device.core.convert;

import com.kedacom.avIntegration.response.decoder.StyleQueryResponse;
import com.kedacom.avIntegration.vo.decoder.StyleQueryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:38
 */
@Mapper(componentModel = "spring")
public interface DecoderConvert {

    DecoderConvert INSTANCE = Mappers.getMapper(DecoderConvert.class);

    StyleQueryVO queryResponseToQueryVO(StyleQueryResponse response);

}
