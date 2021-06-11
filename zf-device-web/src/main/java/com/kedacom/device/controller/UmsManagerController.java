package com.kedacom.device.controller;

import cn.hutool.core.util.StrUtil;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.ums.entity.AcceptUrlListen;
import com.kedacom.core.DeviceStatusListenerManager;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Slf4j
@RestController
@RequestMapping("ums/manager")
@Api(value = "统一平台管理接口", tags = "统一平台管理接口")
public class UmsManagerController {

    @Autowired
    UmsManagerService umsManagerService;

    @ApiOperation("添加接入模式")
    @PostMapping("/mode")
    public BaseResult<Boolean> setDeviceWorkMode() {

        return null;
    }

    @ApiOperation(value = "添加统一平台信息", tags = "默认添加的设备类型为UMS，建议填写具体设备类型字段")
    @PostMapping("/insertUmsDevice")
    public BaseResult<String> insertUmsDevice(@Valid @RequestBody UmsDeviceInfoAddRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        if (umsManagerService.isRepeatForName(null, requestDto.getName())) {
            log.error("新增统一平台信息失败 ： 设备名称重复");
            return BaseResult.failed("设备名称重复，请重新填写");
        }
        if (umsManagerService.isRepeatForDeviceIp(null, requestDto.getDeviceIp())) {
            log.error("新增统一平台信息失败 ： 设备IP重复");
            return BaseResult.failed("设备IP重复，请重新填写");
        }
        if (umsManagerService.isRepeatForDevicePort(null, requestDto.getDevicePort())) {
            log.error("新增统一平台信息失败 ： 设备端口重复");
            return BaseResult.failed("设备端口重复，请重新填写");
        }
        String umsId = umsManagerService.insertUmsDevice(requestDto);
        if (StrUtil.isBlank(umsId)) {
            return BaseResult.failed("添加统一平台信息失败");
        }

        return BaseResult.succeed("添加统一平台信息成功", umsId);
    }

    @ApiOperation("更新统一平台信息")
    @PostMapping("/updateUmsDevice")
    public BaseResult<String> updateUmsDevice(@Valid @RequestBody UmsDeviceInfoUpdateRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        if (umsManagerService.isRepeatForName(requestDto.getId(), requestDto.getName())) {
            log.error("更新统一平台信息失败 ： 设备名称重复");
            return BaseResult.failed("设备名称重复，请重新填写");
        }
        if (umsManagerService.isRepeatForDeviceIp(requestDto.getId(), requestDto.getDeviceIp())) {
            log.error("更新统一平台信息失败 ： 设备IP重复");
            return BaseResult.failed("设备IP重复，请重新填写");
        }
        if (umsManagerService.isRepeatForDevicePort(requestDto.getId(), requestDto.getDevicePort())) {
            log.error("更新统一平台信息失败 ： 设备端口重复");
            return BaseResult.failed("设备端口重复，请重新填写");
        }
        String umsId = umsManagerService.updateUmsDevice(requestDto);
        if (StrUtil.isBlank(umsId)) {
            return BaseResult.failed("更新统一平台信息失败");
        }

        return BaseResult.succeed("更新统一平台信息成功", umsId);
    }

    @ApiOperation("删除统一平台信息")
    @PostMapping("/deleteUmsDevice")
    public BaseResult<Boolean> deleteUmsDevice(@Valid @RequestBody UmsDeviceInfoDeleteRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        if (umsManagerService.deleteUmsDevice(requestDto)) {
            return BaseResult.succeed("统一平台信息删除成功");
        }

        return BaseResult.failed("删除统一平台信息失败");
    }

    @ApiOperation("查询统一平台信息")
    @PostMapping("/selectUmsDeviceList")
    public BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(@RequestBody UmsDeviceInfoSelectRequestDto requestDto) {

        BasePage<UmsDeviceInfoSelectResponseDto> basePage = umsManagerService.selectUmsDeviceList(requestDto);

        return BaseResult.succeed(basePage);
    }

    @PostMapping("/deviceStatusRegister")
    @ApiOperation(value = "注冊到设备状态通知管理类")
    public BaseResult deviceStatusRegister(@Valid @RequestBody AcceptUrlListen listener, BindingResult result) {
        DeviceStatusListenerManager.getInstance().register(listener);
        return BaseResult.succeed("注册成功");
    }

    @PostMapping("/getDeviceInfoById")
    @ApiOperation(value = "根据平台id查询平台信息")
    public BaseResult<UmsDeviceInfoSelectByIdResponseDto> getDeviceInfoById(@Valid @RequestBody UmsDeviceInfoSelectByIdRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        UmsDeviceInfoSelectByIdResponseDto responseDto = umsManagerService.getDeviceInfoById(requestDto);

        return BaseResult.succeed("查询成功", responseDto);
    }

}
