package com.kedacom.device.api.msp;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.decoder.OsdConfigRequest;
import com.kedacom.avIntegration.request.decoder.OsdDeleteRequest;
import com.kedacom.avIntegration.request.decoder.StyleConfigRequest;
import com.kedacom.avIntegration.request.decoder.StyleQueryRequest;
import com.kedacom.avIntegration.response.decoder.StyleQueryVO;
import com.kedacom.device.api.msp.fallback.DecoderApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 10:18
 */
@FeignClient(value = "device-server", contextId = "decoderApi", path = "/api-device/api/v1/manage/decoder", fallbackFactory = DecoderApiFallbackFactory.class)
public interface DecoderApi {

    /**
     * 设置解码器的OSD
     *
     * @param request
     * @return
     */
    @PostMapping("osd/config")
    BaseResult osdConfig(@RequestBody OsdConfigRequest request);

    /**
     * 取消解码通道的字幕显示
     *
     * @param request
     * @return
     */
    @PostMapping("osd/delete")
    BaseResult osdDelete(@RequestBody OsdDeleteRequest request);

    /**
     * 查询解码通道的画面风格及最大解码能力
     *
     * @param request
     * @return
     */
    @PostMapping("style/query")
    BaseResult<StyleQueryVO> styleQuery(@RequestBody StyleQueryRequest request);

    /**
     * 设置解码通道的画面风格
     *
     * @param request
     * @return
     */
    @PostMapping("style/config")
    BaseResult styleConfig(@RequestBody StyleConfigRequest request);


}
