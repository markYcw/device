package com.kedacom.device.controller;

import cn.hutool.core.util.StrUtil;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.aiBox.request.*;
import com.kedacom.aiBox.response.AiBoxContrastResponseDto;
import com.kedacom.aiBox.response.GetThresholdResponseDto;
import com.kedacom.aiBox.response.QueryListResponseDto;
import com.kedacom.aiBox.response.SelectPageResponseDto;
import com.kedacom.common.utils.ValidUtils;
import com.kedacom.device.core.service.AiBoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wangxy
 * @describe AI-Box设备接入
 * @date 2021/10/15
 */
@RestController
@RequestMapping("nvr/ai_box")
@Api(value = "AI-Box设备接入", tags = "AI-Box设备接入")
public class AiBoxController {

    @Resource
    AiBoxService aiBoxService;

    @ApiOperation("分页查询AI-Box设备信息列表")
    @PostMapping("/selectPage")
    public BaseResult<BasePage<SelectPageResponseDto>> selectPage(@RequestBody SelectPageRequestDto requestDto) {

        BasePage<SelectPageResponseDto> result = aiBoxService.selectPage(requestDto);

        return BaseResult.succeed(result);
    }

    @ApiOperation("查询AI-Box设备信息集合")
    @PostMapping("/queryList")
    public BaseResult<List<QueryListResponseDto>> queryList(@RequestBody QueryListRequestDto requestDto) {

        List<QueryListResponseDto> result = aiBoxService.queryList(requestDto);

        return BaseResult.succeed(result);
    }

    @ApiOperation("新增/修改AI-Box设备信息")
    @PostMapping("/addOrUpdate")
    public BaseResult<String> addOrUpdate(@Valid @RequestBody AddOrUpdateRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);
        String checkResult = aiBoxService.checkRequestDto(requestDto);
        if (StrUtil.isNotBlank(checkResult)) {
            return BaseResult.failed(checkResult);
        }
        String addOrUpdateResult = aiBoxService.addOrUpdate(requestDto);
        if (StrUtil.isNotBlank(addOrUpdateResult)) {
            return BaseResult.succeed(null, addOrUpdateResult + "成功");
        }

        return BaseResult.failed(addOrUpdateResult + "失败");
    }

    @ApiOperation("删除AI-Box设备信息")
    @PostMapping("/delete")
    public BaseResult<String> delete(@Valid @RequestBody DeleteRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);
        if (aiBoxService.delete(requestDto)) {
            return BaseResult.succeed(null, "删除成功");
        }

        return BaseResult.failed("删除失败");
    }

    @ApiOperation("获取远程AIBox人脸对比阈值")
    @PostMapping("/getThreshold")
    public BaseResult<GetThresholdResponseDto> getThreshold(@RequestBody GetThresholdRequestDto requestDto) {

        GetThresholdResponseDto responseDto = aiBoxService.getThresholdOfInterface(requestDto);

        return BaseResult.succeed(null, responseDto);
    }

    @ApiOperation("图片对比")
    @PostMapping("/contrast")
    public BaseResult<String> contrast(@Valid @RequestBody ContrastRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);
        AiBoxContrastResponseDto result = aiBoxService.contrast(requestDto);
        if (result.getFlag()) {
            return BaseResult.succeed(null, "相似度 : " + result.getData() + "%");
        }

        return BaseResult.failed(result.getErrorMessage());
    }

}
