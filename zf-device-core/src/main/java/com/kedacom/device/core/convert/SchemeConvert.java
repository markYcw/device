package com.kedacom.device.core.convert;

import com.kedacom.acl.network.data.avIntegration.scheme.SchemeConfigResponse;
import com.kedacom.acl.network.data.avIntegration.scheme.SchemeQueryResponse;
import com.kedacom.msp.response.scheme.SchemeConfigVO;
import com.kedacom.msp.response.scheme.SchemeQueryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:38
 */
@Mapper(componentModel = "spring")
public interface SchemeConvert {

    SchemeConvert INSTANCE = Mappers.getMapper(SchemeConvert.class);

    SchemeConfigVO configResponseToConfigVO(SchemeConfigResponse response);

    SchemeQueryVO queryResponseToQueryVO(SchemeQueryResponse response);

}
