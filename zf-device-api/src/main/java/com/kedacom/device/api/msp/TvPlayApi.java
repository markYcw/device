package com.kedacom.device.api.msp;


import com.kedacom.BaseResult;
import com.kedacom.msp.request.tvplay.*;
import com.kedacom.msp.response.tvplay.TvPlayOpenVO;
import com.kedacom.device.api.msp.fallback.TvPlayApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:52
 */
@FeignClient(value = "device-server", contextId = "tvPlayApi", path = "/api-device/api/v1/manage/tvplay", fallbackFactory = TvPlayApiFallbackFactory.class)
public interface TvPlayApi {

    /**
     * 信号源在指定窗口的显示，可批量显示
     *
     * @param request
     * @return
     */
    @PostMapping("batch/start")
    BaseResult batchStart(@RequestBody BatchStartRequest request);

    /**
     * 关闭大屏上正在显示的窗口，可批量关闭
     *
     * @param request
     * @return
     */
    @PostMapping("batch/stop")
    BaseResult batchStop(@RequestBody BatchStopRequest request);

    /**
     * 清空大屏上正在显示的窗口
     *
     * @param request
     * @return
     */
    @PostMapping("clear")
    BaseResult clear(@RequestBody TvPlayClearRequest request);

    /**
     * 设置窗口显示风格，支持一画面、四画面切换
     *
     * @param request
     * @return
     */
    @PostMapping("style")
    BaseResult style(@RequestBody TvPlayStyleRequest request);

    /**
     * 大屏任意位置自由开窗，不依赖预案
     *
     * @param request
     * @return
     */
    @PostMapping("open")
    BaseResult<TvPlayOpenVO> open(@RequestBody TvPlayOpenRequest request);

    /**
     * 正在开窗的窗口显示顺序修改
     *
     * @param request
     * @return
     */
    @PostMapping("order")
    BaseResult order(@RequestBody TvPlayOrderRequest request);

    /**
     * 对正在浏览（实时流）的窗口控制暂停浏览
     *
     * @param request
     * @return
     */
    @PostMapping("action")
    BaseResult action(@RequestBody TvPlayActionRequest request);

}
