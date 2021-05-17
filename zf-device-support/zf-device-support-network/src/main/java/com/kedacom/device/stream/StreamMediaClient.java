package com.kedacom.device.stream;


import com.kedacom.core.anno.KmProxy;
import com.kedacom.core.pojo.BaseResponse;
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
    StartRecResponse startRec(StartRecRequest startRecRequest);

    /**
     * 停止录像
     *
     * @param StopRecDTO
     * @return Boolean
     */
    BaseResponse stopRec(StopRecRequest stoprecRequest);

    /**
     * 查询录像记录
     *
     * @param QueryRecDTO
     * @return QueryrecResponse
     */
    QueryRecResponse queryRec(QueryRecRequest queryrecRequest);

    /**
     * 开启音频混音
     *
     * @param StartAudioMixDTO
     * @return StartAudioMixResponse
     */
    StartAudioMixResponse startAudioMix(StartAudioMixRequest startAudioMixRequest);

    /**
     * 停止音频混音
     *
     * @param StopAudioMixDTO
     * @return Boolean
     */
    BaseResponse stopAudioMix(StopAudioMixRequest stopAudioMixRequest);

    /**
     * 更新音频混音
     *
     * @param UpdateAudioMixDTO
     * @return Boolean
     */
    BaseResponse updateAudioMix(UpdateAudioMixRequest updateAudioMixRequest);

    /**
     * 查询所有混音
     *
     * @param QueryAllAudioMixDTO
     * @return List<String> 混音ID集合
     */
    QueryAllAudioMixResponse queryAllAudioMix(QueryAllAudioMixRequest queryAllAudioMixRequest);

    /**
     * 查询混音信息
     *
     * @param QueryAudioMixDTO
     * @return QueryAudioMixResponse
     */
    QueryAudioMixResponse queryAudioMix(QueryAudioMixRequest queryAudioMixRequest);

    /**
     * 开始画面合成
     *
     * @param StartVideoMixDTO
     * @return StartVideoMixResponse
     */
    StartVideoMixResponse startVideoMix(StartVideoMixRequest startVideoMixRequest);

    /**
     * 停止画面合成
     *
     * @param StopVideoMixDTO
     * @return Boolean
     */
    BaseResponse stopVideoMix(StopVideoMixRequest stopVideoMixRequest);

    /**
     * 更新画面合成
     *
     * @param UpdateVideoMixDTO
     * @return Boolean
     */
    BaseResponse updateVideoMix(UpdateVideoMixRequest updateVideoMixRequest);

    /**
     * 查询所有画面合成
     *
     * @param ssid
     * @return List<String> 合成ID集合
     */
    QueryAllAudioMixResponse queryAllVideoMix(QueryAllVideoMixRequest queryAllVideoMixRequest);

    /**
     * 查询画面信息
     *
     * @param ssid
     * @param queryVideoMixRequest
     * @return QueryVideoMixResponse
     */
    QueryVideoMixResponse queryVideoMix(QueryVideoMixRequest queryVideoMixRequest);

}
