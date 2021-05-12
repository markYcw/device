package com.kedacom.device.core.msp;

import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.avIntegration.response.scheme.SchemeConfigResponse;
import com.kedacom.avIntegration.response.scheme.SchemeQueryResponse;
import com.kedacom.device.core.msp.fallback.SchemeManageSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@FeignClient(name = "msp",
        contextId = "msp-scheme",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/scheme",
        fallbackFactory = SchemeManageSdkFallbackFactory.class)
public interface SchemeManageSdk {

    /**
     * 预案的画面布局配置
     * @param request
     * @return
     */
    @PostMapping("config")
    SchemeConfigResponse config(@RequestBody SchemeConfigRequest request);

    /**
     * 查询预案布局，窗口位置信息
     * @param request
     * @return
     */
    @PostMapping("query")
    SchemeQueryResponse query(@RequestBody SchemeQueryRequest request);

}
