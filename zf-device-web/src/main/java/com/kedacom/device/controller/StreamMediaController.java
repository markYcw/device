package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:06
 */
@RestController
@RequestMapping("device/streamMedia")
@Api(value = "流媒体", tags = "流媒体")
@Slf4j
public class StreamMediaController {

    @Autowired
    private StreamMediaService streamMediaService;

    @ApiOperation("开启录像")
    @PostMapping("/startRec")
    public BaseResult<StartRecResponseVO> startRec(@Valid @RequestBody StartRecDTO startrecDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        StartRecResponseVO startRec = streamMediaService.startRec(startrecDTO);
        return BaseResult.succeed(startRec);
    }

    @ApiOperation("停止录像")
    @PostMapping("/stopRec")
    public BaseResult<Boolean> stopRec(@Valid @RequestBody StopRecDTO stoprecDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean stopRec = streamMediaService.stopRec(stoprecDTO);
        return BaseResult.succeed("停止成功", stopRec);
    }

    @ApiOperation("查询录像记录")
    @PostMapping("/queryRec")
    public BaseResult<QueryRecResponseVO> queryRec(@Valid @RequestBody QueryRecDTO queryrecDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryRecResponseVO queryRec = streamMediaService.queryRec(queryrecDTO);
        return BaseResult.succeed(queryRec);
    }

    @ApiOperation("录像任务保活")
    @PostMapping("/recKeepAlive")
    public BaseResult<Boolean> recKeepAlive(@Valid @RequestBody RecKeepAliveDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean alive = streamMediaService.recKeepAlive(dto);
        return BaseResult.succeed("保活成功", alive);
    }

    @ApiOperation("开启音频混音:返回混音设备分组id、混音ID")
    @PostMapping("/startAudioMix")
    public BaseResult<StartAudioMixResponseVO> startAudioMix(@Valid @RequestBody StartAudioMixDTO startAudioMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        StartAudioMixResponseVO startAudioMixResponseVO = streamMediaService.startAudioMix(startAudioMixDTO);
        return BaseResult.succeed(startAudioMixResponseVO);
    }

    @ApiOperation("停止音频混音")
    @PostMapping("/stopAudioMix")
    public BaseResult<Boolean> stopAudioMix(@Valid @RequestBody StopAudioMixDTO stopAudioMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean stopAudioMix = streamMediaService.stopAudioMix(stopAudioMixDTO);
        return BaseResult.succeed("停止成功", stopAudioMix);
    }

    @ApiOperation("更新音频混音")
    @PostMapping("/updateAudioMix")
    public BaseResult<Boolean> updateAudioMix(@Valid @RequestBody UpdateAudioMixDTO updateAudioMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean updateAudioMix = streamMediaService.updateAudioMix(updateAudioMixDTO);
        return BaseResult.succeed(updateAudioMix);
    }

    @ApiOperation("查询所有混音 返回混音ID集合")
    @PostMapping("/queryAllAudioMix")
    public BaseResult<QueryAllAudioMixVO> queryAllAudioMix(@Valid @RequestBody QueryAllAudioMixDTO queryAllAudioMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryAllAudioMixVO queryAllAudioMix = streamMediaService.queryAllAudioMix(queryAllAudioMixDTO);
        return BaseResult.succeed(queryAllAudioMix);
    }

    @ApiOperation("查询混音信息")
    @PostMapping("/queryAudioMix")
    public BaseResult<QueryAudioMixResponseVO> queryAudioMix(@Valid @RequestBody QueryAudioMixDTO queryAudioMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryAudioMixResponseVO queryAudioMixResponseVO = streamMediaService.queryAudioMix(queryAudioMixDTO);
        return BaseResult.succeed(queryAudioMixResponseVO);
    }

    @ApiOperation("开始画面合成: 返回画面合成设备分组id、画面合成ID")
    @PostMapping("/startVideoMix")
    public BaseResult<StartVideoMixResponseVO> startVideoMix(@Valid @RequestBody StartVideoMixDTO startVideoMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        StartVideoMixResponseVO startVideoMixResponseVO = streamMediaService.startVideoMix(startVideoMixDTO);
        return BaseResult.succeed(startVideoMixResponseVO);
    }

    @ApiOperation("停止画面合成")
    @PostMapping("/stopVideoMix")
    public BaseResult<Boolean> stopVideoMix(@Valid @RequestBody StopVideoMixDTO stopVideoMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean stopVideoMix = streamMediaService.stopVideoMix(stopVideoMixDTO);
        return BaseResult.succeed("停止成功", stopVideoMix);
    }

