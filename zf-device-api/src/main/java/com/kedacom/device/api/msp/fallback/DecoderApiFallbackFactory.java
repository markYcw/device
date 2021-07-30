package com.kedacom.device.api.msp.fallback;

import com.kedacom.BaseResult;
import com.kedacom.msp.request.decoder.OsdConfigRequest;
import com.kedacom.msp.request.decoder.OsdDeleteRequest;
import com.kedacom.msp.request.decoder.StyleConfigRequest;
import com.kedacom.msp.request.decoder.StyleQueryRequest;
import com.kedacom.msp.response.decoder.StyleQueryVO;
import com.kedacom.device.api.msp.DecoderApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 10:20
 */
public class DecoderApiFallbackFactory implements FallbackFactory<DecoderApi> {

    private volatile DecoderApi decoderApi;

    @Override
    public DecoderApi create(Throwable throwable) {
        return new DecoderApi() {
            @Override
            public BaseResult osdConfig(OsdConfigRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult osdDelete(OsdDeleteRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<StyleQueryVO> styleQuery(StyleQueryRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult styleConfig(StyleConfigRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
