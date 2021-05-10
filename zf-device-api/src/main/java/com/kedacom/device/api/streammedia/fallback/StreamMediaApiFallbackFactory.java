package com.kedacom.device.api.streammedia.fallback;

import com.kedacom.device.api.streammedia.StreamMediaApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 10:24
 */
public class StreamMediaApiFallbackFactory implements FallbackFactory<StreamMediaApi> {
    @Override
    public StreamMediaApi create(Throwable throwable) {
        return new StreamMediaApi() {
        };
    }
}
