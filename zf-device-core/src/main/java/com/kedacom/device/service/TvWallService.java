package com.kedacom.device.service;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.*;
import com.kedacom.avIntegration.response.*;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 11:02
 */
public interface TvWallService {

    BaseResult<TvWallListResponse> ls(TvWallListRequest request);

    BaseResult<TvWallLayoutResponse> layout(TvWallLayoutRequest request);

    BaseResult<TvWallQueryPipelineResponse> query(TvWallQueryPipelineRequest request);

    BaseResult<TvWallPipelineBindResponse> configBind(TvWallPipelineBindRequest request);

    BaseResult<TvWallConfigResponse> config(TvWallConfigRequest request);

    BaseResult delete(TvWallDeleteRequest request);

}
