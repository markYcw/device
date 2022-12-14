package com.kedacom.device.core.service;

import com.kedacom.msp.request.tvplay.*;
import com.kedacom.acl.network.data.avIntegration.tvplay.TvPlayOpenResponse;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:48
 */
public interface TvPlayService {

    void batchStart(BatchStartRequest request);

    void batchStop(BatchStopRequest request);

    void clear(TvPlayClearRequest request);

    void style(TvPlayStyleRequest request);

    TvPlayOpenResponse open(TvPlayOpenRequest request);

    void order(TvPlayOrderRequest request);

    void action(TvPlayActionRequest request);

}
