package com.kedacom.device.controller;

import cn.hutool.core.util.StrUtil;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.utils.ValidUtils;
import com.kedacom.device.core.service.StreamingMediaService;
import com.kedacom.streamingMedia.request.StreamingMediaQueryDto;
import com.kedacom.streamingMedia.request.StreamingMediaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
@RestController
@RequestMapping("/streaming")
@Api(value = "流媒体服务器信息配置", tags = "流媒体服务器信息配置")
public class StreamingMediaController {

    @Resource
    StreamingMediaService streamingMediaService;

    @ApiOperation("分页列表")
    @PostMapping("/querySmList")
    public BaseResult<BasePage<StreamingMediaVo>> querySmList(@RequestBody StreamingMediaQueryDto streamingMediaQuery) {

        BasePage<StreamingMediaVo> page = streamingMediaService.querySmList(streamingMediaQuery);

        return BaseResult.succeed("查询流媒体服务器列表信息成功", page);
    }

    @ApiOperation("根据id查询信息")
    @PostMapping("/querySm")
    public BaseResult<StreamingMediaVo> querySm(@RequestParam("id") String id) {

        return BaseResult.succeed("todo msg", streamingMediaService.querySm(id));
    }

    @ApiOperation("查询所有流媒体")
    @PostMapping("/smList")
    public BaseResult<List<StreamingMediaVo>> smList() {

        return BaseResult.succeed("查询成功", streamingMediaService.smList());
    }

    @ApiOperation("保存")
    @PostMapping("/saveSm")
    public BaseResult<Boolean> saveSm(@Valid @RequestBody StreamingMediaVo streamingMediaVo, BindingResult bindingResult) {

        ValidUtils.paramValid(bindingResult);
        String repeat = streamingMediaService.isRepeat(streamingMediaVo);
        if(StrUtil.isNotBlank(repeat)){
            return BaseResult.failed(repeat);
        }
        if (streamingMediaService.saveSm(streamingMediaVo)) {
            return BaseResult.succeed("添加流媒体服务配置成功", true);
        }

        return BaseResult.failed("添加流媒体服务配置失败");
    }

    @ApiOperation("修改")
    @PostMapping("/updateSm")
    public BaseResult<Boolean> updateSm(@Valid @RequestBody StreamingMediaVo streamingMediaVo, BindingResult bindingResult) {

        ValidUtils.paramValid(bindingResult);
        String repeat = streamingMediaService.isRepeat(streamingMediaVo);
        if(StrUtil.isNotBlank(repeat)){
            return BaseResult.failed(repeat);
        }
        if (streamingMediaService.updateSm(streamingMediaVo)) {
            return BaseResult.succeed("修改流媒体服务配置成功", true);
        }

        return BaseResult.failed("修改流媒体服务配置失败");
    }

}
