package com.kedacom.device.service;

import com.kedacom.BaseResult;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
public interface StreamMediaService {

    BaseResult<StartRecResponseVO> startRec(StartRecRequestDTO startrecRequestDTO);

    BaseResult<Boolean> stopRec(StopRecRequestDTO stoprecRequestDTO);

    BaseResult<QueryRecResponseVO> queryRec(QueryRecRequestDTO queryrecRequestDTO);

    BaseResult<String> startAudioMix(StartAudioMixRequestDTO startAudioMixRequestDTO);

    BaseResult<Boolean> stopAudioMix(StopAudioMixRequestDTO stopAudioMixRequestDTO);

    BaseResult<Boolean> updateAudioMix(UpdateAudioMixRequestDTO updateAudioMixRequestDTO);

    BaseResult<List<String>> queryAllAudioMix(QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO);

    BaseResult<QueryAudioMixResponseVO> queryAudioMix(QueryAudioMixRequestDTO queryAudioMixRequestDTO);

    BaseResult<String> startVideoMix(StartVideoMixRequestDTO startVideoMixRequestDTO);

    BaseResult<Boolean> stopVideoMix(StopVideoMixRequestDTO stopVideoMixRequestDTO);

    BaseResult<Boolean> updateVideoMix(UpdateVideoMixRequestDTO updateVideoMixRequestDTO);

    BaseResult<List<String>> queryAllVideoMix(String unitId);

    BaseResult<QueryVideoMixResponseVO> queryVideoMix(QueryVideoMixRequestDTO queryVideoMixRequestDTO);

}