    @ApiOperation("更新画面合成")
    @PostMapping("/updateVideoMix")
    public BaseResult<Boolean> updateVideoMix(@Valid @RequestBody UpdateVideoMixDTO updateVideoMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean updateVideoMix = streamMediaService.updateVideoMix(updateVideoMixDTO);
        return BaseResult.succeed(updateVideoMix);
    }

    @ApiOperation("查询所有画面合成:返回mixIDs 合成ID集合")
    @PostMapping("/queryAllVideoMix")
    public BaseResult<QueryAllAudioMixVO> queryAllVideoMix(@Valid @RequestBody QueryAllVideoMixDTO queryAllVideoMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryAllAudioMixVO queryAllVideoMix = streamMediaService.queryAllVideoMix(queryAllVideoMixDTO);
        return BaseResult.succeed(queryAllVideoMix);
    }

    @ApiOperation("查询画面合成信息")
    @PostMapping("/queryVideoMix")
    public BaseResult<QueryVideoMixResponseVO> queryVideoMix(@Valid @RequestBody QueryVideoMixDTO queryVideoMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryVideoMixResponseVO queryVideoMixResponseVO = streamMediaService.queryVideoMix(queryVideoMixDTO);
        return BaseResult.succeed(queryVideoMixResponseVO);
    }

    @ApiOperation("合成画面保活")
    @PostMapping("/keepVideoMixAlive")
    public BaseResult<Boolean> keepVideoMixAlive(@Valid @RequestBody VideoMixKeepAliveDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean alive = streamMediaService.keepVideoMixAlive(dto);
        return BaseResult.succeed("保活成功", alive);
    }

    @ApiOperation("发送透明通道数据")
    @PostMapping("/sendTransData")
    public BaseResult<Boolean> sendTransData(@Valid @RequestBody SendTransDataDTO sendTransDataDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean aBoolean = streamMediaService.sendTransData(sendTransDataDTO);
        return BaseResult.succeed("发送成功", aBoolean);
    }

    @ApiOperation("查询实时资源URL")
    @PostMapping("/queryRealUrl")
    public BaseResult<QueryRealUrlVO> queryRealUrl(@Valid @RequestBody QueryRealUrlDTO queryRealUrlDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryRealUrlVO queryRealUrlVO = streamMediaService.queryRealUrl(queryRealUrlDTO);
        return BaseResult.succeed("查询成功", queryRealUrlVO);
    }

    @ApiOperation("查询历史资源URL")
    @PostMapping("/queryHistoryUrl")
    public BaseResult<QueryHistoryUrlVO> queryHistoryUrl(@Valid @RequestBody QueryHistoryUrlDTO queryHistoryUrlDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryHistoryUrlVO queryHistoryUrlVO = streamMediaService.queryHistoryUrl(queryHistoryUrlDTO);
        return BaseResult.succeed("查询成功", queryHistoryUrlVO);
    }

    @ApiOperation("发送宏指令数据")
    @PostMapping("/sendOrderData")
    public BaseResult<Boolean> sendOrderData(@Valid @RequestBody SendOrderDataDTO sendOrderDataDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean aBoolean = streamMediaService.sendOrderData(sendOrderDataDTO);
        return BaseResult.succeed("发送成功", aBoolean);
    }

    @ApiOperation("开始推送媒体流")
    @PostMapping("/startPushUrl")
    public BaseResult<StartPushUrlVO> startPushUrl(@Valid @RequestBody StartPushUrlDTO startPushUrlDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        StartPushUrlVO startPushUrlVO = streamMediaService.startPushUrl(startPushUrlDTO);
        return BaseResult.succeed("推送成功", startPushUrlVO);
    }

    @ApiOperation("停止推送媒体流")
    @PostMapping("/stopPushUrl")
    public BaseResult<Boolean> stopPushUrl(@Valid @RequestBody StopPushUrlDTO stopPushUrlDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean aBoolean = streamMediaService.stopPushUrl(stopPushUrlDTO);
        return BaseResult.succeed("停止成功", aBoolean);
    }

    @ApiOperation("开始拉取媒体流")
    @PostMapping("/startPullUrl")
    public BaseResult<StartPullUrlVO> startPullUrl(@Valid @RequestBody StartPullUrlDTO startPullUrlDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        StartPullUrlVO startPullUrlVO = streamMediaService.startPullUrl(startPullUrlDTO);
        return BaseResult.succeed("拉取成功", startPullUrlVO);
    }

    @ApiOperation("停止拉取媒体流")
    @PostMapping("/stopPullUrl")
    public BaseResult<Boolean> stopPullUrl(@Valid @RequestBody StopPullUrlDTO stopPullUrlDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean aBoolean = streamMediaService.stopPullUrl(stopPullUrlDTO);
        return BaseResult.succeed("停止成功", aBoolean);
    }
}
