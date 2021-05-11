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

    StartRecResponseVO startRec(StartRecRequestDTO startrecRequestDTO);

    Boolean stopRec(StopRecRequestDTO stoprecRequestDTO);

    QueryRecResponseVO queryRec(QueryRecRequestDTO queryrecRequestDTO);

    String startAudioMix(StartAudioMixRequestDTO startAudioMixRequestDTO);

    Boolean stopAudioMix(StopAudioMixRequestDTO stopAudioMixRequestDTO);

    Boolean updateAudioMix(UpdateAudioMixRequestDTO updateAudioMixRequestDTO);

    List<String> queryAllAudioMix(QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO);

    QueryAudioMixResponseVO queryAudioMix(QueryAudioMixRequestDTO queryAudioMixRequestDTO);

    String startVideoMix(StartVideoMixRequestDTO startVideoMixRequestDTO);

    Boolean stopVideoMix(StopVideoMixRequestDTO stopVideoMixRequestDTO);

    Boolean updateVideoMix(UpdateVideoMixRequestDTO updateVideoMixRequestDTO);

    List<String> queryAllVideoMix(String unitId);

    QueryVideoMixResponseVO queryVideoMix(QueryVideoMixRequestDTO queryVideoMixRequestDTO);

}
