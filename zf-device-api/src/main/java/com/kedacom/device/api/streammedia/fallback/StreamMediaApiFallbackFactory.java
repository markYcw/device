package com.kedacom.device.api.streammedia.fallback;

import com.kedacom.BaseResult;
import com.kedacom.device.api.streammedia.StreamMediaApi;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 10:24
 */
public class StreamMediaApiFallbackFactory implements FallbackFactory<StreamMediaApi> {
    @Override
    public StreamMediaApi create(Throwable throwable) {
        return new StreamMediaApi() {
            @Override
            public BaseResult<StartRecResponseVO> startRec(StartRecDTO startrecDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> stopRec(StopRecDTO stoprecDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<QueryRecResponseVO> queryRec(QueryRecDTO queryrecDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<StartAudioMixResponseVO> startAudioMix(StartAudioMixDTO startAudioMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> stopAudioMix(StopAudioMixDTO stopAudioMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> updateAudioMix(UpdateAudioMixDTO updateAudioMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<List<String>> queryAllAudioMix(QueryAllAudioMixDTO queryAllAudioMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<QueryAudioMixResponseVO> queryAudioMix(QueryAudioMixDTO queryAudioMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<StartVideoMixResponseVO> startVideoMix(StartVideoMixDTO startVideoMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> stopVideoMix(StopVideoMixDTO stopVideoMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> updateVideoMix(UpdateVideoMixDTO updateVideoMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<QueryAllAudioMixVO> queryAllVideoMix(QueryAllVideoMixDTO queryAllVideoMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<QueryVideoMixResponseVO> queryVideoMix(QueryVideoMixDTO queryVideoMixDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> sendTransData(SendTransDataDTO sendTransDataDTO) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
