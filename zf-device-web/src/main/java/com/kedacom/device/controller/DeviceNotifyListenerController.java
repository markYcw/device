package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.deviceListener.RegisterListenerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册订阅设备通知
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-18 17:55:24
 */
@Api(tags = "RegisterListener/注册订阅设备通知")
@RestController
@RequestMapping("listener")
public class DeviceNotifyListenerController {

    @Autowired
    private RegisterListenerService registerListenerService;

    @ApiOperation("注册订阅设备消息")
    @PostMapping("/registerListener")
    public BaseResult<RegisterListenerVo> registerListener(@RequestBody RegisterListenerVo registerUrlVo) {
       return registerListenerService.registerListener(registerUrlVo);
    }

    @ApiOperation("取消订阅设备消息")
    @PostMapping("/UnRegisterListener")
    public BaseResult<Boolean> UnRegisterListener(@RequestBody RegisterListenerVo registerUrlVo) {

         registerListenerService.UnRegisterListener(registerUrlVo);
        return BaseResult.succeed("取消订阅成功");
    }

}
