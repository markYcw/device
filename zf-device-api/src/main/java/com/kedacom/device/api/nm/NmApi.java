package com.kedacom.device.api.nm;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.nm.fallback.NmApiFallbackFactory;
import com.kedacom.newMedia.dto.SVROrderDTO;
import com.kedacom.streamMedia.request.GetAudioCapDTO;
import com.kedacom.streamMedia.request.GetBurnStateDTO;
import com.kedacom.streamMedia.request.GetSvrAudioActStateDTO;
import com.kedacom.streamMedia.response.GetAudioCapVO;
import com.kedacom.streamMedia.response.GetBurnStateVO;
import com.kedacom.streamMedia.response.GetSvrAudioActStateVo;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * svr相关操作
 * @author ycw
 * @date 2021/09/07 14:55
 */
@FeignClient(name = "device-server", contextId = "nmApi", path = "/api-device/ums/newMedia",fallbackFactory = NmApiFallbackFactory.class)
public interface NmApi {

    @ApiOperation(value = "添加统一平台信息", tags = "默认添加的设备类型为UMS，建议填写具体设备类型字段")
    @PostMapping("/insertUmsDevice")
    public BaseResult<String> insertUmsDevice(@RequestBody UmsDeviceInfoAddRequestDto requestDto);

    @ApiOperation("手动同步设备数据")
    @PostMapping("/syncDeviceData")
    public BaseResult<String> syncDeviceData(@RequestBody UmsDeviceInfoSyncRequestDto requestDto);

    @ApiOperation("更新统一平台信息")
    @PostMapping("/updateUmsDevice")
    public BaseResult<String> updateUmsDevice(@RequestBody UmsDeviceInfoUpdateRequestDto requestDto);

    @ApiOperation("删除统一平台信息")
    @PostMapping("/deleteUmsDevice")
    public BaseResult<String> deleteUmsDevice(@RequestBody UmsDeviceInfoDeleteRequestDto requestDto);

    @ApiOperation("分页查询统一平台信息")
    @PostMapping("/selectUmsDeviceList")
    public BaseResult<BasePage<UmsDeviceInfoSelectResponseDto>> selectUmsDeviceList(@RequestBody UmsDeviceInfoSelectRequestDto requestDto);

    @PostMapping("/getDeviceInfoById")
    @ApiOperation(value = "根据平台id查询平台信息")
    public BaseResult<UmsDeviceInfoSelectByIdResponseDto> getDeviceInfoById(@RequestBody UmsDeviceInfoSelectByIdRequestDto requestDto);

    @ApiOperation("查询统一设备平台下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceList")
    public BaseResult<BasePage<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceList(@RequestBody UmsSubDeviceInfoQueryRequestDto requestDto);

    @ApiOperation("查询当前分组下挂载的子设备信息")
    @PostMapping("/selectUmsSubDeviceByGroupId")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGroupId(@RequestBody UmsSubDeviceInfoQueryByGroupIdRequestDto requestDto);

    @ApiOperation("根据国标id查询设备信息")
    @PostMapping("/selectUmsSubDeviceByGbIds")
    public BaseResult<List<UmsSubDeviceInfoQueryResponseDto>> selectUmsSubDeviceByGbIds(@RequestBody UmsSubDeviceInfoQueryByGbIdsRequestDto requestDto);

    @ApiOperation("查询告警类型列表")
    @PostMapping("/selectUmsAlarmTypeList")
    public BaseResult<List<UmsAlarmTypeQueryResponseDto>> selectUmsAlarmTypeList();

    @ApiOperation("查询统一平台分组集合信息接口")
    @PostMapping("/selectUmsGroupList")
    public BaseResult<List<UmsScheduleGroupItemQueryResponseDto>> selectUmsGroupList(@RequestBody UmsScheduleGroupQueryRequestDto requestDto);

    @ApiOperation(value = "根据当前分组ID查询子分组集合，umsId必传，groupId如果为空，默认查当前平台下根分组集合")
    @PostMapping("/childGroupList")
    public BaseResult<List<SelectChildUmsGroupResponseDto>> selectChildUmsGroupList(@RequestBody SelectChildUmsGroupRequestDto requestDto);

    @ApiOperation("发送宏指令数据 目前只支持SVR2931型号")
    @PostMapping("/sendOrderData")
    public BaseResult<Boolean> sendOrderData(@RequestBody SVROrderDTO dto);

    @ApiOperation("获取音频能力集 目前只支持SVR2931型号")
    @PostMapping("/getAudioCap")
    public BaseResult<GetAudioCapVO> getAudioCap(@RequestBody GetAudioCapDTO getAudioCapDTO);

    @ApiOperation("获取当前语音激励状态 目前只支持SVR2931型号")
    @PostMapping("/getSvrAudioActState")
    public BaseResult<GetSvrAudioActStateVo> getSvrAudioActState(@RequestBody GetSvrAudioActStateDTO dto);

    @ApiOperation("刻录状态请求 目前只支持SVR2931型号")
    @PostMapping("/getBurnState")
    public BaseResult<GetBurnStateVO> getBurnState(@RequestBody GetBurnStateDTO getBurnStateDTO);

}
