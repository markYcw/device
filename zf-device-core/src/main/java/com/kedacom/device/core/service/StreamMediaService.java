package com.kedacom.device.core.service;

import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.streamMedia.request.StartMeetRecDTO;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
public interface StreamMediaService {

    StartRecResponseVO startRec(StartRecDTO request);

    Boolean stopRec(StopRecDTO request);

    Boolean updateRec(UpdateRecDTO updateRecDTO);

    QueryRecResponseVO queryRec(QueryRecDTO request);

    StartAudioMixResponseVO startAudioMix(StartAudioMixDTO request);

    Boolean stopAudioMix(StopAudioMixDTO request);

    Boolean updateAudioMix(UpdateAudioMixDTO request);

    QueryAllAudioMixVO queryAllAudioMix(QueryAllAudioMixDTO request);

    QueryAudioMixResponseVO queryAudioMix(QueryAudioMixDTO request);

    StartVideoMixResponseVO startVideoMix(StartVideoMixDTO request);

    Boolean stopVideoMix(StopVideoMixDTO request);

    Boolean updateVideoMix(UpdateVideoMixDTO request);

    QueryAllAudioMixVO queryAllVideoMix(QueryAllVideoMixDTO umsId);

    QueryVideoMixResponseVO queryVideoMix(QueryVideoMixDTO request);

    Boolean sendTransData(SendTransDataDTO request);

    QueryRealUrlVO queryRealUrl(QueryRealUrlDTO queryRealUrlDTO);

    QueryHistoryUrlVO queryHistoryUrl(QueryHistoryUrlDTO queryHistoryUrlDTO);

    Boolean sendOrderData(SendOrderDataDTO sendOrderDataDTO);

    StartPushUrlVO startPushUrl(StartPushUrlDTO startPushUrlDTO);

    Boolean stopPushUrl(StopPushUrlDTO stopPushUrlDTO);

    StartPullUrlVO startPullUrl(StartPullUrlDTO startPullUrlDTO);

    Boolean stopPullUrl(StopPullUrlDTO stopPullUrlDTO);

    Boolean keepVideoMixAlive(VideoMixKeepAliveDTO request);

    Boolean recKeepAlive(RecKeepAliveDTO request);

    GetAudioCapVO getAudioCap(GetAudioCapDTO getAudioCapDTO);

    Boolean ctrlAudioAct(CtrlAudioActDTO ctrlAudioActDTO);

    Boolean setAudioActInterval(SetAudioActIntervalDTO setAudioActIntervalDTO);

    GetBurnStateVO getBurnState(GetBurnStateDTO getBurnStateDTO);

    DeviceInfoEntity getBySsid(Integer ssid);

    GetSvrAudioActStateVo getSvrAudioActState(GetSvrAudioActStateDTO dto);


    QueryMeetRecVO queryMeetRec(QueryMeetRecDTO dto);

    StartMeetRecResponseVO startMeetRec(StartMeetRecDTO dto);

    Boolean stopMeetRec(StopMeetRecDTO dto);

    Boolean updateMeetRec(UpdateMeetRecDTO dto);

    Boolean meetRecKeepAlive(MeetRecKeepAliveDTO dto);

    QueryMeetRecTaskVO queryMeetRecTask(QueryMeetRecTaskDTO dto);

    Boolean deleteMeetRec(DeleteMeetRecDTO dto);

    Boolean meetRecordConfig(MeetRecordConfigDTO dto);

    QueryMeetRecordConfigVO queryMeetRecordConfig(QueryMeetRecordConfigDTO dto);

    QueryMeetRecordCapVO queryMeetRecordCap(QueryMeetRecordCapDTO dto);
}
