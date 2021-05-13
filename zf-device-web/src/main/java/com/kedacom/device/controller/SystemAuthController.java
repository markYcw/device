package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.RequestBaseParam;
import com.kedacom.avIntegration.request.auth.SystemLoginRequest;
import com.kedacom.acl.network.data.avIntegration.auth.SystemLoginResponse;
import com.kedacom.acl.network.data.avIntegration.auth.SystemVersionResponse;
import com.kedacom.avIntegration.response.auth.SystemLoginVO;
import com.kedacom.avIntegration.response.auth.SystemVersionVO;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.convert.SystemAuthConvert;
import com.kedacom.device.core.service.SystemAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:06
 */
@RestController
@RequestMapping("/api/v1/manage/system")
@Api(value = "鉴权", tags = "鉴权")
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
    public BaseResult<SystemLoginVO> login(@Valid @RequestBody SystemLoginRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        SystemLoginResponse response = systemAuthService.login(request);
        SystemLoginVO systemLoginVO = SystemAuthConvert.INSTANCE.loginResponseToLoginVo(response);
        return BaseResult.succeed(systemLoginVO);
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
    public BaseResult keepAlive(@Valid @RequestBody RequestBaseParam request, BindingResult br) {
        ValidUtils.paramValid(br);

        systemAuthService.keepAlive(request);
        return BaseResult.succeed("保活成功");
    }

    /**
     * 显控统一服务API版本号
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("version")
    @ApiOperation("显控统一服务API版本号")
    public BaseResult<SystemVersionVO> version(@Valid @RequestBody RequestBaseParam request, BindingResult br) {
        ValidUtils.paramValid(br);

        SystemVersionResponse response = systemAuthService.version(request);
        SystemVersionVO systemVersionVO = SystemAuthConvert.INSTANCE.versionResponseToLoginVo(response);
        return BaseResult.succeed(systemVersionVO);
    }

    /**
     * 退出显控统一服务，关闭令牌
     *
     * @param request 请求
     * @return 响应
     */
    @PostMapping("logout")
    @ApiOperation("退出显控统一服务，关闭令牌")
    public BaseResult logout(@Valid @RequestBody RequestBaseParam request, BindingResult br) {
        ValidUtils.paramValid(br);

        systemAuthService.logout(request);
        return BaseResult.succeed("登出成功");
    }

}
