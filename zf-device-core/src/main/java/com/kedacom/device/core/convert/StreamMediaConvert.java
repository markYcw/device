package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.TransDataEntity;
import com.kedacom.device.core.event.TransDataNotifyEvent;
import com.kedacom.device.stream.request.*;
import com.kedacom.streamMedia.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/12 10:12
 */
@Mapper(componentModel = "spring")
public interface StreamMediaConvert {

    StreamMediaConvert INSTANCE = Mappers.getMapper(StreamMediaConvert.class);

    StartRecRequest convertStartRecRequest(StartRecDTO request);

    StopRecRequest convertStopRecRequest(StopRecDTO request);

    QueryRecRequest convertQueryRecRequest(QueryRecDTO request);

    StartAudioMixRequest convertStartAudioMixRequest(StartAudioMixDTO request);

    StopAudioMixRequest convertStopAudioMixRequest(StopAudioMixDTO request);

    UpdateAudioMixRequest convertUpdateAudioMixRequest(UpdateAudioMixDTO request);

    QueryAllAudioMixRequest convertQueryAllAudioMixRequest(QueryAllAudioMixDTO request);

    QueryAudioMixRequest convertQueryAudioMixRequest(QueryAudioMixDTO request);

    StartVideoMixRequest convertStartVideoMixRequest(StartVideoMixDTO request);

    StopVideoMixRequest convertStopVideoMixRequest(StopVideoMixDTO request);

    UpdateVideoMixRequest convertUpdateVideoMixRequest(UpdateVideoMixDTO request);

    QueryVideoMixRequest convertQueryVideoMixRequest(QueryVideoMixDTO request);

    SendTransDataRequest convertSendTransDataDTO(SendTransDataDTO request);

    TransDataEntity convertTransDataNotifyEvent(TransDataNotifyEvent event);

    QueryRealUrlRequest convertQueryRealUrlDTO(QueryRealUrlDTO request);

    QueryHistoryUrlRequest convertQueryHistoryUrlDTO(QueryHistoryUrlDTO request);

    SendOrderDataRequest convertSendOrderDataDTO(SendOrderDataDTO request);

    StartPushUrlRequest convertStartPushUrlDTO(StartPushUrlDTO request);

    StopPushUrlRequest convertStopPushUrlDTO(StopPushUrlDTO request);

    StartPullUrlRequest convertStartPullUrlDTO(StartPullUrlDTO request);

    StopPullUrlRequest convertStopPullUrlDTO(StopPullUrlDTO request);

}
