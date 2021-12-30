package com.kedacom.device.controller;

import com.kedacom.device.core.service.DeviceNotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/29 9:24
 * @description 设备消息接收控制层
 */
@RestController
@RequestMapping("ums/device")
@Api(value = "设备消息接收控制层", tags = "设备消息接收控制层")
public class DeviceNotifyController {

    @Autowired
    private DeviceNotifyService service;

    @PostMapping("/notify")
    @ApiOperation(value = "设备通知")
    public void cuNotify(@RequestBody String notify) {

        service.handleNotify(notify);
    }
}
