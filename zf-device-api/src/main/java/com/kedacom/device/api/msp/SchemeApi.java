package com.kedacom.device.api.msp;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.avIntegration.response.scheme.SchemeConfigVO;
import com.kedacom.avIntegration.response.scheme.SchemeQueryVO;
import com.kedacom.device.api.msp.fallback.SchemeApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:19
 */
@FeignClient(value = "device-server", contextId = "schemeManageApi", path = "/api/v1/manage/scheme", fallbackFactory = SchemeApiFallbackFactory.class)
public interface SchemeApi {

    /**
     * 预案的画面布局配置
     *
     * @param request
     * @return
     */
    @PostMapping("config")
    BaseResult<SchemeConfigVO> config(@RequestBody SchemeConfigRequest request);

    /**
     * 查询预案布局，窗口位置信息
     *
     * @param request
     * @return
     */
    @PostMapping("query")
    BaseResult<SchemeQueryVO> query(@RequestBody SchemeQueryRequest request);

}
