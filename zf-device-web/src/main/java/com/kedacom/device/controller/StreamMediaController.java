package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.service.StreamMediaService;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartAudioMixResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class StreamMediaController {

    @Resource
    private StreamMediaService streamMediaService;

    @ApiOperation("开启录像")
    @PostMapping("/startRec")
    public BaseResult<StartrecResponseVO> startRec(@Valid @RequestBody StartRecRequestDTO startrecRequestDTO, BindingResult br){
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
    public BaseResult<QueryrecResponseVO> queryRec(@Valid @RequestBody QueryRecRequestDTO queryrecRequestDTO, BindingResult br){
        ValidUtils.paramValid(br);
        return streamMediaService.queryRec(queryrecRequestDTO);
    }

    @ApiOperation("开启音频混音")
    @PostMapping("/startAudioMix")
    public BaseResult<StartAudioMixResponseVO> startAudioMix(@Valid @RequestBody StartAudioMixRequestDTO startAudioMixRequestDTO, BindingResult br){
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



}
