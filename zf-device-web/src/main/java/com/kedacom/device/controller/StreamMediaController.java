package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.StreamMediaService;
import com.kedacom.streamMedia.request.*;
import com.kedacom.streamMedia.response.QueryAudioMixResponseVO;
import com.kedacom.streamMedia.response.QueryRecResponseVO;
import com.kedacom.streamMedia.response.QueryVideoMixResponseVO;
import com.kedacom.streamMedia.response.StartRecResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:06
 */
@RestController
@RequestMapping("device/streamMedia")
@Api(value = "流媒体",tags = "流媒体")
@Slf4j
public class StreamMediaController {

    @Autowired
    private StreamMediaService streamMediaService;

    @ApiOperation("开启录像")
    @PostMapping("/startRec")
    public BaseResult<StartRecResponseVO> startRec(@Valid @RequestBody StartRecRequest startrecRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        StartRecResponseVO startRec = streamMediaService.startRec(startrecRequest);
        return BaseResult.succeed(startRec);
    }

    @ApiOperation("停止录像")
    @PostMapping("/stopRec")
    public BaseResult<Boolean> stopRec(@Valid @RequestBody StopRecRequest stoprecRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean stopRec = streamMediaService.stopRec(stoprecRequest);
        return BaseResult.succeed(stopRec);
    }

    @ApiOperation("查询录像记录")
    @PostMapping("/queryRec")
    public BaseResult<QueryRecResponseVO> queryRec(@Valid @RequestBody QueryRecRequest queryrecRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryRecResponseVO queryRec = streamMediaService.queryRec(queryrecRequest);
        return BaseResult.succeed(queryRec);
    }

    /**
     * 混音ID，20位字符
     *
     * @param startAudioMixRequest
     * @param br
     * @return mixID 混音ID，20位字符
     */
    @ApiOperation("开启音频混音: 返回mixID 混音ID，20位字符")
    @PostMapping("/startAudioMix")
    public BaseResult<String> startAudioMix(@Valid @RequestBody StartAudioMixRequest startAudioMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        String mixID = streamMediaService.startAudioMix(startAudioMixRequest);
        return BaseResult.succeed(mixID);
    }

    @ApiOperation("停止音频混音")
    @PostMapping("/stopAudioMix")
    public BaseResult<Boolean> stopAudioMix(@Valid @RequestBody StopAudioMixRequest stopAudioMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean stopAudioMix = streamMediaService.stopAudioMix(stopAudioMixRequest);
        return BaseResult.succeed(stopAudioMix);
    }

    @ApiOperation("更新音频混音")
    @PostMapping("/updateAudioMix")
    public BaseResult<Boolean> updateAudioMix(@Valid @RequestBody UpdateAudioMixRequest updateAudioMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean updateAudioMix = streamMediaService.updateAudioMix(updateAudioMixRequest);
        return BaseResult.succeed(updateAudioMix);
    }

    /**
     * @param queryAllAudioMixRequest
     * @param br
     * @return 混音ID集合
     */
    @ApiOperation("查询所有混音 返回混音ID集合")
    @PostMapping("/queryAllAudioMix")
    public BaseResult<List<String>> queryAllAudioMix(@Valid @RequestBody QueryAllAudioMixRequest queryAllAudioMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        List<String> queryAllAudioMix = streamMediaService.queryAllAudioMix(queryAllAudioMixRequest);
        return BaseResult.succeed(queryAllAudioMix);
    }

    @ApiOperation("查询混音信息")
    @PostMapping("/queryAudioMix")
    public BaseResult<QueryAudioMixResponseVO> queryAudioMix(@Valid @RequestBody QueryAudioMixRequest queryAudioMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryAudioMixResponseVO queryAudioMixResponseVO = streamMediaService.queryAudioMix(queryAudioMixRequest);
        return BaseResult.succeed(queryAudioMixResponseVO);
    }

    /**
     * @param startVideoMixRequest
     * @param br
     * @return mixID 合成ID，20位字符
     */
    @ApiOperation("开始画面合成: 返回mixID 合成ID，20位字符")
    @PostMapping("/startVideoMix")
    public BaseResult<String> startVideoMix(@Valid @RequestBody StartVideoMixRequest startVideoMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        String mixID = streamMediaService.startVideoMix(startVideoMixRequest);
        return BaseResult.succeed(mixID);
    }

    @ApiOperation("停止画面合成")
    @PostMapping("/stopVideoMix")
    public BaseResult<Boolean> stopVideoMix(@Valid @RequestBody StopVideoMixRequest stopVideoMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean stopVideoMix = streamMediaService.stopVideoMix(stopVideoMixRequest);
        return BaseResult.succeed(stopVideoMix);
    }

    @ApiOperation("更新画面合成")
    @PostMapping("/updateVideoMix")
    public BaseResult<Boolean> updateVideoMix(@Valid @RequestBody UpdateVideoMixRequest updateVideoMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        Boolean updateVideoMix = streamMediaService.updateVideoMix(updateVideoMixRequest);
        return BaseResult.succeed(updateVideoMix);
    }

    /**
     * @param unitId
     * @return mixIDs 合成ID集合
     */
    @ApiOperation("查询所有画面合成:返回mixIDs 合成ID集合")
    @PostMapping("/queryAllVideoMix")
    public BaseResult<List<String>> queryAllVideoMix(@RequestParam("unitId") String unitId) {
        if (StringUtils.isBlank(unitId)) {
            log.error("统一平台id为空");
            return BaseResult.failed("统一平台id为空");
        }
        List<String> mixIDs = streamMediaService.queryAllVideoMix(unitId);
        return BaseResult.succeed(mixIDs);
    }

    @ApiOperation("查询画面合成信息")
    @PostMapping("/queryVideoMix")
    public BaseResult<QueryVideoMixResponseVO> queryVideoMix(@Valid @RequestBody QueryVideoMixRequest queryVideoMixRequest, BindingResult br) {
        ValidUtils.paramValid(br);

        QueryVideoMixResponseVO queryVideoMixResponseVO = streamMediaService.queryVideoMix(queryVideoMixRequest);
        return BaseResult.succeed(queryVideoMixResponseVO);
    }

}
