package com.kedacom.device.core.service;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:47
 */
public interface TvWallService {

    TvWallListResponse ls(TvWallListRequest request);

    TvWallLayoutResponse layout(TvWallLayoutRequest request);

    TvWallQueryPipelineResponse query(TvWallQueryPipelineRequest request);

    TvWallConfigResponse config(TvWallConfigRequest request);

    TvWallPipelineBindResponse configBind(TvWallPipelineBindRequest request);

    void delete(TvWallDeleteRequest request);

}
