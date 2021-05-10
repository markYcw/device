package com.kedacom.device.api.msp;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.api.msp.fallback.SystemAuthApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
@FeignClient(contextId = "systemAuthApi", path = "/api/v1/manage/system", fallbackFactory = SystemAuthApiFallbackFactory.class)
public interface SystemAuthApi {
    /**
     * 登录显控服务
     *
     * @param request 登录请求
     * @return 登录响应
     */
    @PostMapping("login")
    SystemLoginResponse login(@RequestBody SystemLoginRequest request);

    /**
     * 保活
     * 成功登陆后，用户有效期为30m，需手动调用该接口保持心跳
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("keepalive")
    SystemKeepAliveResponse keepAlive(@RequestBody RequestBaseParam request);

    /**
     * 显控统一服务API版本号
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("version")
    SystemVersionResponse version(@RequestBody RequestBaseParam request);

    /**
     * 退出显控统一服务，关闭令牌
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("logout")
    SystemLogOutResponse logout(@RequestBody RequestBaseParam request);

}