package com.kedacom.device.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kedacom.BaseResult;
import com.kedacom.device.core.utils.ValidUtils;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:06
 */
@RestController
@RequestMapping("device/streamMedia")
@Api(value = "流媒体")
@Slf4j
public class StreamMediaController {

    @Resource
    private StreamMediaService streamMediaService;

    @ApiOperation("开启录像")
    @PostMapping("/startRec")
    public BaseResult<StartRecResponseVO> startRec(@Valid @RequestBody StartRecRequestDTO startrecRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.startRec(startrecRequestDTO);
    }

    @ApiOperation("停止录像")
    @PostMapping("/stopRec")
    public BaseResult<Boolean> stopRec(@Valid @RequestBody StopRecRequestDTO stoprecRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.stopRec(stoprecRequestDTO);
    }

    @ApiOperation("查询录像记录")
    @PostMapping("/queryRec")
    public BaseResult<QueryRecResponseVO> queryRec(@Valid @RequestBody QueryRecRequestDTO queryrecRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.queryRec(queryrecRequestDTO);
    }

    @ApiOperation("开启音频混音")
    @PostMapping("/startAudioMix")
    public BaseResult<String> startAudioMix(@Valid @RequestBody StartAudioMixRequestDTO startAudioMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.startAudioMix(startAudioMixRequestDTO);
    }

    @ApiOperation("停止音频混音")
    @PostMapping("/stopAudioMix")
    public BaseResult<Boolean> stopAudioMix(@Valid @RequestBody StopAudioMixRequestDTO stopAudioMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.stopAudioMix(stopAudioMixRequestDTO);
    }

    @ApiOperation("更新音频混音")
    @PostMapping("/updateAudioMix")
    public BaseResult<Boolean> updateAudioMix(@Valid @RequestBody UpdateAudioMixRequestDTO updateAudioMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.updateAudioMix(updateAudioMixRequestDTO);
    }

    @ApiOperation("查询所有混音")
    @PostMapping("/queryAllAudioMix")
    public BaseResult<List<String>> queryAllAudioMix(@Valid @RequestBody QueryAllAudioMixRequestDTO queryAllAudioMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.queryAllAudioMix(queryAllAudioMixRequestDTO);
    }

    @ApiOperation("查询混音信息")
    @PostMapping("/queryAudioMix")
    public BaseResult<QueryAudioMixResponseVO> queryAudioMix(@Valid @RequestBody QueryAudioMixRequestDTO queryAudioMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.queryAudioMix(queryAudioMixRequestDTO);
    }

    @ApiOperation("开始画面合成")
    @PostMapping("/startVideoMix")
    public BaseResult<String> startVideoMix(@Valid @RequestBody StartVideoMixRequestDTO startVideoMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.startVideoMix(startVideoMixRequestDTO);
    }

    @ApiOperation("停止画面合成")
    @PostMapping("/stopVideoMix")
    public BaseResult<Boolean> stopVideoMix(@Valid @RequestBody StopVideoMixRequestDTO stopVideoMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.stopVideoMix(stopVideoMixRequestDTO);
    }

    @ApiOperation("更新画面合成")
    @PostMapping("/updateVideoMix")
    public BaseResult<Boolean> updateVideoMix(@Valid @RequestBody UpdateVideoMixRequestDTO updateVideoMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.updateVideoMix(updateVideoMixRequestDTO);
    }

    @ApiOperation("查询所有画面合成")
    @PostMapping("/queryAllVideoMix")
    public BaseResult<List<String>> queryAllVideoMix(@RequestParam("unitId") String unitId){
        if (StringUtils.isBlank(unitId)){
            log.error("统一平台id为空");
            return BaseResult.failed("统一平台id为空");
        }
        return streamMediaService.queryAllVideoMix(unitId);
    }

    @ApiOperation("查询画面合成信息")
    @PostMapping("/queryVideoMix")
    public BaseResult<QueryVideoMixResponseVO> queryVideoMix(@Valid @RequestBody QueryVideoMixRequestDTO queryVideoMixRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.queryVideoMix(queryVideoMixRequestDTO);
    }

}
