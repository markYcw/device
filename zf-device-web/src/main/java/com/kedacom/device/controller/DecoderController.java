package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.msp.request.decoder.OsdConfigRequest;
import com.kedacom.msp.request.decoder.OsdDeleteRequest;
import com.kedacom.msp.request.decoder.StyleConfigRequest;
import com.kedacom.msp.request.decoder.StyleQueryRequest;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdConfigResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.OsdDeleteResponse;
import com.kedacom.acl.network.data.avIntegration.decoder.StyleQueryResponse;
import com.kedacom.msp.response.decoder.StyleQueryVO;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.convert.DecoderConvert;
import com.kedacom.device.core.service.DecoderService;
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
 * @Date: 2021/5/11 13:16
 */
@RestController
@RequestMapping("/api/v1/manage/decoder")
@Api(value = "解码通道管理",tags = "解码通道管理")
@Slf4j
public class DecoderController {

    @Resource
    private DecoderService decoderService;

    /**
     * 设置解码器的OSD
     *
     * @param request
     * @return
     */
    @PostMapping("osd/config")
    @ApiOperation("配置解码通道字幕信息 设置解码器的OSD")
    public BaseResult osdConfig(@Valid @RequestBody OsdConfigRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        OsdConfigResponse response = decoderService.osdConfig(request);
        return BaseResult.succeed(response.getErrstr());
    }

    /**
     * 取消解码通道的字幕显示
     *
     * @param request
     * @return
     */
    @PostMapping("osd/delete")
    @ApiOperation("取消解码通道的字幕显示")
    public BaseResult osdDelete(@Valid @RequestBody OsdDeleteRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        OsdDeleteResponse response = decoderService.osdDelete(request);
        return BaseResult.succeed(response.getErrstr());
    }

    /**
     * 查询解码通道的画面风格及最大解码能力
     *
     * @param request
     * @return
     */
    @PostMapping("style/query")
    @ApiOperation("查询解码通道的画面风格及最大解码能力")
    public BaseResult<StyleQueryVO> styleQuery(@Valid @RequestBody StyleQueryRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        StyleQueryResponse response = decoderService.styleQuery(request);
        StyleQueryVO styleQueryVO = DecoderConvert.INSTANCE.queryResponseToQueryVO(response);
        return BaseResult.succeed(styleQueryVO);
    }

    /**
     * 设置解码通道的画面风格
     *
     * @param request
     * @return
     */
    @PostMapping("style/config")
    @ApiOperation("设置解码通道的画面风格")
    public BaseResult styleConfig(@Valid @RequestBody StyleConfigRequest request, BindingResult br) {
        ValidUtils.paramValid(br);

        decoderService.styleConfig(request);
        return BaseResult.succeed("设置成功");
    }

}
