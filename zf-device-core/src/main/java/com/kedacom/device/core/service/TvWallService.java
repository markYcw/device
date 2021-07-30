package com.kedacom.device.core.service;

import com.kedacom.acl.network.data.avIntegration.tvwall.*;
import com.kedacom.msp.request.tvwall.*;

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
