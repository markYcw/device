package com.kedacom.device.api.msp.fallback;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.vo.auth.SystemLoginVO;
import com.kedacom.avIntegration.vo.auth.SystemVersionVO;
import com.kedacom.device.api.msp.SystemAuthApi;
import feign.hystrix.FallbackFactory;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
public class SystemAuthApiFallbackFactory implements FallbackFactory<SystemAuthApi> {

    private volatile SystemAuthApi systemAuthApi;

    @Override
    public SystemAuthApi create(Throwable cause) {
        return new SystemAuthApi() {
            @Override
            public BaseResult<SystemLoginVO> login(SystemLoginRequest request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult keepAlive(RequestBaseParam request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult<SystemVersionVO> version(RequestBaseParam request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }

            @Override
            public BaseResult logout(RequestBaseParam request) {
                return BaseResult.failed("服务出错，请稍后重试");
            }
        };
    }
}
