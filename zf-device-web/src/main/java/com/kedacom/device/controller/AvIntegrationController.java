package com.kedacom.device.controller;


import com.kedacom.device.service.AvIntegrationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:03
 */
@RestController
@RequestMapping("device/avIntegration")
@Api(value = "拼控")
@Slf4j
public class AvIntegrationController {

    @Resource
    private AvIntegrationService avIntegrationService;



}
