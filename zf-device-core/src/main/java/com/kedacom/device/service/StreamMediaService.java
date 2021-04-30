package com.kedacom.device.service;

import com.kedacom.BaseResult;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartAudioMixResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
public interface StreamMediaService {

    BaseResult<StartrecResponseVO> startRec(StartRecRequestDTO startrecRequestDTO);

    BaseResult<Boolean> stopRec(StopRecRequestDTO stoprecRequestDTO);

    BaseResult<QueryrecResponseVO> queryRec(QueryRecRequestDTO queryrecRequestDTO);

    BaseResult<StartAudioMixResponseVO> startAudioMix(StartAudioMixRequestDTO startAudioMixRequestDTO);

    BaseResult<Boolean> stopAudioMix(StopAudioMixRequestDTO stopAudioMixRequestDTO);

    BaseResult<Boolean> updateAudioMix(UpdateAudioMixRequestDTO updateAudioMixRequestDTO);

    BaseResult<List<String>> queryAllAudioMix(QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO);

    BaseResult<QueryAudioMixResponseVO> queryAudioMix(QueryAudioMixRequestDTO queryAudioMixRequestDTO);

}
