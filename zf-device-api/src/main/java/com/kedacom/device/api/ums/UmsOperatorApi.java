package com.kedacom.device.api.ums;

import com.kedacom.BaseResult;
import com.kedacom.device.api.ums.fallback.UmsOperatorApiFallBackFactory;
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
 * @date 2021/6/4
 */
@FeignClient(value = "device-server", contextId = "UmsOperatorApi", path = "/api-device/ums/operator", fallbackFactory = UmsOperatorApiFallBackFactory.class)
public interface UmsOperatorApi {

    @ApiOperation("创建调度组")
    @PostMapping("/createScheduleGroup")
    BaseResult<UmsScheduleGroupCreateResponseDto> createScheduleGroup(@RequestBody UmsScheduleGroupCreateRequestDto request);

    @ApiOperation("删除调度组")
    @PostMapping("/deleteScheduleGroup")
    BaseResult<Boolean> deleteScheduleGroup(@RequestBody UmsScheduleGroupDeleteRequestDto requestDto);

    @ApiOperation("添加调度组成员设备")
    @PostMapping("/addScheduleGroupMember")
    BaseResult<Boolean> addScheduleGroupMember(@RequestBody UmsScheduleGroupAddMembersRequestDto requestDto);

    @ApiOperation("删除调度组成员设备")
    @PostMapping("/deleteScheduleGroupMember")
    BaseResult<Boolean> deleteScheduleGroupMember(@RequestBody UmsScheduleGroupDeleteMembersRequestDto requestDto);

    @ApiOperation("查询调度组集合")
    @PostMapping("/selectScheduleGroupList")
    BaseResult<List<UmsScheduleGroupQueryResponseDto>> selectScheduleGroupList(@RequestBody UmsScheduleGroupQueryRequestDto requestDto);

    @ApiOperation("查询调度组状态，查询成功后会通过kafka通知推送")
    @PostMapping("/queryScheduleGroupStatus")
    BaseResult<Boolean> queryScheduleGroupStatus(@RequestBody UmsScheduleGroupQueryStatusRequestVo requestDto);

    @ApiOperation("设置调度组静音")
    @PostMapping("/setScheduleGroupSilence")
    BaseResult<Boolean> setScheduleGroupSilence(@RequestBody UmsScheduleGroupSetSilenceRequestDto requestDto);

    @ApiOperation("查询调度组静音")
    @PostMapping("/queryScheduleGroupSilence")
    BaseResult<UmsScheduleGroupQuerySilenceResponseDto> queryScheduleGroupSilence(@RequestBody UmsScheduleGroupQuerySilenceRequestDto requestDto);

    @ApiOperation("设置调度组哑音")
    @PostMapping("/setScheduleGroupMute")
    BaseResult<Boolean> setScheduleGroupMute(@RequestBody UmsScheduleGroupSetMuteRequestDto requestDto);

    @ApiOperation("查询调度组哑音")
    @PostMapping("/queryScheduleGroupMute")
    BaseResult<UmsScheduleGroupQueryMuteResponseDto> queryScheduleGroupMute(@RequestBody UmsScheduleGroupQueryMuteRequestDto requestDto);

    @ApiOperation("调度组PTZ控制")
    @PostMapping("/controlScheduleGroupPtz")
    BaseResult<Boolean> controlScheduleGroupPtz(@RequestBody UmsScheduleGroupPtzControlRequestDto requestDto);

    @ApiOperation("开始画面合成")
    @PostMapping("/startScheduleGroupVmpMix")
    BaseResult<UmsScheduleGroupStartVmpMixResponseDto> startScheduleGroupVmpMix(@RequestBody UmsScheduleGroupStartVmpMixRequestDto requestDto);

    @ApiOperation("更新画面合成")
    @PostMapping("/updateScheduleGroupVmpMix")
    BaseResult<Boolean> updateScheduleGroupVmpMix(@RequestBody UmsScheduleGroupUpdateVmpMixRequestDto requestDto);

    @ApiOperation("停止画面合成")
    @PostMapping("/stopScheduleGroupVmpMix")
    BaseResult<Boolean> stopScheduleGroupVmpMix(@RequestBody UmsScheduleGroupStopVmpMixRequestDto requestDto);

    @ApiOperation("查询画面合成")
    @PostMapping("/queryScheduleGroupVmpMix")
    BaseResult<UmsScheduleGroupQueryVmpMixResponseDto> queryScheduleGroupVmpMix(@RequestBody UmsScheduleGroupQueryVmpMixRequestDto requestDto);

    @ApiOperation("呼叫设备上线")
    @PostMapping("/callUpSubDevice")
    BaseResult<Boolean> callUpSubDevice(@RequestBody UmsScheduleGroupSubDeviceCallUpRequestDto requestDto);

    @ApiOperation("设置调度组广播源")
    @PostMapping("/setScheduleGroupBroadcast")
    BaseResult<Boolean> setScheduleGroupBroadcast(@RequestBody UmsScheduleGroupSetBroadcastRequestDto requestDto);

    @ApiOperation("取消调度组广播源")
    @PostMapping("/cancelScheduleGroupBroadcast")
    BaseResult<Boolean> cancelScheduleGroupBroadcast(@RequestBody UmsScheduleGroupCancelBroadcastRequestDto requestDto);

    @ApiOperation("查询调度组广播源")
    @PostMapping("/queryScheduleGroupBroadcast")
    BaseResult<UmsScheduleGroupQueryBroadcastResponseDto> queryScheduleGroupBroadcast(@RequestBody UmsScheduleGroupQueryBroadcastRequestDto requestDto);

    @ApiOperation("设置调度组媒体源")
    @PostMapping("/setScheduleGroupMedia")
    BaseResult<Boolean> setScheduleGroupMedia(@RequestBody UmsScheduleGroupSetMediaRequestDto requestDto);

    @ApiOperation("查询调度组媒体源")
    @PostMapping("/queryScheduleGroupMedia")
    BaseResult<UmsScheduleGroupQueryMediaResponseDto> queryScheduleGroupMedia(@RequestBody UmsScheduleGroupQueryMediaRequestDto requestDto);

    @ApiOperation("加入讨论组")
    @PostMapping("/joinScheduleGroupDiscussionGroup")
    BaseResult<List<String>> joinScheduleGroupDiscussionGroup(@RequestBody UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto);

    @ApiOperation("离开讨论组")
    @PostMapping("/quitScheduleGroupDiscussionGroup")
    BaseResult<Boolean> quitScheduleGroupDiscussionGroup(@RequestBody UmsScheduleGroupQuitDiscussionGroupRequestDto requestDto);

    @ApiOperation("查询讨论组")
    @PostMapping("/queryScheduleGroupDiscussionGroup")
    BaseResult<List<UmsScheduleGroupQueryDiscussionGroupResponseDto>> queryScheduleGroupDiscussionGroup(@RequestBody UmsScheduleGroupQueryDiscussionGroupRequestDto requestDto);

    @ApiOperation("清空讨论组")
    @PostMapping("/clearScheduleGroupDiscussionGroup")
    BaseResult<Boolean> clearScheduleGroupDiscussionGroup(@RequestBody UmsScheduleGroupClearDiscussionGroupRequestDto requestDto);

}
