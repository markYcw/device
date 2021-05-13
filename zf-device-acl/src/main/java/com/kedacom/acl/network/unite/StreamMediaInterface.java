package com.kedacom.acl.network.unite;


import com.kedacom.acl.network.anno.HeadParam;
import com.kedacom.acl.network.anno.RequestBody;
import com.kedacom.acl.network.data.streamMeida.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryRecResponseVO;
import com.kedacom.streamMedia.response.QueryVideoMixResponseVO;
import com.kedacom.streamMedia.response.StartRecResponseVO;

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
     *
     * @param ssid
     * @param startrecRequestDTO
     * @return StartrecResponseVO
     */
    StartRecResponseVO startRec(@HeadParam Integer ssid, @RequestBody StartRecRequestDTO startrecRequestDTO);

    /**
     * 停止录像
     *
     * @param ssid
     * @param stoprecRequestDTO
     * @return Boolean
     */
    Boolean stopRec(@HeadParam Integer ssid, @RequestBody StopRecRequestDTO stoprecRequestDTO);

    /**
     * 查询录像记录
     *
     * @param ssid
     * @param queryrecRequestDTO
     * @return QueryrecResponseVO
     */
    QueryRecResponseVO queryRec(@HeadParam Integer ssid, @RequestBody QueryRecRequestDTO queryrecRequestDTO);

    /**
     * 开启音频混音
     *
     * @param ssid
     * @param startAudioMixRequestDTO
     * @return StartAudioMixResponseVO
     */
    String startAudioMix(@HeadParam Integer ssid, @RequestBody StartAudioMixRequestDTO startAudioMixRequestDTO);

    /**
     * 停止音频混音
     *
     * @param ssid
     * @param stopAudioMixRequestDTO
     * @return Boolean
     */
    Boolean stopAudioMix(@HeadParam Integer ssid, @RequestBody StopAudioMixRequestDTO stopAudioMixRequestDTO);

    /**
     * 更新音频混音
     *
     * @param ssid
     * @param updateAudioMixRequestDTO
     * @return Boolean
     */
    Boolean updateAudioMix(@HeadParam Integer ssid, @RequestBody UpdateAudioMixRequestDTO updateAudioMixRequestDTO);

    /**
     * 查询所有混音
     *
     * @param ssid
     * @param queryAllAudioMixRequestDTO
     * @return List<String> 混音ID集合
     */
    List<String> queryAllAudioMix(@HeadParam Integer ssid, @RequestBody QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO);

    /**
     * 查询混音信息
     *
     * @param ssid
     * @param queryAudioMixRequestDTO
     * @return QueryAudioMixResponseVO
     */
    QueryAudioMixResponseVO queryAudioMix(@HeadParam Integer ssid, @RequestBody QueryAudioMixRequestDTO queryAudioMixRequestDTO);

    /**
     * 开始画面合成
     *
     * @param ssid
     * @param startVideoMixRequestDTO
     * @return StartVideoMixResponseVO
     */
    String startVideoMix(@HeadParam Integer ssid, @RequestBody StartVideoMixRequestDTO startVideoMixRequestDTO);

    /**
     * 停止画面合成
     *
     * @param ssid
     * @param stopVideoMixRequestDTO
     * @return Boolean
     */
    Boolean stopVideoMix(@HeadParam Integer ssid, @RequestBody StopVideoMixRequestDTO stopVideoMixRequestDTO);

    /**
     * 更新画面合成
     *
     * @param ssid
     * @param updateVideoMixRequestDTO
     * @return Boolean
     */
    Boolean updateVideoMix(@HeadParam Integer ssid, @RequestBody UpdateVideoMixRequestDTO updateVideoMixRequestDTO);

    /**
     * 查询所有画面合成
     *
     * @param ssid
     * @return List<String> 合成ID集合
     */
    List<String> queryAllVideoMix(@HeadParam Integer ssid);

    /**
     * 查询画面信息
     *
     * @param ssid
     * @param queryVideoMixRequestDTO
     * @return QueryVideoMixResponseVO
     */
    QueryVideoMixResponseVO queryVideoMix(@HeadParam Integer ssid, @RequestBody QueryVideoMixRequestDTO queryVideoMixRequestDTO);

}
