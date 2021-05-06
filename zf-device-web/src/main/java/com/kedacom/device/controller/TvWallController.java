package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.*;
import com.kedacom.avIntegration.response.*;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.service.TvWallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 10:58
 */
@RestController
@RequestMapping("/api/v1/manage/tvwall")
@Api(value = "大屏管理")
public class TvWallController {

    @Resource
    private TvWallService tvWallService;

    @PostMapping("ls")
    @ApiOperation("获取所有大屏配置")
    public BaseResult<TvWallListResponse> ls(@Valid @RequestBody TvWallListRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return tvWallService.ls(request);
    }

    @PostMapping("layout")
    @ApiOperation("获取大屏布局，不等分模式下使用-获取大屏布局，不等分模式下使用")
    public BaseResult<TvWallLayoutResponse> layout(@Valid @RequestBody TvWallLayoutRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return tvWallService.layout(request);
    }


    @PostMapping("query")
    @ApiOperation("查询大屏通道绑定（虚拟屏）")
    public BaseResult<TvWallQueryPipelineResponse> query(@Valid @RequestBody TvWallQueryPipelineRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return tvWallService.query(request);
    }

    @PostMapping("config")
    @ApiOperation("设置大屏（虚拟屏）")
    public BaseResult<TvWallConfigResponse> config(@Valid @RequestBody TvWallConfigRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return tvWallService.config(request);
    }

    @PostMapping("config/bind")
    @ApiOperation("配置大屏通道绑定（虚拟屏）- 配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式")
    public BaseResult<TvWallPipelineBindResponse> configBind(@Valid @RequestBody TvWallPipelineBindRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return tvWallService.configBind(request);
    }

    @PostMapping("delete")
    @ApiOperation("删除大屏（虚拟屏）- 拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系")
    public BaseResult delete(@Valid @RequestBody TvWallDeleteRequest request, BindingResult br) {
        ValidUtils.paramValid(br);
        return tvWallService.delete(request);
    }

}
