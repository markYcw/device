package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.avIntegration.request.scheme.SchemeConfigRequest;
import com.kedacom.avIntegration.request.scheme.SchemeQueryRequest;
import com.kedacom.avIntegration.response.scheme.SchemeConfigResponse;
import com.kedacom.avIntegration.response.scheme.SchemeQueryResponse;
import com.kedacom.avIntegration.vo.scheme.SchemeConfigVO;
import com.kedacom.avIntegration.vo.scheme.SchemeQueryVO;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.convert.SchemeConvert;
import com.kedacom.device.core.service.SchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Auther: hxj
 * @Date: 2021/5/11 19:37
 */
@RestController
@RequestMapping("/api/v1/manage/scheme")
@Api(value = "预案布局", tags = "预案布局")
@Slf4j
public class SchemeController {

    @Autowired
    private SchemeService schemeService;

    /**
     * 预案的画面布局配置
     *
     * @param request
     * @return
     */
    @PostMapping("config")
    @ApiOperation("预案的画面布局配置")
    public BaseResult<SchemeConfigVO> config(@Valid @RequestBody SchemeConfigRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        SchemeConfigResponse response = schemeService.config(request);
        SchemeConfigVO schemeConfigVO = SchemeConvert.INSTANCE.configResponseToConfigVO(response);
        return BaseResult.succeed(schemeConfigVO);
    }

    /**
     * 查询预案布局，窗口位置信息
     *
     * @param request
     * @return
     */
    @PostMapping("query")
    @ApiOperation("查询预案布局，窗口位置信息")
    public BaseResult<SchemeQueryVO> query(@Valid @RequestBody SchemeQueryRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        SchemeQueryResponse response = schemeService.query(request);
        SchemeQueryVO schemeQueryVO = SchemeConvert.INSTANCE.queryResponseToQueryVO(response);
        return BaseResult.succeed(schemeQueryVO);
    }
}
