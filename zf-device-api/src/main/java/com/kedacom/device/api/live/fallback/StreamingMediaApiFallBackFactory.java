package com.kedacom.device.api.live.fallback;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.live.StreamingMediaApi;
import com.kedacom.streamingMedia.request.StreamingMediaQueryDto;
import com.kedacom.streamingMedia.request.StreamingMediaVo;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
public class StreamingMediaApiFallBackFactory implements FallbackFactory<StreamingMediaApi> {

    @Override
    public StreamingMediaApi create(Throwable throwable) {
        return new StreamingMediaApi() {
            @Override
            public BaseResult<BasePage<StreamingMediaVo>> querySmList(StreamingMediaQueryDto streamingMediaQuery) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<StreamingMediaVo> querySm(Long id) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<StreamingMediaVo>> smList() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> saveSm(StreamingMediaVo streamingMediaVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> updateSm(StreamingMediaVo streamingMediaVo) {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
