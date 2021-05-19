package com.kedacom.device.ums;

import com.kedacom.device.ums.response.QuerySubDeviceInfoResponse;
import com.kedacom.core.anno.KmProxy;
import com.kedacom.device.ums.request.*;
import com.kedacom.device.ums.response.*;

/**
 * @author van.shu
 * @create 2021/5/13 14:14
 */
@KmProxy
public interface UmsClient {

    /**
     * 登录
     * @param request 请求
     * @return 响应
     */
    LoginResponse login(LoginRequest request);

    /**
     * 注销
     * @param request 请求
     * @return 响应
     */
    LogoutResponse logout(LogoutRequest request);

    /**
     * 获取设备信息
     * @param request 请求
     * @return 响应
     */
    QuerySubDeviceInfoResponse querydev(QueryDeviceRequest request);

    /**
     * 获取所有设备分组
     * @param request 请求
     * @return 响应
     */
    QueryAllDeviceGroupResponse getalldevgroup(QueryAllDeviceGroupRequest request);

    /**
     * 查询调度组成员媒体源
     * @param request 请求
     * @return 响应
     */
    QueryMediaResponse queryscheduling(QueryMediaRequest request);

    /**
     * 设置调度组成员媒体源
     * @param request
     * @return
     */
    SetMediaResponse setscheduling(SetMediaRequest request);

    /**
     * 查询调度组成员广播源
     * @param request
     * @return
     */
    QueryBroadcastResponse querybroadcast(QueryBroadcastRequest request);

    /**
     * 取消调度组广播源
     * @param request
     * @return
     */
    CancelBroadcastResponse cancelbroadcast(CancelBroadcastRequest request);

    /**
     * 设置调度组广播源
     * @param request
     * @return
     */
    SetBroadcastResponse setbroadcast(SetBroadcastRequest request);

    /**
     * 呼叫设备上线
     * @param request
     * @return
     */
    CallUpResponse callmember(CallUpRequest request);

    /**
     * 查询画面合成
     * @param request
     * @return
     */
    QueryVmpMixResponse queryvmpmix(QueryVmpMixRequest request);

    /**
     * 停止画面合成
     * @param request
     * @return
     */
    StopVmpMixResponse stopvmpmix(StopVmpMixRequest request);

    /**
     * 更新画面合成
     * @param request
     * @return
     */
    UpdateVmpMixResponse updatevmpmix(UpdateVmpMixRequest request);

    /**
     * 开始合成画面
     * @param request
     * @return
     */
    StartVmpMixResponse startvmpmix(StartVmpMixRequest request);

    /**
     * 清空讨论组
     * @param request
     * @return
     */
    ClearDiscussionGroupResponse cleardiscussion(ClearDiscussionGroupRequest request);

    /**
     * 查询讨论组
     * @param request
     * @return
     */
    QueryDiscussionGroupResponse querydiscussion(QueryDiscussionGroupRequest request);

    /**
     * 推出讨论组
     * @param request
     * @return
     */
    QuitDiscussionGroupResponse quitdiscussion(QuitDiscussionGroupRequest request);

    /**
     * 加入讨论组
     * @param request
     * @return
     */
    JoinDiscussionGroupResponse joindiscussion(JoinDiscussionGroupRequest request);

    /**
     * 调度组PTZ控制
     * @param request
     * @return
     */
    PtzControlResponse groupptz(PtzControlRequest request);

    /**
     * 查询调度组哑音
     * @param request
     * @return
     */
    QueryMuteResponse querygroupmute(QueryMuteRequest request);

    /**
     * 设置调度组哑音
     * @param request
     * @return
     */
    SetMuteResponse setgroupmute(SetMuteRequest request);

    /**
     * 查询调度组静音
     * @param request
     * @return
     */
    QuerySilenceResponse querygroupsilence(QuerySilenceRequest request);

    /**
     * 设置调度组静音
     * @param request
     * @return
     */
    SetSilenceResponse setgroupsilence(SetSilenceRequest request);

    /**
     * 查询调度组信息
     * @param request
     * @return
     */
    QueryScheduleGroupResponse querygroup(QueryScheduleGroupRequest request);

    /**
     * 删除调度组成员设备
     * @param request
     * @return
     */
    DeleteMembersResponse deletegroupmember(DeleteMembersRequest request);

    /**
     * 添加调度组成员
     * @param request
     * @return
     */
    AddMembersResponse addgroupmember(AddMembersRequest request);

    /**
     * 删除调度组
     * @param request
     * @return
     */
    DeleteResponse deletegroup(DeleteRequest request);

    /**
     * 创建调度组
     * @param request
     * @return
     */
    CreateResponse creategroup(CreateRequest request);

}
