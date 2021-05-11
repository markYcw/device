package com.kedacom.device.core.convert;

import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.avIntegration.vo.tvwall.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:38
 */
@Mapper(componentModel = "spring")
public interface TvWallConvert {

    TvWallConvert INSTANCE = Mappers.getMapper(TvWallConvert.class);

    TvWallListVO listResponseToListVO(TvWallListResponse response);

    TvWallLayoutVO layoutResponseToLayoutVO(TvWallLayoutResponse response);

    TvWallQueryPipelineVO queryPipelineResponseToQueryPipelineVO(TvWallQueryPipelineResponse response);

    TvWallConfigVO configResponseToConfigVO(TvWallConfigResponse response);

    TvWallPipelineBindVO pipelineBindResponseToPipelineBindVO(TvWallPipelineBindResponse response);
}
