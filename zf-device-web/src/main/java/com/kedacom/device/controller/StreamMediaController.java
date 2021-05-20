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
import java.util.List;

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
        return BaseResult.succeed(stopRec);
    }

    @ApiOperation("查询录像记录")
    @PostMapping("/queryRec")
    public BaseResult<QueryRecResponseVO> queryRec(@Valid @RequestBody QueryRecDTO queryrecDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryRecResponseVO queryRec = streamMediaService.queryRec(queryrecDTO);
        return BaseResult.succeed(queryRec);
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
        return BaseResult.succeed(stopAudioMix);
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
    public BaseResult<List<String>> queryAllAudioMix(@Valid @RequestBody QueryAllAudioMixDTO queryAllAudioMixDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        List<String> queryAllAudioMix = streamMediaService.queryAllAudioMix(queryAllAudioMixDTO);
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
        return BaseResult.succeed(stopVideoMix);
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

    @ApiOperation("发送透明通道数据")
    @PostMapping("/sendTransData")
    public BaseResult<Boolean> sendTransData(@Valid @RequestBody SendTransDataDTO sendTransDataDTO, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean aBoolean = streamMediaService.sendTransData(sendTransDataDTO);
        return BaseResult.succeed("发送成功", aBoolean);
    }


}
