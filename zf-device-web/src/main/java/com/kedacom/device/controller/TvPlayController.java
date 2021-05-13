package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.tvplay.*;
import com.kedacom.avIntegration.response.tvplay.TvPlayOpenVO;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.convert.TvPlayConvert;
import com.kedacom.acl.network.data.avIntegration.tvplay.TvPlayOpenResponse;
import com.kedacom.device.core.service.TvPlayService;
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
 * @Date: 2021/5/10 09:47
 */
@RestController
@RequestMapping("/api/v1/manage/tvplay")
@Api(value = "窗口浏览",tags = "窗口浏览")
@Slf4j
public class TvPlayController {

    @Resource
    private TvPlayService tvPlayService;


    /**
     * 信号源在指定窗口的显示，可批量显示
     *
     * @param request
     * @return
     */
    @PostMapping("batch/start")
    @ApiOperation("信号源在指定窗口的显示，可批量显示")
    public BaseResult batchStart(@Valid @RequestBody BatchStartRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        tvPlayService.batchStart(request);
        return BaseResult.succeed("开启成功");
    }

    /**
     * 关闭大屏上正在显示的窗口，可批量关闭
     *
     * @param request
     * @return
     */
    @PostMapping("batch/stop")
    @ApiOperation("关闭大屏上正在显示的窗口，可批量关闭")
    public BaseResult batchStop(@Valid @RequestBody BatchStopRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        tvPlayService.batchStop(request);
        return BaseResult.succeed("关闭成功");
    }

    /**
     * 清空大屏上正在显示的窗口
     *
     * @param request
     * @return
     */
    @PostMapping("clear")
    @ApiOperation("清空大屏上正在显示的窗口")
    public BaseResult clear(@Valid @RequestBody TvPlayClearRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        tvPlayService.clear(request);
        return BaseResult.succeed("清空成功");
    }

    /**
     * 设置窗口显示风格，支持一画面、四画面切换
     *
     * @param request
     * @return
     */
    @PostMapping("style")
    @ApiOperation("设置窗口显示风格，支持一画面、四画面切换")
    public BaseResult style(@Valid @RequestBody TvPlayStyleRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

      tvPlayService.style(request);
       return BaseResult.succeed("设置成功");
    }

    /**
     * 大屏任意位置自由开窗，不依赖预案
     *
     * @param request
     * @return
     */
    @PostMapping("open")
    @ApiOperation("大屏任意位置自由开窗，不依赖预案")
    public BaseResult<TvPlayOpenVO> open(@Valid @RequestBody TvPlayOpenRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        TvPlayOpenResponse response = tvPlayService.open(request);
        TvPlayOpenVO tvPlayOpenVO = TvPlayConvert.INSTANCE.openResponseToOpenVO(response);
        return BaseResult.succeed(tvPlayOpenVO);
    }

    /**
     * 正在开窗的窗口显示顺序修改
     *
     * @param request
     * @return
     */
    @PostMapping("order")
    @ApiOperation("正在开窗的窗口显示顺序修改")
    public BaseResult order(@Valid @RequestBody TvPlayOrderRequest request, BindingResult br) {
        ValidUtils.paramValid(br);


        tvPlayService.order(request);
        return BaseResult.succeed("排序成功");
    }

    /**
     * 对正在浏览（实时流）的窗口控制暂停浏览
     *
     * @param request
     * @return
     */
    @PostMapping("action")
    @ApiOperation("对正在浏览（实时流）的窗口控制暂停浏览")
    public BaseResult action(@Valid @RequestBody TvPlayActionRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        tvPlayService.action(request);
        return BaseResult.succeed("操作成功");
    }

}
