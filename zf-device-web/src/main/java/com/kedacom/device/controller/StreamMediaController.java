package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.service.StreamMediaService;
import com.kedacom.streamMedia.request.QueryrecRequestDTO;
import com.kedacom.streamMedia.request.StartrecRequestDTO;
import com.kedacom.streamMedia.request.StoprecRequestDTO;
import com.kedacom.streamMedia.response.QueryrecResponseVO;
import com.kedacom.streamMedia.response.StartrecResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @PostMapping("/startrec")
    public BaseResult<StartrecResponseVO> startrec(@RequestBody StartrecRequestDTO startrecRequestDTO){
        return streamMediaService.startrec(startrecRequestDTO);
    }

    @ApiOperation("停止录像")
    @PostMapping("/stoprec")
    public BaseResult stoprec(@RequestBody StoprecRequestDTO stoprecRequestDTO){
        return streamMediaService.stoprec(stoprecRequestDTO);
    }

    @ApiOperation("查询录像记录")
    @PostMapping("/queryrec")
    public BaseResult<QueryrecResponseVO> queryrec(@RequestBody QueryrecRequestDTO queryrecRequestDTO){
        return streamMediaService.queryrec(queryrecRequestDTO);
    }



}
