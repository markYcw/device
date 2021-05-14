package com.kedacom.device.unite;

import com.kedacom.acl.network.anno.HeadParam;
import com.kedacom.acl.network.anno.RequestBody;
import com.kedacom.acl.network.ums.requestvo.*;
import com.kedacom.ums.responsedto.*;

import java.util.List;

/**
 * 媒体调度服务接口
 *
 * @author van.shu
 * @create 2021/4/26 17:35
 */
public interface MediaScheduleInterface {

    /**
     * 创建调度组
     *
     * @param ssid
     * @param createRequest
     * @return
     */
    UmsScheduleGroupCreateResponseDto createScheduleGroup(@HeadParam  Integer ssid,  @RequestBody UmsScheduleGroupCreateRequest createRequest);

    /**
     * 删除调度组
     *
     * @param ssid
     * @param deleteScheduleGroup
     * @return
     */
    Boolean deleteScheduleGroup(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupDeleteRequest deleteScheduleGroup);

    /**
     * 添加调度组成员设备
     *
     * @param ssid
     * @param addScheduleGroupMember
     * @return
     */
    Boolean addScheduleGroupMember(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupAddMembersRequest addScheduleGroupMember);

    /**
     * 删除调度组成员设备
     *
     * @param ssid
     * @param deleteScheduleGroupMember
     * @return
     */
    Boolean deleteScheduleGroupMember(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupDeleteMembersRequest deleteScheduleGroupMember);

    /**
     * 查询调度组集合
     *
     * @param ssid
     * @return
     */
    List<UmsScheduleGroupQueryResponseDto> selectScheduleGroupList(Integer ssid);

    /**
     * 设置调度组静音
     *
     * @param ssid
     * @param setSilenceRequest
     * @return
     */
    Boolean setScheduleGroupSilence(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupSetSilenceRequest setSilenceRequest);

    /**
     * 查询调度组静音
     *
     * @param ssid
     * @param silenceRequest
     * @return
     */
    UmsScheduleGroupQuerySilenceResponseDto queryScheduleGroupSilence(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQuerySilenceRequest silenceRequest);

    /**
     * 设置调度组哑音
     *
     * @param ssid
     * @param setMuteRequest
     * @return
     */
    Boolean setScheduleGroupMute(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupSetMuteRequest setMuteRequest);

    /**
     * 查询调度组哑音
     *
     * @param ssid
     * @param queryMuteRequest
     * @return
     */
    UmsScheduleGroupQueryMuteResponseDto queryScheduleGroupMute(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQueryMuteRequest queryMuteRequest);

    /**
     * 调度组PTZ控制
     *
     * @param ssid
     * @param ptzControlRequest
     * @return
     */
    Boolean controlScheduleGroupPtz(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupPtzControlRequest ptzControlRequest);


    /**
     * 加入讨论组
     *
     * @param request
     * @return
     */
    List<String> joinScheduleGroupDiscussionGroup(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupJoinDiscussionGroupRequest request);

    /**
     * 离开讨论组
     *
     * @param request
     * @return
     */
    Boolean quitScheduleGroupDiscussionGroup(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQuitDiscussionGroupRequest request);

    /**
     * 查询讨论组
     *
     * @param request
     * @return
     */
    List<UmsScheduleGroupQueryDiscussionGroupResponseDto> queryScheduleGroupDiscussionGroup(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQueryDiscussionGroupRequest request);

    /**
     * 清空讨论组
     *
     * @param request
     * @return
     */
    Boolean clearScheduleGroupDiscussionGroup(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupClearDiscussionGroupRequest request);

    /**
     * 开始画面合成
     *
     * @param request
     * @return
     */
    String startScheduleGroupVmpMix(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupStartVmpMixRequest request);
//
//    /**
//     * 更新画面合成
//     *
//     * @param request
//     * @return
//     */
//    UmsScheduleGroupUpdateVmpMixResponseDto updateScheduleGroupVmpMix(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupUpdateVmpMixRequest request);
//
//    /**
//     * 停止画面合成
//     *
//     * @param request
//     * @return
//     */
//    Boolean stopScheduleGroupVmpMix(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupStopVmpMixRequest request);
//
////    /**
//     * 查询画面合成
//     *
//     * @param request
//     * @return
//     */
//    UmsScheduleGroupQueryVmpMixResponseDto queryScheduleGroupVmpMix(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQueryVmpMixRequest request);
//
//    /**
//     * 开始录像
//     *
//     * @param request
//     * @return
//     */
//    UmsStartRecResponseDto startRec(@HeadParam Integer ssid, @RequestBody UmsStartRecRequest request);
//
//    /**
//     * 停止录像
//     *
//     * @param request
//     * @return
//     */
//    Boolean stopRec(@HeadParam Integer ssid, @RequestBody UmsStopRecRequest request);
//
//    /**
//     * 查询录像列表
//     *
//     * @param request
//     * @return
//     */
//    UmsQueryRecListResponseDto queryRecList(@HeadParam Integer ssid, @RequestBody UmsQueryRecListRequest request);
//
//    /**
//     * 呼叫设备上线
//     *
//     * @param request
//     * @return
//     */
//    Boolean callUpSubDevice(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupSubDeviceCallUpRequest request);
//
//    /**
//     * 设置调度组广播源
//     *
//     * @param request
//     * @return
//     */
//    Boolean setScheduleGroupBroadcast(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupSetBroadcastRequest request);
//
//    /**
//     * 取消调度组广播源
//     *
//     * @param request
//     * @return
//     */
//    Boolean cancelScheduleGroupBroadcast(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupCancelBroadcastRequest request);
//
//    /**
//     * 查询调度组广播源
//     *
//     * @param request
//     * @return
//     */
//    UmsScheduleGroupQueryBroadcastResponseDto cancelScheduleGroupBroadcast(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQueryBroadcastRequest request);
//
//    /**
//     * 设置调度组媒体源
//     *
//     * @param request
//     * @return
//     */
//    Boolean setScheduleGroupMedia(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupSetMediaRequest request);
//
//    /**
//     * 查询调度组媒体源
//     *
//     * @param request
//     * @return
//     */
//    UmsScheduleGroupQueryMediaResponseDto queryScheduleGroupMedia(@HeadParam Integer ssid, @RequestBody UmsScheduleGroupQueryMediaRequest request);

}
