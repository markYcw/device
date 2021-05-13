package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.ums.requestdto.*;
import com.kedacom.ums.responsedto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @date 2021/5/10
 */
@RestController
@RequestMapping("ums/operator")
@Api(value = "统一设备操作接口",tags = "统一设备操作接口")
public class UmsOperateController {

    @ApiOperation("创建调度组")
    @PostMapping("/createScheduleGroup")
    public BaseResult<UmsScheduleGroupCreateResponseDto> createScheduleGroup(@Valid @RequestBody UmsScheduleGroupCreateRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("删除调度组")
    @PostMapping("/deleteScheduleGroup")
    public BaseResult<Boolean> deleteScheduleGroup(@Valid @RequestBody UmsScheduleGroupDeleteRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("添加调度组成员设备")
    @PostMapping("/addScheduleGroupMember")
    public BaseResult<Boolean> addScheduleGroupMember(@Valid @RequestBody UmsScheduleGroupAddMembersRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("删除调度组成员设备")
    @PostMapping("/deleteScheduleGroupMember")
    public BaseResult<Boolean> deleteScheduleGroupMember(@Valid @RequestBody UmsScheduleGroupDeleteMembersRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询调度组集合")
    @PostMapping("/selectScheduleGroupList")
    public BaseResult<List<UmsScheduleGroupQueryResponseDto>> selectScheduleGroupList(@Valid @RequestBody UmsScheduleGroupQueryRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询调度组状态，查询成功后会通过kafka通知推送")
    @PostMapping("/queryScheduleGroupStatus")
    public BaseResult<Boolean> queryScheduleGroupStatus(@Valid @RequestBody UmsScheduleGroupQueryStatusRequestVo requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("设置调度组静音")
    @PostMapping("/setScheduleGroupSilence")
    public BaseResult<Boolean> setScheduleGroupSilence(@Valid @RequestBody UmsScheduleGroupSetSilenceRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询调度组静音")
    @PostMapping("/queryScheduleGroupSilence")
    public BaseResult<UmsScheduleGroupQuerySilenceResponseDto> queryScheduleGroupSilence(@Valid @RequestBody UmsScheduleGroupQuerySilenceRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("设置调度组哑音")
    @PostMapping("/setScheduleGroupMute")
    public BaseResult<Boolean> setScheduleGroupMute(@Valid @RequestBody UmsScheduleGroupSetMuteRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询调度组哑音")
    @PostMapping("/queryScheduleGroupMute")
    public BaseResult<UmsScheduleGroupQueryMuteResponseDto> queryScheduleGroupMute(@Valid @RequestBody UmsScheduleGroupQueryMuteRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("调度组PTZ控制")
    @PostMapping("/controlScheduleGroupPtz")
    public BaseResult<Boolean> controlScheduleGroupPtz(@Valid @RequestBody UmsScheduleGroupPtzControlRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("加入讨论组")
    @PostMapping("/joinScheduleGroupDiscussionGroup")
    public BaseResult<List<UmsScheduleGroupJoinDiscussionGroupResponseDto>> joinScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("离开讨论组")
    @PostMapping("/quitScheduleGroupDiscussionGroup")
    public BaseResult<Boolean> quitScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupQuitDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询讨论组")
    @PostMapping("/queryScheduleGroupDiscussionGroup")
    public BaseResult<List<UmsScheduleGroupQueryDiscussionGroupResponseDto>> queryScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupQueryDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("清空讨论组")
    @PostMapping("/clearScheduleGroupDiscussionGroup")
    public BaseResult<Boolean> clearScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupClearDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("开始画面合成")
    @PostMapping("/startScheduleGroupVmpMix")
    public BaseResult<UmsScheduleGroupStartVmpMixResponseDto> startScheduleGroupVmpMix(@Valid @RequestBody UmsScheduleGroupStartVmpMixRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("更新画面合成")
    @PostMapping("/updateScheduleGroupVmpMix")
    public BaseResult<UmsScheduleGroupUpdateVmpMixResponseDto> updateScheduleGroupVmpMix(@Valid @RequestBody UmsScheduleGroupUpdateVmpMixRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("停止画面合成")
    @PostMapping("/stopScheduleGroupVmpMix")
    public BaseResult<Boolean> stopScheduleGroupVmpMix(@Valid @RequestBody UmsScheduleGroupStopVmpMixRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询画面合成")
    @PostMapping("/queryScheduleGroupVmpMix")
    public BaseResult<UmsScheduleGroupQueryVmpMixResponseDto> queryScheduleGroupVmpMix(@Valid @RequestBody UmsScheduleGroupQueryVmpMixRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("开始录像")
    @PostMapping("/startRec")
    public BaseResult<UmsStartRecResponseDto> startRec(@Valid @RequestBody UmsStartRecRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("停止录像")
    @PostMapping("/stopRec")
    public BaseResult<Boolean> stopRec(@Valid @RequestBody UmsStopRecRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询录像列表")
    @PostMapping("/queryRecList")
    public BaseResult<UmsQueryRecListResponseDto> queryRecList(@Valid @RequestBody UmsQueryRecListRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("呼叫设备上线")
    @PostMapping("/callUpSubDevice")
    public BaseResult<Boolean> callUpSubDevice(@Valid @RequestBody UmsScheduleGroupSubDeviceCallUpRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("设置调度组广播源")
    @PostMapping("/setScheduleGroupBroadcast")
    public BaseResult<Boolean> setScheduleGroupBroadcast(@Valid @RequestBody UmsScheduleGroupSetBroadcastRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("取消调度组广播源")
    @PostMapping("/cancelScheduleGroupBroadcast")
    public BaseResult<Boolean> cancelScheduleGroupBroadcast(@Valid @RequestBody UmsScheduleGroupCancelBroadcastRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询调度组广播源")
    @PostMapping("/cancelScheduleGroupBroadcast")
    public BaseResult<UmsScheduleGroupQueryBroadcastResponseDto> cancelScheduleGroupBroadcast(@Valid @RequestBody UmsScheduleGroupQueryBroadcastRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("设置调度组媒体源")
    @PostMapping("/setScheduleGroupMedia")
    public BaseResult<Boolean> setScheduleGroupMedia(@Valid @RequestBody UmsScheduleGroupSetMediaRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

    @ApiOperation("查询调度组媒体源")
    @PostMapping("/queryScheduleGroupMedia")
    public BaseResult<UmsScheduleGroupQueryMediaResponseDto> queryScheduleGroupMedia(@Valid @RequestBody UmsScheduleGroupQueryMediaRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(null);
    }

}
