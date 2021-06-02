package com.kedacom.device.core.service;

import com.kedacom.ums.requestdto.UmsScheduleGroupClearDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupJoinDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupQueryDiscussionGroupRequestDto;
import com.kedacom.ums.requestdto.UmsScheduleGroupQuitDiscussionGroupRequestDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupQueryDiscussionGroupResponseDto;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/6/2
 */
public interface DiscussionManagerService {

    /**
     * 加入讨论组
     * @param requestDto
     * @return
     */
    List<String> joinScheduleGroupDiscussionGroup(UmsScheduleGroupJoinDiscussionGroupRequestDto requestDto);

    /**
     * 离开讨论组
     * @param requestDto
     * @return
     */
    Boolean quitScheduleGroupDiscussionGroup(UmsScheduleGroupQuitDiscussionGroupRequestDto requestDto);

    /**
     * 查询讨论组
     * @param requestDto
     * @return
     */
    List<UmsScheduleGroupQueryDiscussionGroupResponseDto> queryScheduleGroupDiscussionGroup(UmsScheduleGroupQueryDiscussionGroupRequestDto requestDto);

    /**
     * 清空讨论组
     * @param requestDto
     * @return
     */
    Boolean clearScheduleGroupDiscussionGroup(UmsScheduleGroupClearDiscussionGroupRequestDto requestDto);

}
