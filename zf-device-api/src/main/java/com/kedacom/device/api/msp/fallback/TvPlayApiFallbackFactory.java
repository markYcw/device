package com.kedacom.device.api.msp.fallback;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.avIntegration.response.tvplay.TvPlayOpenVO;
import com.kedacom.device.api.msp.TvPlayApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:54
 */
public class TvPlayApiFallbackFactory implements FallbackFactory<TvPlayApi> {

    private volatile TvPlayApi tvPlayApi;

    @Override
    public TvPlayApi create(Throwable throwable) {
        return new TvPlayApi() {
            @Override
            public BaseResult batchStart(BatchStartRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult batchStop(BatchStopRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult clear(TvPlayClearRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult style(TvPlayStyleRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<TvPlayOpenVO> open(TvPlayOpenRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult order(TvPlayOrderRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult action(TvPlayActionRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
