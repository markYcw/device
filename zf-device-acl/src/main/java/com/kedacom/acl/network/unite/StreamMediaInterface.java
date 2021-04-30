package com.kedacom.acl.network.unite;

import com.kedacom.BaseResult;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;

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
    BaseResult<StartRecResponseVO> startRec(String ssid, StartRecRequestDTO startrecRequestDTO);

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
    BaseResult<QueryRecResponseVO> queryRec(String ssid, QueryRecRequestDTO queryrecRequestDTO);

    /**
     * 开启音频混音
     * @param ssid
     * @param startAudioMixRequestDTO
     * @return StartAudioMixResponseVO
     */
    BaseResult<String> startAudioMix(String ssid, StartAudioMixRequestDTO startAudioMixRequestDTO);

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

    /**
     *  开始画面合成
     * @param ssid
     * @param startVideoMixRequestDTO
     * @return StartVideoMixResponseVO
     */
    BaseResult<String> startVideoMix(String ssid, StartVideoMixRequestDTO startVideoMixRequestDTO);

    /**
     * 停止画面合成
     * @param ssid
     * @param stopVideoMixRequestDTO
     * @return Boolean
     */
    BaseResult<Boolean> stopVideoMix(String ssid, StopVideoMixRequestDTO stopVideoMixRequestDTO);

    /**
     * 更新画面合成
     * @param ssid
     * @param updateVideoMixRequestDTO
     * @return Boolean
     */
    BaseResult<Boolean> updateVideoMix(String ssid, UpdateVideoMixRequestDTO updateVideoMixRequestDTO);

    /**
     * 查询所有画面合成
     * @param ssid
     * @return List<String> 合成ID集合
     */
    BaseResult<List<String>> queryAllVideoMix(String ssid);

    /**
     * 查询画面信息
     * @param ssid
     * @param queryVideoMixRequestDTO
     * @return QueryVideoMixResponseVO
     */
    BaseResult<QueryVideoMixResponseVO> queryVideoMix(String ssid, QueryVideoMixRequestDTO queryVideoMixRequestDTO);

}
