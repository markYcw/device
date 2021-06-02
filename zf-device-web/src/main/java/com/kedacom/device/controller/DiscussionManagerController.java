package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.DiscussionManagerService;
import com.kedacom.ums.requestdto.UmsScheduleGroupClearDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupJoinDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupQueryDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupQuitDiscussionGroupRequestDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupQueryDiscussionGroupResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @date 2021/6/2
 */
@RestController
@RequestMapping("ums/operator")
@Api(value = "讨论组管理接口",tags = "讨论组管理接口（基于融合调度服务）")
public class DiscussionManagerController {

    @Autowired
    DiscussionManagerService discussionManagerService;

    @ApiOperation("加入讨论组")
    @PostMapping("/joinScheduleGroupDiscussionGroup")
    public BaseResult<List<String>> joinScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(discussionManagerService.joinScheduleGroupDiscussionGroup(requestDto));
    }

    @ApiOperation("离开讨论组")
    @PostMapping("/quitScheduleGroupDiscussionGroup")
    public BaseResult<Boolean> quitScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupQuitDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(discussionManagerService.quitScheduleGroupDiscussionGroup(requestDto));
    }

    @ApiOperation("查询讨论组")
    @PostMapping("/queryScheduleGroupDiscussionGroup")
    public BaseResult<List<UmsScheduleGroupQueryDiscussionGroupResponseDto>> queryScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupQueryDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(discussionManagerService.queryScheduleGroupDiscussionGroup(requestDto));
    }

    @ApiOperation("清空讨论组")
    @PostMapping("/clearScheduleGroupDiscussionGroup")
    public BaseResult<Boolean> clearScheduleGroupDiscussionGroup(@Valid @RequestBody UmsScheduleGroupClearDiscussionGroupRequestDto requestDto, BindingResult result) {

        ValidUtils.paramValid(result);

        return BaseResult.succeed(discussionManagerService.clearScheduleGroupDiscussionGroup(requestDto));
    }

}
