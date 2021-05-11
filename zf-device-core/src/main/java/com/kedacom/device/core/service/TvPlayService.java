package com.kedacom.device.core.service;

import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.avIntegration.response.tvplay.*;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:48
 */
public interface TvPlayService {

    BatchStartResponse batchStart(BatchStartRequest request);

    BatchStopResponse batchStop(BatchStopRequest request);

    TvPlayClearResponse clear(TvPlayClearRequest request);

    TvPlayStyleResponse style(TvPlayStyleRequest request);

    TvPlayOpenResponse open(TvPlayOpenRequest request);

    TvPlayOrderResponse order(TvPlayOrderRequest request);

    TvPlayActionResponse action(TvPlayActionRequest request);

}
