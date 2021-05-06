package com.kedacom.device.controller;


import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.DisplayControlBaseDTO;
import com.kedacom.avIntegration.request.DisplayControlLoginRequest;
import com.kedacom.avIntegration.response.DisplayControlLoginResponse;
import com.kedacom.avIntegration.response.DisplayControlVersionResponse;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.service.DisplayAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:03
 */
@RestController
@RequestMapping("/api/v1/manage/system")
@Api(value = "显控服务")
public class DisplayAuthController {

    @Resource
    private DisplayAuthService displayAuthService;

    @PostMapping("login")
    @ApiOperation("登录显控统一服务，获取令牌")
    public BaseResult<DisplayControlLoginResponse> login(@Valid @RequestBody DisplayControlLoginRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return displayAuthService.login(request);
    }

    @PostMapping("keepalive")
    @ApiOperation("成功登陆后，用户有效期为30m，需手动调用该接口保持心跳")
    public BaseResult keepalive(@Valid @RequestBody DisplayControlBaseDTO baseDTO, BindingResult br) {
        ValidUtils.paramValid(br);
        return displayAuthService.keepalive(baseDTO);
    }

    @PostMapping("version")
    @ApiOperation("显控统一服务API版本号")
    public BaseResult<DisplayControlVersionResponse> version(@Valid @RequestBody DisplayControlBaseDTO baseDTO, BindingResult br) {
        ValidUtils.paramValid(br);
        return displayAuthService.version(baseDTO);
    }

    @PostMapping("logout")
    @ApiOperation("退出显控统一服务，关闭令牌")
    public BaseResult logout(@Valid @RequestBody DisplayControlBaseDTO baseDTO, BindingResult br) {
        ValidUtils.paramValid(br);
        return displayAuthService.logout(baseDTO);
    }

}
