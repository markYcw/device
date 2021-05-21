package com.kedacom.device.api.ums;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.ums.fallback.UmsManageApiFallBackFactory;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/21
 */
@FeignClient(contextId = "UmsManageApi", path = "ums/manager", fallbackFactory = UmsManageApiFallBackFactory.class)
public interface UmsManageApi {

    @ApiOperation(value = "添加统一平台信息", tags = "默认添加的设备类型为UMS，建议填写具体设备类型字段")
    @PostMapping("/insertUmsDevice")
    BaseResult<String> insertUmsDevice(@RequestBody UmsDeviceInfoAddRequestDto requestDto);

    @ApiOperation("更新统一平台信息")
    @PostMapping("/updateUmsDevice")
    BaseResult<String> updateUmsDevice(@RequestBody UmsDeviceInfoUpdateRequestDto requestDto);

    @ApiOperation("删除统一平台信息")
    @PostMapping("/deleteUmsDevice")
    BaseResult<Boolean> deleteUmsDevice(@RequestBody UmsDeviceInfoDeleteRequestDto requestDto);

    @ApiOperation("手动同步设备数据")
    @PostMapping("/syncDeviceData")
    BaseResult<String> syncDeviceData(@RequestBody UmsDeviceInfoSyncRequestDto requestDto);

    @ApiOperation("获取当前服务统一设备最近一次同步设备时间")
    @PostMapping("/lastSyncTime")
    BaseResult<String> getUmsLastSyncTime(@RequestBody UmsDeviceInfoSyncRequestDto requestDto);

    @ApiOperation("查询统一平台信息")
    @PostMapping("/selectUmsDeviceList")
    BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(@RequestBody UmsDeviceInfoSelectRequestDto requestDto);

    @ApiOperation("查询统一设备平台下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceList")
    BaseResult<BasePage<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceList(@RequestBody UmsSubDeviceInfoQueryRequestDto requestDto);

    @ApiOperation("查询当前分组下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceByGroupId")
    BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGroupId(@RequestBody UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto);

    @ApiOperation("根据设备id查询设备信息")
    @PostMapping("selectUmsSubDeviceByIds")
    BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByIds(@RequestBody UmsSubDeviceInfoQueryByIdsRequestDto requestDto);

    @ApiOperation("根据国标id查询子设备信息")
    @PostMapping("/selectUmsSubDeviceByGbIds")
    BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(@RequestBody UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto);

    @ApiOperation("删除统一平台下的子设备")
    @PostMapping("/deleteUmsSubDevice")
    BaseResult<String> deleteUmsSubDevice(@RequestBody UmsSubDeviceInfoDeleteRequestDto requestDto);

    @ApiOperation("查询告警类型列表")
    @PostMapping("/selectUmsAlarmTypeList")
    BaseResult<List<UmsAlarmTypeQueryResponseDto>> selectUmsAlarmTypeList();

    @ApiOperation("查询统一平台分组集合信息接口")
    @PostMapping("/selectUmsGroupList")
    BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupList(@RequestBody UmsScheduleGroupQueryRequestDto requestDto);

    @ApiOperation(value = "根据当前分组ID查询子分组集合，umsId必传，groupId如果为空，默认查当前平台下根分组集合")
    @PostMapping("/childGroupList")
    BaseResult<List<SelectChildUmsGroupResponseDto>> selectChildUmsGroupList(@RequestBody SelectChildUmsGroupRequestDto requestDto);

    @ApiOperation("根据子设备条件查询统一平台下分组中设备状况信息")
    @PostMapping("/selectUmsGroupItemList")
    BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupItemList(@RequestBody UmsScheduleGroupItemQueryRequestDto requestDto);

}
