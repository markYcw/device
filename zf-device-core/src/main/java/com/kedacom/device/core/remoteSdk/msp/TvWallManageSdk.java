package com.kedacom.device.core.remoteSdk.msp;

import com.kedacom.acl.network.data.avIntegration.tvwall.*;
import com.kedacom.msp.request.tvwall.*;
import com.kedacom.device.core.config.RemoteFeignConfig;
import com.kedacom.device.core.remoteSdk.msp.fallback.TvWallManageSdkFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:57
 */
@FeignClient(name = "msp",
        contextId = "msp-tvwall",
        url = "${zf.msp.server_addr}",
        path = "/api/v1/manage/tvwall",
        fallbackFactory = TvWallManageSdkFallbackFactory.class,
        configuration = RemoteFeignConfig.class)
public interface TvWallManageSdk {

    /**
     * 获取所有大屏配置
     *
     * @param request
     * @return
     */
    @PostMapping("ls")
    TvWallListResponse ls(@RequestBody TvWallListRequest request);

    /**
     * 获取大屏布局，不等分模式下使用
     *
     * @param request
     * @return
     */
    @PostMapping("layout")
    TvWallLayoutResponse layout(@RequestBody TvWallLayoutRequest request);

    /**
     * 主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）
     *
     * @param request
     * @return
     */
    @PostMapping("query")
    TvWallQueryPipelineResponse query(@RequestBody TvWallQueryPipelineRequest request);

    /**
     * 配置虚拟屏
     *
     * @param request
     * @return
     */
    @PostMapping("config")
    TvWallConfigResponse config(@RequestBody TvWallConfigRequest request);

    /**
     * 配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式
     *
     * @param request
     * @return
     */
    @PostMapping("config/bind")
    TvWallPipelineBindResponse configBind(@RequestBody TvWallPipelineBindRequest request);

    /**
     * 拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系
     *
     * @param request
     * @return
     */
    @PostMapping("delete")
    TvWallDeleteResponse delete(@RequestBody TvWallDeleteRequest request);

}
