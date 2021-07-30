package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.acl.network.data.avIntegration.tvwall.*;
import com.kedacom.msp.request.tvwall.*;
import com.kedacom.msp.response.tvwall.*;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.convert.TvWallConvert;
import com.kedacom.device.core.service.TvWallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:06
 */
@RestController
@RequestMapping("/api/v1/manage/tvwall")
@Api(value = "大屏管理",tags = "大屏管理")
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
    public BaseResult<TvWallListVO> ls(@Valid @RequestBody TvWallListRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        TvWallListResponse response = tvWallService.ls(request);
        TvWallListVO tvWallListVO = TvWallConvert.INSTANCE.listResponseToListVO(response);
        return BaseResult.succeed(tvWallListVO);

    }

    /**
     * 获取大屏布局，不等分模式下使用
     *
     * @param request
     * @return
     */
    @PostMapping("layout")
    @ApiOperation("获取大屏布局，不等分模式下使用")
    public BaseResult<TvWallLayoutVO> layout(@Valid @RequestBody TvWallLayoutRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        TvWallLayoutResponse response = tvWallService.layout(request);
        TvWallLayoutVO tvWallLayoutVO = TvWallConvert.INSTANCE.layoutResponseToLayoutVO(response);
        return BaseResult.succeed(tvWallLayoutVO);
    }

    /**
     * 主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）
     *
     * @param request
     * @return
     */
    @PostMapping("query")
    @ApiOperation("主要是查询虚拟屏窗口绑定关系信息和解码通道使用模式（使用显控平台的内置解码通道资源或外部绑定的解码通道资源）")
    public BaseResult<TvWallQueryPipelineVO> query(@Valid @RequestBody TvWallQueryPipelineRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        TvWallQueryPipelineResponse response = tvWallService.query(request);
        TvWallQueryPipelineVO tvWallQueryPipelineVO = TvWallConvert.INSTANCE.queryPipelineResponseToQueryPipelineVO(response);
        return BaseResult.succeed(tvWallQueryPipelineVO);
    }

    /**
     * 配置虚拟屏
     *
     * @param request
     * @return
     */
    @PostMapping("config")
    @ApiOperation("配置虚拟屏")
    public BaseResult<TvWallConfigVO> config(@Valid @RequestBody TvWallConfigRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        TvWallConfigResponse response = tvWallService.config(request);
        TvWallConfigVO tvWallConfigVO = TvWallConvert.INSTANCE.configResponseToConfigVO(response);
        return BaseResult.succeed(tvWallConfigVO);
    }

    /**
     * 配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式
     *
     * @param request
     * @return
     */
    @PostMapping("config/bind")
    @ApiOperation("配置虚拟屏窗口与资源的绑定关系、解码通道资源使用模式")
    public BaseResult<TvWallPipelineBindVO> configBind(@Valid @RequestBody TvWallPipelineBindRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        TvWallPipelineBindResponse response = tvWallService.configBind(request);
        TvWallPipelineBindVO tvWallPipelineBindVOVO = TvWallConvert.INSTANCE.pipelineBindResponseToPipelineBindVO(response);
        return BaseResult.succeed(tvWallPipelineBindVOVO);
    }

    /**
     * 拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系
     *
     * @param request
     * @return
     */
    @PostMapping("delete")
    @ApiOperation("拼接屏模式不允许删除，虚拟屏模式全部删除，混合屏模式删除绑定关系")
    public BaseResult delete(@Valid @RequestBody TvWallDeleteRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        tvWallService.delete(request);
        return BaseResult.succeed("删除成功");
    }

}
