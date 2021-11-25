package com.kedacom.device.api.live;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.live.fallback.StreamingMediaApiFallBackFactory;
import com.kedacom.streamingMedia.request.StreamingMediaQueryDto;
import com.kedacom.streamingMedia.request.StreamingMediaVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
@FeignClient(value = "device-server", contextId = "StreamingMediaApi", path = "/api-device/streaming", fallbackFactory = StreamingMediaApiFallBackFactory.class)
public interface StreamingMediaApi {

    @ApiOperation("分页列表")
    @PostMapping("/querySmList")
    BaseResult<BasePage<StreamingMediaVo>> querySmList(@RequestBody StreamingMediaQueryDto streamingMediaQuery);

    @ApiOperation("根据id查询信息")
    @PostMapping("/querySm")
    BaseResult<StreamingMediaVo> querySm(@RequestParam("id") String id);

    @ApiOperation("查询所有流媒体")
    @PostMapping("/smList")
    BaseResult<List<StreamingMediaVo>> smList();

    @ApiOperation("保存")
    @PostMapping("/saveSm")
    BaseResult<Boolean> saveSm(@RequestBody StreamingMediaVo streamingMediaVo);

    @ApiOperation("修改")
    @PostMapping("/updateSm")
    BaseResult<Boolean> updateSm(@RequestBody StreamingMediaVo streamingMediaVo);

}
