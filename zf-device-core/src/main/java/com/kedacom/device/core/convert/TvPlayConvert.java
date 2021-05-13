package com.kedacom.device.core.convert;

import com.kedacom.acl.network.data.avIntegration.tvplay.TvPlayOpenResponse;
import com.kedacom.avIntegration.response.tvplay.TvPlayOpenVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:38
 */
@Mapper(componentModel = "spring")
public interface TvPlayConvert {

    TvPlayConvert INSTANCE = Mappers.getMapper(TvPlayConvert.class);

    TvPlayOpenVO openResponseToOpenVO(TvPlayOpenResponse response);

}
