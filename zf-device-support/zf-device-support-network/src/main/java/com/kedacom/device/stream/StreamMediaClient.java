package com.kedacom.device.stream;


import com.kedacom.core.anno.KmProxy;
import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.device.stream.request.*;
import com.kedacom.device.stream.response.*;

/**
 * 流媒体接口
 *
 * @author van.shu
 * @create 2021/4/26 17:30
 */
@KmProxy
public interface StreamMediaClient {

    /**
     * 开启录像
     *
     * @param startRecRequest
     * @return StartRecResponse
     */
    StartRecResponse startRec(StartRecRequest startRecRequest);

    /**
     * 停止录像
     *
     * @param stoprecRequest
     * @return BaseResponse
     */
    BaseResponse stopRec(StopRecRequest stoprecRequest);

    /**
     * 更新录像
     *
     * @param updateRecRequest
     * @return
     */
    UpdateRecResponse updateRec(UpdateRecRequest updateRecRequest);


    /**
     * 查询录像记录
     *
     * @param queryrecRequest
     * @return QueryRecResponse
     */
    QueryRecResponse queryRec(QueryRecRequest queryrecRequest);

    /**
     * 开启音频混音
     *
     * @param startAudioMixRequest
     * @return StartAudioMixResponse
     */
    StartAudioMixResponse startAudioMix(StartAudioMixRequest startAudioMixRequest);

    /**
     * 停止音频混音
     *
     * @param stopAudioMixRequest
     * @return BaseResponse
     */
    BaseResponse stopAudioMix(StopAudioMixRequest stopAudioMixRequest);

    /**
     * 更新音频混音
     *
     * @param updateAudioMixRequest
     * @return BaseResponse
     */
    BaseResponse updateAudioMix(UpdateAudioMixRequest updateAudioMixRequest);

    /**
     * 查询所有混音
     *
     * @param queryAllAudioMixRequest
     * @return QueryAllAudioMixResponse
     */
    QueryAllAudioMixResponse queryAllAudioMix(QueryAllAudioMixRequest queryAllAudioMixRequest);

    /**
     * 查询混音信息
     *
     * @param queryAudioMixRequest
     * @return QueryAudioMixResponse
     */
    QueryAudioMixResponse queryAudioMix(QueryAudioMixRequest queryAudioMixRequest);

    /**
     * 开始画面合成
     *
     * @param startVideoMixRequest
     * @return StartVideoMixResponse
     */
    StartVideoMixResponse startVideoMix(StartVideoMixRequest startVideoMixRequest);

    /**
     * 停止画面合成
     *
     * @param stopVideoMixRequest
     * @return BaseResponse
     */
    BaseResponse stopVideoMix(StopVideoMixRequest stopVideoMixRequest);

    /**
     * 更新画面合成
     *
     * @param updateVideoMixRequest
     * @return BaseResponse
     */
    BaseResponse updateVideoMix(UpdateVideoMixRequest updateVideoMixRequest);

    /**
     * 查询所有画面合成
     *
     * @param queryAllVideoMixRequest
     * @return QueryAllAudioMixResponse
     */
    QueryAllAudioMixResponse queryAllVideoMix(QueryAllVideoMixRequest queryAllVideoMixRequest);

    /**
     * 查询画面信息
     *
     * @param queryVideoMixRequest
     * @return QueryVideoMixResponse
     */
    QueryVideoMixResponse queryVideoMix(QueryVideoMixRequest queryVideoMixRequest);

    /**
     * 发送透明通道数据
     *
     * @param sendTransDataRequest
     * @return SendTransDataResponse
     */
    SendTransDataResponse sendTransData(SendTransDataRequest sendTransDataRequest);

    /**
     * 查询实时资源URL
     *
     * @param queryRealUrlRequest
     * @return
     */
    QueryRealUrlResponse queryRealUrl(QueryRealUrlRequest queryRealUrlRequest);

    /**
     * 查询历史资源URL
     *
     * @param queryHistoryUrlRequest
     * @return
     */
    QueryHistoryUrlResponse queryHistoryUrl(QueryHistoryUrlRequest queryHistoryUrlRequest);

    /**
     * 开始推送媒体流
     *
     * @param startPushUrlRequest
     * @return
     */
    StartPushUrlResponse startPushUrl(StartPushUrlRequest startPushUrlRequest);

    /**
     * 停止推送媒体流
     *
     * @param stopPushUrlRequest
     * @return
     */
    StopPushUrlResponse stopPushUrl(StopPushUrlRequest stopPushUrlRequest);

    /**
     * 开始拉取媒体流
     *
     * @param startPullUrlRequest
     * @return
     */
    StartPullUrlResponse startPullUrl(StartPullUrlRequest startPullUrlRequest);

    /**
     * 停止拉取媒体流
     *
     * @param stopPullUrlRequest
     * @return
     */
    StopPullUrlResponse stopPullUrl(StopPullUrlRequest stopPullUrlRequest);

    /**
     * 合成画面保活
     *
     * @param keepVideoMixAlive
     * @return
     */
    VideoMixKeepAliveResponse keepVideoMixAlive(VideoMixKeepAliveRequest keepVideoMixAlive);

    /**
     * 录像任务保活
     *
     * @param recKeepAliveRequest
     * @return
     */
    BaseResponse recKeepAlive(RecKeepAliveRequest recKeepAliveRequest);

    /**
     * 发送宏指令数据
     *
     * @param sendOrderDataRequest
     * @return
     */
    SendOrderDataResponse sendOrderData(SendOrderDataRequest sendOrderDataRequest);

    /**
     * 获取音频能力集
     *
     * @param getAudioCapRequest
     * @return
     */
    GetAudioCapResponse getAudioCap(GetAudioCapRequest getAudioCapRequest);

    /**
     * 控制音频功率上报
     *
     * @param ctrlAudioActRequest
     * @return
     */
    CtrlAudioActResponse ctrlAudioAct(CtrlAudioActRequest ctrlAudioActRequest);

    /**
     * 设置音频功率上报间隔
     *
     * @param setAudioActIntervalRequest
     * @return
     */
    SetAudioActIntervalResponse setAudioActInterval(SetAudioActIntervalRequest setAudioActIntervalRequest);

    /**
     * 刻录状态请求
     *
     * @param getBurnStateRequest
     * @return
     */
    GetBurnStateResponse getBurnState(GetBurnStateRequest getBurnStateRequest);

    /**
     * 获取当前语音激励状态
     *
     * @param request
     * @return
     */
    GetSvrAudioActStateResponse getSvrAudioActState(GetSvrAudioActStateRequest request);

    QueryMeetRecResponse recMeetQuery(QueryMeetRecRequest queryMeetRecRequest);

    StartMeetRecResponse startRecMeet(StartMeetRecRequest recMeetRequest);

    StopMeetRecResponse stopMeetRec(StopMeetRecRequest recRequest);

    UpdateMeetRecResponse updateMeetRec(UpdateMeetRecRequest recRequest);

    MeetRecKeepAliveResponse meetRecKeepAlive(MeetRecKeepAliveRequest aliveRequest);

    QueryMeetRecTaskResponse queryMeetRecTask(QueryMeetRecTaskRequest taskRequest);

    DeleteMeetRecResponse deleteMeetRec(DeleteMeetRecRequest recRequest);

    MeetRecordConfigResponse meetRecordConfig(MeetRecordConfigRequest configRequest);

    QueryMeetRecordConfigResponse queryMeetRecordConfig(QueryMeetRecordConfigRequest configRequest);

    QueryMeetRecordCapResponse queryMeetRecordCap(QueryMeetRecordCapRequest capRequest);

}
