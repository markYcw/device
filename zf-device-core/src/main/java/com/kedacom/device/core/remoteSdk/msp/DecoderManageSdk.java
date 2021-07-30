package com.kedacom.device.core.remoteSdk.msp;


import com.kedacom.acl.network.data.avIntegration.decoder.OsdConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdDeleteResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleQueryResponse;
import com.kedacom.msp.request.decoder.OsdConfigRequest;
import com.kedacom.msp.request.decoder.OsdDeleteRequest;
import com.kedacom.msp.request.decoder.StyleConfigRequest;
import com.kedacom.msp.request.decoder.StyleQueryRequest;
import com.kedacom.device.core.config.RemoteFeignConfig;
import com.kedacom.device.core.remoteSdk.msp.fallback.DecoderManageSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 解码器通过管理相关接口
 *
 * @Auther: hxj
 * @Date: 2021/5/6 19:20
 */
@FeignClient(name = "msp",
        contextId = "msp-decoder",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/decoder",
        fallbackFactory = DecoderManageSdkFallbackFactory.class,
        configuration = RemoteFeignConfig.class)
public interface DecoderManageSdk {

    /**
     * 设置解码器的OSD
     *
     * @param request
     * @return
     */
    @PostMapping("osd/config")
    OsdConfigResponse osdConfig(@RequestBody OsdConfigRequest request);

    /**
     * 取消解码通道的字幕显示
     *
     * @param request
     * @return
     */
    @PostMapping("osd/delete")
    OsdDeleteResponse osdDelete(@RequestBody OsdDeleteRequest request);

    /**
     * 查询解码通道的画面风格及最大解码能力
     *
     * @param request
     * @return
     */
    @PostMapping("style/query")
    StyleQueryResponse styleQuery(@RequestBody StyleQueryRequest request);

    /**
     * 设置解码通道的画面风格
     *
     * @param request
     * @return
     */
    @PostMapping("style/config")
    StyleConfigResponse styleConfig(@RequestBody StyleConfigRequest request);

}
