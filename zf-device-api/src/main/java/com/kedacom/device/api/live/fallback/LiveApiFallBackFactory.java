package com.kedacom.device.api.live.fallback;

import com.kedacom.BaseResult;
import com.kedacom.cu.vo.*;
import com.kedacom.device.api.live.LiveApi;
import feign.hystrix.FallbackFactory;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
public class LiveApiFallBackFactory implements FallbackFactory<LiveApi> {

    @Override
    public LiveApi create(Throwable throwable) {
        return new LiveApi() {

            @Override
            public BaseResult<LiveStreamResponseVo> liveStream(LiveStreamRequestVo liveStreamRequestVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<VodStreamResponseVo> vodStream(VodStreamRequestVo vodStreamRequestVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<StreamResponseVo> stream(StreamRequestVo streamRequestVo) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }

}
