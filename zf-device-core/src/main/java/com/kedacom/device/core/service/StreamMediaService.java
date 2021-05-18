package com.kedacom.device.core.service;

import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
public interface StreamMediaService {

    StartRecResponseVO startRec(StartRecDTO request);

    Boolean stopRec(StopRecDTO request);

    QueryRecResponseVO queryRec(QueryRecDTO request);

    StartAudioMixResponseVO startAudioMix(StartAudioMixDTO request);

    Boolean stopAudioMix(StopAudioMixDTO request);

    Boolean updateAudioMix(UpdateAudioMixDTO request);

    List<String> queryAllAudioMix(QueryAllAudioMixDTO request);

    QueryAudioMixResponseVO queryAudioMix(QueryAudioMixDTO request);

    StartVideoMixResponseVO startVideoMix(StartVideoMixDTO request);

    Boolean stopVideoMix(StopVideoMixDTO request);

    Boolean updateVideoMix(UpdateVideoMixDTO request);

    QueryAllAudioMixVO queryAllVideoMix(QueryAllVideoMixDTO umsId);

    QueryVideoMixResponseVO queryVideoMix(QueryVideoMixDTO request);

}
