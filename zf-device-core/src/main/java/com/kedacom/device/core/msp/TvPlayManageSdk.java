package com.kedacom.device.core.msp;

import com.kedacom.acl.network.data.avIntegration.tvplay.*;
import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.device.core.config.config.DeviceFastJsonConfig;
import com.kedacom.device.core.msp.fallback.TvPlayManageSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:06
 */
@FeignClient(name = "msp",
        contextId = "msp-tvplay",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/tvplay",
        fallbackFactory = TvPlayManageSdkFallbackFactory.class,
        configuration = DeviceFastJsonConfig.class)
public interface TvPlayManageSdk {

    /**
     * 信号源在指定窗口的显示，可批量显示
     *
     * @param request
     * @return
     */
    @PostMapping("batch/start")
    BatchStartResponse batchStart(@RequestBody BatchStartRequest request);

    /**
     * 关闭大屏上正在显示的窗口，可批量关闭
     *
     * @param request
     * @return
     */
    @PostMapping("batch/stop")
    BatchStopResponse batchStop(@RequestBody BatchStopRequest request);

    /**
     * 清空大屏上正在显示的窗口
     *
     * @param request
     * @return
     */
    @PostMapping("clear")
    TvPlayClearResponse clear(@RequestBody TvPlayClearRequest request);

    /**
     * 设置窗口显示风格，支持一画面、四画面切换
     *
     * @param request
     * @return
     */
    @PostMapping("style")
    TvPlayStyleResponse style(@RequestBody TvPlayStyleRequest request);

    /**
     * 大屏任意位置自由开窗，不依赖预案
     *
     * @param request
     * @return
     */
    @PostMapping("open")
    TvPlayOpenResponse open(@RequestBody TvPlayOpenRequest request);

    /**
     * 正在开窗的窗口显示顺序修改
     *
     * @param request
     * @return
     */
    @PostMapping("order")
    TvPlayOrderResponse order(@RequestBody TvPlayOrderRequest request);

    /**
     * 对正在浏览（实时流）的窗口控制暂停浏览
     *
     * @param request
     * @return
     */
    @PostMapping("action")
    TvPlayActionResponse action(@RequestBody TvPlayActionRequest request);

}
