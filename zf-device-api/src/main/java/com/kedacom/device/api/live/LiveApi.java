package com.kedacom.device.api.live;

import com.kedacom.BaseResult;
import com.kedacom.cu.vo.*;
import com.kedacom.device.api.live.fallback.LiveApiFallBackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@FeignClient(value = "device-server", contextId = "LiveApi", path = "/api-device/live", fallbackFactory = LiveApiFallBackFactory.class)
public interface LiveApi {

    @ApiOperation("发布直播")
    @PostMapping("/liveStream")
    BaseResult<LiveStreamResponseVo> liveStream(LiveStreamRequestVo liveStreamRequestVo);

    @ApiOperation("发布点播")
    @PostMapping("/vodStream")
    BaseResult<VodStreamResponseVo> vodStream(VodStreamRequestVo vodStreamRequestVo);

    @ApiOperation("发布rtsp源")
    @PostMapping("/stream")
    BaseResult<StreamResponseVo> stream(StreamRequestVo streamRequestVo);

}
