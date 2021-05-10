package com.kedacom.device.controller;

import com.kedacom.avIntegration.request.tvwall.*;
import com.kedacom.avIntegration.response.tvwall.*;
import com.kedacom.device.core.service.TvWallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:06
 */
@RestController
@RequestMapping("/api/v1/manage/tvwall")
@Api(value = "大屏管理")
@Slf4j
public class TvWallController {

    @Resource
    private TvWallService tvWallService;

    /**
     * 获取所有大屏配置
     *
     * @param request
     * @return
     */
    @PostMapping("ls")
    @ApiOperation("获取所有大屏配置")
    public TvWallListResponse ls(@RequestBody TvWallListRequest request) {

    }

    /**
     * 获取大屏布局，不等分模式下使用
     *
     * @param request
     * @return
     */
    @PostMapping("layout")
    @ApiOperation("获取大屏布局，不等分模式下使用")
    public TvWallLayoutResponse layout(@RequestBody TvWallLayoutRequest request) {

    }

    /**
     * 主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）
     *
     * @param request
     * @return
     */
    @PostMapping("query")
    @ApiOperation("主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）")
    public TvWallQueryPipelineResponse query(@RequestBody TvWallQueryPipelineRequest request) {

    }

    /**
     * 配置虚拟屏
     *
     * @param request
     * @return
     */
    @PostMapping("config")
    @ApiOperation("配置虚拟屏")
    public TvWallConfigResponse config(@RequestBody TvWallConfigRequest request) {

    }

    /**
     * 配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式
     *
     * @param request
     * @return
     */
    @PostMapping("config/bind")
    @ApiOperation("配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式")
    public TvWallPipelineBindResponse configBind(@RequestBody TvWallPipelineBindRequest request) {

    }

    /**
     * 拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系
     *
     * @param request
     * @return
     */
    @PostMapping("delete")
    @ApiOperation("拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系")
    public TvWallDeleteResponse delete(@RequestBody TvWallDeleteRequest request) {

    }

}
