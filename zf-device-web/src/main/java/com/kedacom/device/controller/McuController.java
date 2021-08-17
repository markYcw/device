package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.McuService;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.request.McuAccountDTO;
import com.kedacom.mp.mcu.request.McuConfsDTO;
import com.kedacom.mp.mcu.request.McuTemplatesDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author hxj
 * @date: 2021/8/13 13:48
 * @description 会议平台操作接口
 */
@RestController
@RequestMapping("ums/mcu")
@Api(value = "会议平台操作接口", tags = "会议平台操作接口")
public class McuController {

    @Autowired
    private McuService mcuService;

    @ApiOperation("登录平台")
    @PostMapping("/login")
    public BaseResult login(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.login(dto);
    }

    @ApiOperation("登出平台")
    @PostMapping("/logout")
    public BaseResult logout(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.logout(dto);
    }

    @ApiOperation("创建/删除账户")
    @PostMapping("/account")
    public BaseResult account(@Valid @RequestBody McuAccountDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.account(dto);
    }

    @ApiOperation("获取会议列表")
    @PostMapping("/confs")
    public BaseResult confs(@Valid @RequestBody McuConfsDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.confs(dto);
    }

    @ApiOperation("获取会议模板列表")
    @PostMapping("/templates")
    public BaseResult templates(@Valid @RequestBody McuTemplatesDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.templates(dto);
    }


}
