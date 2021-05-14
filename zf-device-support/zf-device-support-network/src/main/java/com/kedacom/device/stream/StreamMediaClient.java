package com.kedacom.device.stream;


import com.kedacom.core.anno.KmProxy;
import com.kedacom.device.stream.request.*;
import com.kedacom.device.stream.response.*;

/**
 * 流媒体接口
 *
 * @author van.shu
 * @create 2021/4/26 17:30
 */
@KmProxy
public interface StreamMediaClient {

    /**
     * 开启录像
     *
     * @param StartRecDTO
     * @return StartrecResponse
     */
    StartRecResponse startRec(StartRecDTO startRecDTO);

    /**
     * 停止录像
     *
     * @param StopRecDTO
     * @return Boolean
     */
    StreamMediaResponse stopRec(StopRecDTO stoprecDTO);

    /**
     * 查询录像记录
     *
     * @param QueryRecDTO
     * @return QueryrecResponse
     */
    QueryRecResponse queryRec(QueryRecDTO queryrecDTO);

    /**
     * 开启音频混音
     *
     * @param StartAudioMixDTO
     * @return StartAudioMixResponse
     */
    StartAudioMixResponse startAudioMix(StartAudioMixDTO startAudioMixDTO);

    /**
     * 停止音频混音
     *
     * @param StopAudioMixDTO
     * @return Boolean
     */
    StreamMediaResponse stopAudioMix(StopAudioMixDTO stopAudioMixDTO);

    /**
     * 更新音频混音
     *
     * @param UpdateAudioMixDTO
     * @return Boolean
     */
    StreamMediaResponse updateAudioMix(UpdateAudioMixDTO updateAudioMixDTO);

    /**
     * 查询所有混音
     *
     * @param QueryAllAudioMixDTO
     * @return List<String> 混音ID集合
     */
    QueryAllAudioMixResponse queryAllAudioMix(QueryAllAudioMixDTO queryAllAudioMixDTO);

    /**
     * 查询混音信息
     *
     * @param QueryAudioMixDTO
     * @return QueryAudioMixResponse
     */
    QueryAudioMixResponse queryAudioMix(QueryAudioMixDTO queryAudioMixDTO);

    /**
     * 开始画面合成
     *
     * @param StartVideoMixDTO
     * @return StartVideoMixResponse
     */
    StartVideoMixResponse startVideoMix(StartVideoMixDTO startVideoMixDTO);

    /**
     * 停止画面合成
     *
     * @param StopVideoMixDTO
     * @return Boolean
     */
    StreamMediaResponse stopVideoMix(StopVideoMixDTO stopVideoMixDTO);

    /**
     * 更新画面合成
     *
     * @param UpdateVideoMixDTO
     * @return Boolean
     */
    StreamMediaResponse updateVideoMix(UpdateVideoMixDTO updateVideoMixDTO);

    /**
     * 查询所有画面合成
     *
     * @param ssid
     * @return List<String> 合成ID集合
     */
    QueryAllAudioMixResponse queryAllVideoMix(QueryAllVideoMixDTO queryAllVideoMixDTO);

    /**
     * 查询画面信息
     *
     * @param ssid
     * @param queryVideoMixDTO
     * @return QueryVideoMixResponse
     */
    QueryVideoMixResponse queryVideoMix(QueryVideoMixDTO queryVideoMixDTO);

}
