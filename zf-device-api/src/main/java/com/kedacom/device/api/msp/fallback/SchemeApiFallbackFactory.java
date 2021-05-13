package com.kedacom.device.api.msp.fallback;


import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.avIntegration.response.scheme.SchemeConfigVO;
import com.kedacom.avIntegration.response.scheme.SchemeQueryVO;
import com.kedacom.device.api.msp.SchemeApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
public class SchemeApiFallbackFactory implements FallbackFactory<SchemeApi> {

    private volatile SchemeApi SchemeApi;

    @Override
    public SchemeApi create(Throwable throwable) {
        return new SchemeApi() {
            @Override
            public BaseResult<SchemeConfigVO> config(SchemeConfigRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<SchemeQueryVO> query(SchemeQueryRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
