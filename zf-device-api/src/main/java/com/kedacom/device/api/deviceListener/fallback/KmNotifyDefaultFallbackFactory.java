package com.kedacom.device.api.deviceListener.fallback;

import com.kedacom.BaseResult;
import com.kedacom.device.api.deviceListener.KmNotifyApi;
import com.kedacom.deviceListener.RegisterListenerVo;
import feign.hystrix.FallbackFactory;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 17:19
 * @description
 */
public class KmNotifyDefaultFallbackFactory implements FallbackFactory<KmNotifyApi> {
    @Override
    public KmNotifyApi create(Throwable throwable) {
        return new KmNotifyApi() {
            @Override
            public BaseResult<RegisterListenerVo> registerListener(RegisterListenerVo registerUrlVo) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<Boolean> UnRegisterListener(RegisterListenerVo registerUrlVo) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
