package com.kedacom.device.api.streammedia;

import com.kedacom.BaseResult;
import com.kedacom.device.api.streammedia.fallback.StreamMediaApiFallbackFactory;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

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
     * @return
     */
    @PostMapping("/startRec")
    BaseResult<StartRecResponseVO> startRec(@RequestBody StartRecDTO startrecDTO);

    /**
     * 停止录像
     *
     * @param stoprecDTO
     * @return
     */
    @PostMapping("/stopRec")
    BaseResult<Boolean> stopRec(@RequestBody StopRecDTO stoprecDTO);

    /**
     * 查询录像记录
     *
     * @param queryrecDTO
     * @return
     */
    @PostMapping("/queryRec")
    BaseResult<QueryRecResponseVO> queryRec(@RequestBody QueryRecDTO queryrecDTO);

    /**
     * 录像任务保活
     *
     * @param dto
     * @return
     */
    @PostMapping("/recKeepAlive")
    BaseResult<Boolean> recKeepAlive(@RequestBody RecKeepAliveDTO dto);

    /**
     * 开启音频混音:返回混音设备分组id、混音ID
     *
     * @param startAudioMixDTO
     * @return
     */
    @PostMapping("/startAudioMix")
    BaseResult<StartAudioMixResponseVO> startAudioMix(@RequestBody StartAudioMixDTO startAudioMixDTO);

    /**
     * 停止音频混音
     *
     * @param stopAudioMixDTO
     * @return
     */
    @PostMapping("/stopAudioMix")
    BaseResult<Boolean> stopAudioMix(@RequestBody StopAudioMixDTO stopAudioMixDTO);

    /**
     * 更新音频混音
     *
     * @param updateAudioMixDTO
     * @return
     */
    @PostMapping("/updateAudioMix")
    BaseResult<Boolean> updateAudioMix(@RequestBody UpdateAudioMixDTO updateAudioMixDTO);


    /**
     * 查询所有混音 返回混音ID集合
     *
     * @param queryAllAudioMixDTO
     * @return
     */
    @PostMapping("/queryAllAudioMix")
    BaseResult<QueryAllAudioMixVO> queryAllAudioMix(@RequestBody QueryAllAudioMixDTO queryAllAudioMixDTO);

    /**
     * 查询混音信息
     *
     * @param queryAudioMixDTO
     * @return
     */
    @PostMapping("/queryAudioMix")
    BaseResult<QueryAudioMixResponseVO> queryAudioMix(@RequestBody QueryAudioMixDTO queryAudioMixDTO);

    /**
     * 开始画面合成: 返回画面合成设备分组id、画面合成ID
     *
     * @param startVideoMixDTO
     * @return
     */
    @PostMapping("/startVideoMix")
    BaseResult<StartVideoMixResponseVO> startVideoMix(@RequestBody StartVideoMixDTO startVideoMixDTO);

    /**
     * 停止画面合成
     *
     * @param stopVideoMixDTO
     * @return
     */
    @PostMapping("/stopVideoMix")
    BaseResult<Boolean> stopVideoMix(@RequestBody StopVideoMixDTO stopVideoMixDTO);

    /**
     * 更新画面合成
     *
     * @param updateVideoMixDTO
     * @return
     */
    @PostMapping("/updateVideoMix")
    BaseResult<Boolean> updateVideoMix(@RequestBody UpdateVideoMixDTO updateVideoMixDTO);

    /**
     * 查询所有画面合成:返回mixIDs 合成ID集合
     *
     * @param queryAllVideoMixDTO
     * @return
     */
    @PostMapping("/queryAllVideoMix")
    BaseResult<QueryAllAudioMixVO> queryAllVideoMix(@RequestBody QueryAllVideoMixDTO queryAllVideoMixDTO);

    /**
     * 查询画面合成信息
     *
     * @param queryVideoMixDTO
     * @return
     */
    @PostMapping("/queryVideoMix")
    BaseResult<QueryVideoMixResponseVO> queryVideoMix(@RequestBody QueryVideoMixDTO queryVideoMixDTO);

    /**
     * 合成画面保活
     *
     * @param dto
     * @return
     */
    @PostMapping("/keepVideoMixAlive")
    BaseResult<Boolean> keepVideoMixAlive(@RequestBody VideoMixKeepAliveDTO dto);

    /**
     * 发送透明通道数据
     *
     * @param sendTransDataDTO
     * @return
     */
    @PostMapping("/sendTransData")
    BaseResult<Boolean> sendTransData(@RequestBody SendTransDataDTO sendTransDataDTO);

    /**
     * 查询实时资源URL
     *
     * @param queryRealUrlDTO
     * @return
     */
    @PostMapping("/queryRealUrl")
    BaseResult<QueryRealUrlVO> queryRealUrl(@RequestBody QueryRealUrlDTO queryRealUrlDTO);

    /**
     * 查询历史资源URL
     *
     * @param queryHistoryUrlDTO
     * @return
     */
    @PostMapping("/queryHistoryUrl")
    BaseResult<QueryHistoryUrlVO> queryHistoryUrl(@RequestBody QueryHistoryUrlDTO queryHistoryUrlDTO);

    /**
     * 发送宏指令数据
     *
     * @param sendOrderDataDTO
     * @return
     */
    @PostMapping("/sendOrderData")
    BaseResult<Boolean> sendOrderData(@RequestBody SendOrderDataDTO sendOrderDataDTO);

    /**
     * 开始推送媒体流
     *
     * @param startPushUrlDTO
     * @return
     */
    @PostMapping("/startPushUrl")
    BaseResult<StartPushUrlVO> startPushUrl(@RequestBody StartPushUrlDTO startPushUrlDTO);

    /**
     * 停止推送媒体流
     *
     * @param stopPushUrlDTO
     * @return
     */
    @PostMapping("/stopPushUrl")
    BaseResult<Boolean> stopPushUrl(@RequestBody StopPushUrlDTO stopPushUrlDTO);

    /**
     * 开始拉取媒体流
     *
     * @param startPullUrlDTO
     * @return
     */
    @PostMapping("/startPullUrl")
    BaseResult<StartPullUrlVO> startPullUrl(@RequestBody StartPullUrlDTO startPullUrlDTO);

    /**
     * 停止拉取媒体流
     *
     * @param stopPullUrlDTO
     * @return
     */
    @PostMapping("/stopPullUrl")
    BaseResult<Boolean> stopPullUrl(@RequestBody StopPullUrlDTO stopPullUrlDTO);

    @ApiOperation("获取音频能力集")
    @PostMapping("/getAudioCap")
    BaseResult<GetAudioCapVO> getAudioCap(@RequestBody GetAudioCapDTO getAudioCapDTO);

    @ApiOperation("控制音频功率上报")
    @PostMapping("/ctrlAudioAct")
    BaseResult<Boolean> ctrlAudioAct(@RequestBody CtrlAudioActDTO ctrlAudioActDTO);

    @ApiOperation("设置音频功率上报间隔")
    @PostMapping("/setAudioActInterval")
    BaseResult<Boolean> setAudioActInterval(@RequestBody SetAudioActIntervalDTO setAudioActIntervalDTO);

    @ApiOperation("刻录状态请求")
    @PostMapping("/getBurnState")
    BaseResult<GetBurnStateVO> getBurnState(@RequestBody GetBurnStateDTO getBurnStateDTO);

    @ApiOperation("获取当前语音激励状态 目前只支持SVR2931型号")
    @PostMapping("/getSvrAudioActState")
    BaseResult<GetSvrAudioActStateVo> getSvrAudioActState(@RequestBody GetSvrAudioActStateDTO dto);


}
