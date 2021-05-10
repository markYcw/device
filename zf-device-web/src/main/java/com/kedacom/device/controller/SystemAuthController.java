package com.kedacom.device.controller;

import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.avIntegration.response.auth.SystemKeepAliveResponse;
import com.kedacom.avIntegration.response.auth.SystemLogOutResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.device.core.service.SystemAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:06
 */
@RestController
@RequestMapping("/api/v1/manage/system")
@Api(value = "鉴权")
@Slf4j
public class SystemAuthController {

    @Autowired
    private SystemAuthService systemAuthService;

    /**
     * 登录显控服务
     *
     * @param request 登录请求
     * @return 登录响应
     */
    @PostMapping("login")
    @ApiOperation("登录显控统一服务，获取令牌")
    public SystemLoginResponse login(@RequestBody SystemLoginRequest request) {
        return systemAuthService.login(request);
    }

    /**
     * 保活
     * 成功登陆后，用户有效期为30m，需手动调用该接口保持心跳
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("keepalive")
    @ApiOperation("成功登陆后，用户有效期为30m，需手动调用该接口保持心跳")
    public SystemKeepAliveResponse keepAlive(@RequestBody RequestBaseParam request) {
        return systemAuthService.keepAlive(request);
    }

    /**
     * 显控统一服务API版本号
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("version")
    @ApiOperation("显控统一服务API版本号")
    public SystemVersionResponse version(@RequestBody RequestBaseParam request) {
        return systemAuthService.version(request);
    }

    /**
     * 退出显控统一服务，关闭令牌
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("logout")
    @ApiOperation("退出显控统一服务，关闭令牌")
    public SystemLogOutResponse logout(@RequestBody RequestBaseParam request) {
        return systemAuthService.logout(request);
    }

}
