package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.cu.vo.*;
import com.kedacom.device.core.service.LiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@RestController
@RequestMapping("/live")
@Api(tags = "发布直播/点播/RTSP源")
public class LiveController {

    @Resource
    LiveService liveService;

    @ApiOperation("发布直播")
    @PostMapping("/liveStream")
    public BaseResult<LiveStreamResponseVo> liveStream(@RequestBody LiveStreamRequestVo liveStreamRequestVo) {

        return liveService.liveStream(liveStreamRequestVo);
    }

    @ApiOperation("发布点播")
    @PostMapping("/vodStream")
    public BaseResult<VodStreamResponseVo> vodStream(@RequestBody VodStreamRequestVo vodStreamRequestVo) {

        return liveService.vodStream(vodStreamRequestVo);
    }

    @ApiOperation("发布rtsp源")
    @PostMapping("/stream")
    public BaseResult<StreamResponseVo> stream(@RequestBody StreamRequestVo streamRequestVo) {

        return liveService.stream(streamRequestVo);
    }

}
