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
     * @param StartRecRequest
     * @return StartRecResponse
     */
    StartRecResponse startRec(StartRecRequest startRecRequest);

    /**
     * 停止录像
     *
     * @param StopRecRequest
     * @return BaseResponse
     */
    BaseResponse stopRec(StopRecRequest stoprecRequest);

    /**
     * 查询录像记录
     *
     * @param QueryRecRequest
     * @return QueryRecResponse
     */
    QueryRecResponse queryRec(QueryRecRequest queryrecRequest);

    /**
     * 开启音频混音
     *
     * @param StartAudioMixRequest
     * @return StartAudioMixResponse
     */
    StartAudioMixResponse startAudioMix(StartAudioMixRequest startAudioMixRequest);

    /**
     * 停止音频混音
     *
     * @param StopAudioMixRequest
     * @return BaseResponse
     */
    BaseResponse stopAudioMix(StopAudioMixRequest stopAudioMixRequest);

    /**
     * 更新音频混音
     *
     * @param UpdateAudioMixRequest
     * @return BaseResponse
     */
    BaseResponse updateAudioMix(UpdateAudioMixRequest updateAudioMixRequest);

    /**
     * 查询所有混音
     *
     * @param QueryAllAudioMixRequest
     * @return QueryAllAudioMixResponse
     */
    QueryAllAudioMixResponse queryAllAudioMix(QueryAllAudioMixRequest queryAllAudioMixRequest);

    /**
     * 查询混音信息
     *
     * @param QueryAudioMixRequest
     * @return QueryAudioMixResponse
     */
    QueryAudioMixResponse queryAudioMix(QueryAudioMixRequest queryAudioMixRequest);

    /**
     * 开始画面合成
     *
     * @param StartVideoMixRequest
     * @return StartVideoMixResponse
     */
    StartVideoMixResponse startVideoMix(StartVideoMixRequest startVideoMixRequest);

    /**
     * 停止画面合成
     *
     * @param StopVideoMixRequest
     * @return BaseResponse
     */
    BaseResponse stopVideoMix(StopVideoMixRequest stopVideoMixRequest);

    /**
     * 更新画面合成
     *
     * @param UpdateVideoMixRequest
     * @return BaseResponse
     */
    BaseResponse updateVideoMix(UpdateVideoMixRequest updateVideoMixRequest);

    /**
     * 查询所有画面合成
     *
     * @param QueryAllVideoMixRequest
     * @return QueryAllAudioMixResponse
     */
    QueryAllAudioMixResponse queryAllVideoMix(QueryAllVideoMixRequest queryAllVideoMixRequest);

    /**
     * 查询画面信息
     *
     * @param QueryVideoMixRequest
     * @return QueryVideoMixResponse
     */
    QueryVideoMixResponse queryVideoMix(QueryVideoMixRequest queryVideoMixRequest);

}
