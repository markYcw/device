package com.kedacom.device.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.DeviceManagerService;
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
 * @author wangxy
 * @describe
 * @date 2021/6/1
 */
@Slf4j
@RestController
@RequestMapping("ums/manager")
@Api(value = "统一设备管理接口", tags = "统一设备管理接口")
public class DeviceManagerController {

    @Autowired
    DeviceManagerService deviceManagerService;

    @ApiOperation("手动同步设备数据")
    @PostMapping("/syncDeviceData")
    public BaseResult<String> syncDeviceData(@Valid @RequestBody UmsDeviceInfoSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        deviceManagerService.syncDeviceData(requestDto);

        return BaseResult.succeed("当前统一设备开启同步成功");
    }

    @ApiOperation("手动同步设备分组数据")
    @PostMapping("/syncDeviceData")
    public BaseResult<String> syncDeviceData(@Valid @RequestBody UmsDeviceGroupSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        deviceManagerService.queryDeviceGroupNotify(requestDto.getUmsId());

        return BaseResult.succeed("当前统一设备分组开启同步成功");
    }

    @ApiOperation("获取当前服务统一设备最近一次同步设备时间")
    @PostMapping("/lastSyncTime")
    public BaseResult<String> getUmsLastSyncTime(@Valid @RequestBody UmsDeviceInfoSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        String umsLastSyncTime = deviceManagerService.getUmsLastSyncTime(requestDto);

        return BaseResult.succeed(umsLastSyncTime);
    }

    @ApiOperation("查询统一设备平台下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceList")
    public BaseResult<BasePage<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceList(@RequestBody UmsSubDeviceInfoQueryRequestDto requestDto) {

        BasePage<UmsSubDeviceInfoQueryResponseDto> basePage = deviceManagerService.selectUmsSubDeviceList(requestDto);

        return BaseResult.succeed(basePage);
    }

    @ApiOperation("查询当前分组下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceByGroupId")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGroupId(@Valid @RequestBody UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        List<UmsSubDeviceInfoQueryResponseDto> resultList = deviceManagerService.selectUmsSubDeviceByGroupId(requestDto);

        return BaseResult.succeed(resultList);
    }

    @ApiOperation("根据设备id查询设备信息")
    @PostMapping("selectUmsSubDeviceByIds")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByIds(@RequestBody UmsSubDeviceInfoQueryByIdsRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        if (CollectionUtil.isEmpty(ids)) {
            return BaseResult.failed("查询设备信息参数id为空");
        }
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = deviceManagerService.selectUmsSubDeviceByIds(requestDto);

        return BaseResult.succeed(umsSubDeviceInfoQueryResponseDtoList);
    }

    @ApiOperation("根据国标id查询设备信息")
    @PostMapping("/selectUmsSubDeviceByGbIds")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(@RequestBody UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {

        List<String> gbIds = requestDto.getGbIds();
        if (CollectionUtil.isEmpty(gbIds)) {
            return BaseResult.failed("查询设备信息参数gbIds为空");
        }
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = deviceManagerService.selectUmsSubDeviceByGbIds(requestDto);

        return BaseResult.succeed(umsSubDeviceInfoQueryResponseDtoList);
    }

    @ApiOperation("删除统一平台下的子设备")
    @PostMapping("/deleteUmsSubDevice")
    public BaseResult<String> deleteUmsSubDevice(@RequestBody UmsSubDeviceInfoDeleteRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        if (CollectionUtil.isEmpty(ids)) {
            return BaseResult.failed("删除统一平台下的子设备参数id为空");
        }
        if (deviceManagerService.deleteUmsSubDevice(requestDto)) {
            return BaseResult.succeed("统一平台下的子设备删除成功");
        }

        return BaseResult.failed("统一平台下的子设备删除失败");
    }

    @ApiOperation("查询告警类型列表")
    @PostMapping("/selectUmsAlarmTypeList")
    public BaseResult<List<UmsAlarmTypeQueryResponseDto>> selectUmsAlarmTypeList() {

        return BaseResult.succeed(deviceManagerService.selectUmsAlarmTypeList());
    }

    @ApiOperation("查询统一平台分组集合信息接口")
    @PostMapping("/selectUmsGroupList")
    public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupList(@RequestBody UmsScheduleGroupQueryRequestDto requestDto) {

        List<UmsScheduleGroupItemQueryResponseDto> responseDtoList = deviceManagerService.selectUmsGroupList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation(value = "根据当前分组ID查询子分组集合，umsId必传，groupId如果为空，默认查当前平台下根分组集合")
    @PostMapping("/childGroupList")
    public BaseResult<List<SelectChildUmsGroupResponseDto>> selectChildUmsGroupList(@RequestBody SelectChildUmsGroupRequestDto requestDto) {

        List<SelectChildUmsGroupResponseDto> responseDtoList = deviceManagerService.selectChildUmsGroupList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation("根据子设备条件查询统一平台下分组中设备状况信息")
    @PostMapping("/selectUmsGroupItemList")
    public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupItemList(@RequestBody UmsScheduleGroupItemQueryRequestDto requestDto) {

        List<UmsScheduleGroupItemQueryResponseDto> responseDtoList = deviceManagerService.selectUmsGroupItemList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @PostMapping("/childGroupAndSubDevice")
    @ApiOperation(value = "根据当前分组查询子分组和子设备")
    public BaseResult<UmsChildGroupAndSubDeviceInfoResponseVo> selectChildUmsGroupAndSubDeviceInfo(@Valid @RequestBody UmsChildGroupAndSubDeviceInfoRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        String umsId = requestDto.getUmsId();
        String groupId = requestDto.getGroupId();
        UmsChildGroupAndSubDeviceInfoResponseVo umsChildGroupAndSubDeviceInfoResponseVo = new UmsChildGroupAndSubDeviceInfoResponseVo();
        SelectChildUmsGroupRequestDto selectChildUmsGroupRequestDto = new SelectChildUmsGroupRequestDto();
        selectChildUmsGroupRequestDto.setUmsId(umsId);
        selectChildUmsGroupRequestDto.setGroupId(groupId);
        List<SelectChildUmsGroupResponseDto> umsChildGroupList = deviceManagerService.selectChildUmsGroupList(selectChildUmsGroupRequestDto);
        umsChildGroupAndSubDeviceInfoResponseVo.setChildGroupList(umsChildGroupList);
        UmsSubDeviceInfoQueryByGroupIdRequestDto umsSubDeviceInfoQueryByGroupIdRequestDto = new UmsSubDeviceInfoQueryByGroupIdRequestDto();
        umsSubDeviceInfoQueryByGroupIdRequestDto.setUmsId(umsId);
        umsSubDeviceInfoQueryByGroupIdRequestDto.setGroupId(groupId);
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceList = deviceManagerService.selectUmsSubDeviceByGroupId(umsSubDeviceInfoQueryByGroupIdRequestDto);
        umsChildGroupAndSubDeviceInfoResponseVo.setSubDeviceList(umsSubDeviceList);

        return BaseResult.succeed("查询成功", umsChildGroupAndSubDeviceInfoResponseVo);
    }

}
