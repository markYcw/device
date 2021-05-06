package com.kedacom.device.msp;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.SystemLoginRequest;
import com.kedacom.avIntegration.response.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.SystemLogOutResponse;
import com.kedacom.avIntegration.response.SystemLoginResponse;
import com.kedacom.avIntegration.response.SystemVersionResponse;
import com.kedacom.device.msp.fallback.AuthFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 17:08
 */
@FeignClient(name = "msp",
        contextId = "msp-auth",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/system",
        fallbackFactory = AuthFeignFallbackFactory.class)
public interface AuthFeign {
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
