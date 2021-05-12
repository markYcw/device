package com.kedacom.device.core.service;

import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryRecResponseVO;
import com.kedacom.streamMedia.response.QueryVideoMixResponseVO;
import com.kedacom.streamMedia.response.StartRecResponseVO;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
public interface StreamMediaService {

    StartRecResponseVO startRec(StartRecRequest request);

    Boolean stopRec(StopRecRequest request);

    QueryRecResponseVO queryRec(QueryRecRequest request);

    String startAudioMix(StartAudioMixRequest request);

    Boolean stopAudioMix(StopAudioMixRequest request);

    Boolean updateAudioMix(UpdateAudioMixRequest request);

    List<String> queryAllAudioMix(QueryAllAudioMixRequest request);

    QueryAudioMixResponseVO queryAudioMix(QueryAudioMixRequest request);

    String startVideoMix(StartVideoMixRequest request);

    Boolean stopVideoMix(StopVideoMixRequest request);

    Boolean updateVideoMix(UpdateVideoMixRequest request);

    List<String> queryAllVideoMix(String unitId);

    QueryVideoMixResponseVO queryVideoMix(QueryVideoMixRequest request);

}
