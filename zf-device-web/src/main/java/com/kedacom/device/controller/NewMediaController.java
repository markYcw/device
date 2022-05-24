package com.kedacom.device.controller;
import cn.hutool.core.collection.CollectionUtil;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.core.service.NewMediaService;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
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
import java.util.List;

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

    @Autowired
    DeviceManagerService deviceManagerService;

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

    @ApiOperation("分页查询统一平台信息")
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

    @ApiOperation("查询统一设备平台下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceList")
    public BaseResult<BasePage<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceList(@RequestBody UmsSubDeviceInfoQueryRequestDto requestDto) {

        BasePage<UmsSubDeviceInfoQueryResponseDto> basePage = service.selectUmsSubDeviceList(requestDto);

        return BaseResult.succeed(basePage);
    }

    @ApiOperation("查询当前分组下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceByGroupId")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGroupId(@Valid @RequestBody UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        List<UmsSubDeviceInfoQueryResponseDto> resultList = service.selectUmsSubDeviceByGroupId(requestDto);

        return BaseResult.succeed(resultList);
    }

    @ApiOperation("根据国标id查询设备信息")
    @PostMapping("/selectUmsSubDeviceByGbIds")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(@RequestBody UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {

        List<String> gbIds = requestDto.getGbIds();
        if (CollectionUtil.isEmpty(gbIds)) {
            return BaseResult.failed("查询设备信息参数gbIds为空");
        }
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = service.selectUmsSubDeviceByGbIds(requestDto);

        return BaseResult.succeed(umsSubDeviceInfoQueryResponseDtoList);
    }

    @ApiOperation("查询告警类型列表")
    @PostMapping("/selectUmsAlarmTypeList")
    public BaseResult<List<UmsAlarmTypeQueryResponseDto>> selectUmsAlarmTypeList() {

        return BaseResult.succeed(deviceManagerService.selectUmsAlarmTypeList());
    }

    @ApiOperation("查询统一平台分组集合信息接口")
    @PostMapping("/selectUmsGroupList")
    public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupList(@RequestBody UmsScheduleGroupQueryRequestDto requestDto) {

        List<UmsScheduleGroupItemQueryResponseDto> responseDtoList = service.selectUmsGroupList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation(value = "根据当前分组ID查询子分组集合，umsId必传，groupId如果为空，默认查当前平台下根分组集合")
    @PostMapping("/childGroupList")
    public BaseResult<List<SelectChildUmsGroupResponseDto>> selectChildUmsGroupList(@RequestBody SelectChildUmsGroupRequestDto requestDto) {

        List<SelectChildUmsGroupResponseDto> responseDtoList = service.selectChildUmsGroupList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

}
