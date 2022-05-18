package com.kedacom.device.controller;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.NewMediaService;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectByIdResponseDto;
import com.kedacom.ums.responsedto.UmsDeviceInfoSelectResponseDto;
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
 * @author ycw
 * @describe
 * @date 2022/04/02
 */
@Slf4j
@RestController
@RequestMapping("ums/newMedia")
@Api(value = "新媒体平台", tags = "新媒体接口")
public class NewMediaController {

    @Autowired
    NewMediaService service;

    @ApiOperation(value = "添加统一平台信息", tags = "默认添加的设备类型为UMS，建议填写具体设备类型字段")
    @PostMapping("/insertUmsDevice")
    public BaseResult<String> insertUmsDevice(@Valid @RequestBody UmsDeviceInfoAddRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return service.insertUmsDevice(requestDto);
    }

    @ApiOperation("手动同步设备数据")
    @PostMapping("/syncDeviceData")
    public BaseResult<String> syncDeviceData(@Valid @RequestBody UmsDeviceInfoSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        service.syncDeviceData(requestDto);

        return BaseResult.succeed("当前统一设备开启同步成功");
    }

    @ApiOperation("更新统一平台信息")
    @PostMapping("/updateUmsDevice")
    public BaseResult<String> updateUmsDevice(@Valid @RequestBody UmsDeviceInfoUpdateRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return service.updateUmsDevice(requestDto);
    }

    @ApiOperation("删除统一平台信息")
    @PostMapping("/deleteUmsDevice")
    public BaseResult<String> deleteUmsDevice(@Valid @RequestBody UmsDeviceInfoDeleteRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return service.deleteUmsDevice(requestDto);
    }

    @ApiOperation("查询统一平台信息")
    @PostMapping("/selectUmsDeviceList")
    public BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(@RequestBody UmsDeviceInfoSelectRequestDto requestDto) {

        BasePage<UmsDeviceInfoSelectResponseDto> basePage = service.selectUmsDeviceList(requestDto);

        return BaseResult.succeed(basePage);
    }

    @PostMapping("/getDeviceInfoById")
    @ApiOperation(value = "根据平台id查询平台信息")
    public BaseResult<UmsDeviceInfoSelectByIdResponseDto> getDeviceInfoById(@Valid @RequestBody UmsDeviceInfoSelectByIdRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        UmsDeviceInfoSelectByIdResponseDto responseDto = service.getDeviceInfoById(requestDto);

        return BaseResult.succeed("查询成功", responseDto);
    }

}
