package com.kedacom.acl.network.unite;

import com.kedacom.BaseResult;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartAudioMixResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;

import java.util.List;

/**
 * 流媒体接口
 *
 * @author van.shu
 * @create 2021/4/26 17:30
 */
public interface StreamMediaInterface {

    /**
     * 开启录像
     * @param ssid
     * @param startrecRequestDTO
     * @return StartrecResponseVO
     */
    BaseResult<StartrecResponseVO> startRec(String ssid, StartRecRequestDTO startrecRequestDTO);

    /**
     * 停止录像
     * @param ssid
     * @param stoprecRequestDTO
     * @return Boolean
     */
    BaseResult<Boolean> stopRec(String ssid, StopRecRequestDTO stoprecRequestDTO);

    /**
     * 查询录像记录
     * @param ssid
     * @param queryrecRequestDTO
     * @return QueryrecResponseVO
     */
    BaseResult<QueryrecResponseVO> queryRec(String ssid, QueryRecRequestDTO queryrecRequestDTO);

    /**
     * 开启音频混音
     * @param ssid
     * @param startAudioMixRequestDTO
     * @return StartAudioMixResponseVO
     */
    BaseResult<StartAudioMixResponseVO> startAudioMix(String ssid, StartAudioMixRequestDTO startAudioMixRequestDTO);

    /**
     * 停止音频混音
     * @param ssid
     * @param stopAudioMixRequestDTO
     * @return Boolean
     */
    BaseResult<Boolean> stopAudioMix(String ssid, StopAudioMixRequestDTO stopAudioMixRequestDTO);

    /**
     * 更新音频混音
     * @param ssid
     * @param updateAudioMixRequestDTO
     * @return Boolean
     */
    BaseResult<Boolean> updateAudioMix(String ssid, UpdateAudioMixRequestDTO updateAudioMixRequestDTO);

    /**
     * 查询所有混音
     * @param ssid
     * @param queryAllAudioMixRequestDTO
     * @return List<String> 混音ID集合
     */
    BaseResult<List<String>> queryAllAudioMix(String ssid, QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO);

    /**
     * 查询混音信息
     * @param ssid
     * @param queryAudioMixRequestDTO
     * @return QueryAudioMixResponseVO
     */
    BaseResult<QueryAudioMixResponseVO> queryAudioMix(String ssid, QueryAudioMixRequestDTO queryAudioMixRequestDTO);

}
