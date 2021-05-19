package com.kedacom.device.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/10
 */
@Slf4j
@RestController
@RequestMapping("ums/manager")
@Api(value = "统一设备管理接口", tags = "统一设备管理接口")
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

    /**
     * 暂时不去实现
     *
     * @param requestDto
     * @param result
     * @return
     */
    @ApiOperation("通知第三方服务同步设备列表，第三方同步完成会发送kafka消息")
    @PostMapping("/notify/sync")
    public BaseResult<String> notifyThirdServiceSyncData(@Valid @RequestBody UmsDeviceInfoSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        if (umsManagerService.notifyThirdServiceSyncData(requestDto)) {
            return BaseResult.succeed("通知第三方服务同步设备列表成功");
        }

        return BaseResult.failed("通知第三方服务同步设备列表失败");
    }

    @ApiOperation("手动同步设备数据")
    @PostMapping("/syncDeviceData")
    public BaseResult<String> syncDeviceData(@Valid @RequestBody UmsDeviceInfoSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        umsManagerService.syncDeviceData(requestDto);

        return BaseResult.succeed("当前统一设备开启同步成功");
    }

    @ApiOperation("获取当前服务统一设备最近一次同步设备时间")
    @PostMapping("/lastSyncTime")
    public BaseResult<String> getUmsLastSyncTime(@Valid @RequestBody UmsDeviceInfoSyncRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        String umsLastSyncTime = umsManagerService.getUmsLastSyncTime(requestDto);

        return BaseResult.succeed(umsLastSyncTime);
    }

    @ApiOperation("查询统一平台信息")
    @PostMapping("/selectUmsDeviceList")
    public BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(@RequestBody UmsDeviceInfoSelectRequestDto requestDto) {

        BasePage<UmsDeviceInfoSelectResponseDto> basePage = umsManagerService.selectUmsDeviceList(requestDto);

        return BaseResult.succeed(basePage);
    }

    @ApiOperation("查询统一设备平台下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceList")
    public BaseResult<BasePage<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceList(@RequestBody UmsSubDeviceInfoQueryRequestDto requestDto) {

        BasePage<UmsSubDeviceInfoQueryResponseDto> basePage = umsManagerService.selectUmsSubDeviceList(requestDto);

        return BaseResult.succeed(basePage);
    }

    @ApiOperation("查询当前分组下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceByGroupId")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGroupId(@Valid @RequestBody UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);
        List<UmsSubDeviceInfoQueryResponseDto> resultList = umsManagerService.selectUmsSubDeviceByGroupId(requestDto);

        return BaseResult.succeed(resultList);
    }

    @ApiOperation("根据设备id查询设备信息")
    @PostMapping("selectUmsSubDeviceByIds")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByIds(@RequestBody UmsSubDeviceInfoQueryByIdsRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        if (CollectionUtil.isEmpty(ids)) {
            return BaseResult.failed("查询设备信息参数id为空");
        }
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = umsManagerService.selectUmsSubDeviceByIds(requestDto);

        return BaseResult.succeed(umsSubDeviceInfoQueryResponseDtoList);
    }

    @ApiOperation("根据国标id查询子设备信息")
    @PostMapping("/selectUmsSubDeviceByGbIds")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(@RequestBody UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto) {

        List<String> gbIds = requestDto.getGbIds();
        if (CollectionUtil.isEmpty(gbIds)) {
            return BaseResult.failed("查询设备信息参数gbIds为空");
        }
        List<UmsSubDeviceInfoQueryResponseDto> umsSubDeviceInfoQueryResponseDtoList = umsManagerService.selectUmsSubDeviceByGbIds(requestDto);

        return BaseResult.succeed(umsSubDeviceInfoQueryResponseDtoList);
    }

    @ApiOperation("删除统一平台下的子设备")
    @PostMapping("/deleteUmsSubDevice")
    public BaseResult<String> deleteUmsSubDevice(@RequestBody UmsSubDeviceInfoDeleteRequestDto requestDto) {

        List<String> ids = requestDto.getIds();
        if (CollectionUtil.isEmpty(ids)) {
            return BaseResult.failed("删除统一平台下的子设备参数id为空");
        }
        if (umsManagerService.deleteUmsSubDevice(requestDto)) {
            return BaseResult.succeed("统一平台下的子设备删除成功");
        }

        return BaseResult.failed("统一平台下的子设备删除失败");
    }

    @ApiOperation("查询告警类型列表")
    @PostMapping("/selectUmsAlarmTypeList")
    public BaseResult<List<UmsAlarmTypeQueryResponseDto>> selectUmsAlarmTypeList() {

        return BaseResult.succeed(umsManagerService.selectUmsAlarmTypeList());
    }

    @ApiOperation("手动触发从远程更新告警类型列表并返回")
    @PostMapping("/updateUmsAlarmTypeList")
    public BaseResult<List<UmsAlarmTypeQueryResponseDto>> updateUmsAlarmTypeList() {

        List<UmsAlarmTypeQueryResponseDto> responseDtoList = umsManagerService.updateUmsAlarmTypeList();

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation("查询统一平台分组集合信息接口")
    @PostMapping("/selectUmsGroupList")
    public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupList(@RequestBody UmsScheduleGroupQueryRequestDto requestDto) {

        List<UmsScheduleGroupItemQueryResponseDto> responseDtoList = umsManagerService.selectUmsGroupList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation(value = "根据当前分组ID查询子分组集合，umsId必传，groupId如果为空，默认查当前平台下根分组集合")
    @PostMapping("/childGroupList")
    public BaseResult<List<SelectChildUmsGroupResponseDto>> selectChildUmsGroupList(@RequestBody SelectChildUmsGroupRequestDto requestDto) {

        List<SelectChildUmsGroupResponseDto> responseDtoList = umsManagerService.selectChildUmsGroupList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation("根据子设备条件查询统一平台下分组中设备状况信息")
    @PostMapping("/selectUmsGroupItemList")
    public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupItemList(@RequestBody UmsScheduleGroupItemQueryRequestDto requestDto) {

        List<UmsScheduleGroupItemQueryResponseDto> responseDtoList = umsManagerService.selectUmsGroupItemList(requestDto);

        return BaseResult.succeed(responseDtoList);
    }

    @ApiOperation("获取分组")
    @PostMapping("/getGroup")
    public BaseResult<Boolean> getGroup(@RequestParam("umsId") String umsId) {

        Boolean aBoolean = umsManagerService.queryDeviceGroupNotify(umsId);
        return BaseResult.succeed(aBoolean);
    }

}
