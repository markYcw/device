package com.kedacom.device.api.streammedia;

import com.kedacom.BaseResult;
import com.kedacom.device.api.streammedia.fallback.StreamMediaApiFallbackFactory;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 10:19
 */
@FeignClient(value = "device-server", contextId = "streamMediaApi", path = "/api-device/device/streamMedia", fallbackFactory = StreamMediaApiFallbackFactory.class)
public interface StreamMediaApi {


    /**
     * 开启录像
     *
     * @param startrecDTO
     * @param br
     * @return
     */
    @PostMapping("/startRec")
    BaseResult<StartRecResponseVO> startRec(@RequestBody StartRecDTO startrecDTO);

    /**
     * 停止录像
     *
     * @param stoprecDTO
     * @param br
     * @return
     */
    @PostMapping("/stopRec")
    BaseResult<Boolean> stopRec(@RequestBody StopRecDTO stoprecDTO);

    /**
     * 查询录像记录
     *
     * @param queryrecDTO
     * @param br
     * @return
     */
    @PostMapping("/queryRec")
    BaseResult<QueryRecResponseVO> queryRec(@RequestBody QueryRecDTO queryrecDTO);

    /**
     * 开启音频混音:返回混音设备分组id、混音ID
     *
     * @param startAudioMixDTO
     * @param br
     * @return
     */
    @PostMapping("/startAudioMix")
    BaseResult<StartAudioMixResponseVO> startAudioMix(@RequestBody StartAudioMixDTO startAudioMixDTO);

    /**
     * 停止音频混音
     *
     * @param stopAudioMixDTO
     * @param br
     * @return
     */
    @PostMapping("/stopAudioMix")
    BaseResult<Boolean> stopAudioMix(@RequestBody StopAudioMixDTO stopAudioMixDTO);

    /**
     * 更新音频混音
     *
     * @param updateAudioMixDTO
     * @param br
     * @return
     */
    @PostMapping("/updateAudioMix")
    BaseResult<Boolean> updateAudioMix(@RequestBody UpdateAudioMixDTO updateAudioMixDTO);


    /**
     * 查询所有混音 返回混音ID集合
     *
     * @param queryAllAudioMixDTO
     * @param br
     * @return
     */
    @PostMapping("/queryAllAudioMix")
    BaseResult<QueryAllAudioMixVO> queryAllAudioMix(@RequestBody QueryAllAudioMixDTO queryAllAudioMixDTO);

    /**
     * 查询混音信息
     *
     * @param queryAudioMixDTO
     * @param br
     * @return
     */
    @PostMapping("/queryAudioMix")
    BaseResult<QueryAudioMixResponseVO> queryAudioMix(@RequestBody QueryAudioMixDTO queryAudioMixDTO);

    /**
     * 开始画面合成: 返回画面合成设备分组id、画面合成ID
     *
     * @param startVideoMixDTO
     * @param br
     * @return
     */
    @PostMapping("/startVideoMix")
    BaseResult<StartVideoMixResponseVO> startVideoMix(@RequestBody StartVideoMixDTO startVideoMixDTO);

    /**
     * 停止画面合成
     *
     * @param stopVideoMixDTO
     * @param br
     * @return
     */
    @PostMapping("/stopVideoMix")
    BaseResult<Boolean> stopVideoMix(@RequestBody StopVideoMixDTO stopVideoMixDTO);

    /**
     * 更新画面合成
     *
     * @param updateVideoMixDTO
     * @param br
     * @return
     */
    @PostMapping("/updateVideoMix")
    BaseResult<Boolean> updateVideoMix(@RequestBody UpdateVideoMixDTO updateVideoMixDTO);

    /**
     * 查询所有画面合成:返回mixIDs 合成ID集合
     *
     * @param queryAllVideoMixDTO
     * @param br
     * @return
     */
    @PostMapping("/queryAllVideoMix")
    BaseResult<QueryAllAudioMixVO> queryAllVideoMix(@RequestBody QueryAllVideoMixDTO queryAllVideoMixDTO);

    /**
     * 查询画面合成信息
     *
     * @param queryVideoMixDTO
     * @param br
     * @return
     */
    @PostMapping("/queryVideoMix")
    BaseResult<QueryVideoMixResponseVO> queryVideoMix(@RequestBody QueryVideoMixDTO queryVideoMixDTO);

    /**
     * 发送透明通道数据
     *
     * @param sendTransDataDTO
     * @param br
     * @return
     */
    @PostMapping("/sendTransData")
    BaseResult<Boolean> sendTransData(@RequestBody SendTransDataDTO sendTransDataDTO);

}
