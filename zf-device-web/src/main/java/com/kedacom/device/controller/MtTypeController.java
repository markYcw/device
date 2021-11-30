package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.core.service.MtTypeService;
import com.kedacom.mt.request.QueryMtTypeListRequestDto;
import com.kedacom.mt.response.QueryMtTypeListResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/29
 */
@RestController
@RequestMapping("ums/mt/type")
@Api(value = "终端设备类型", tags = "终端设备类型")
public class MtTypeController {

    @Resource
    MtTypeService mtTypeService;

    @ApiOperation("查询终端设备类型信息集合")
    @GetMapping("/queryList")
    public BaseResult<List<QueryMtTypeListResponseVo>> queryList(@RequestBody QueryMtTypeListRequestDto requestDto) {

        List<QueryMtTypeListResponseVo> result = mtTypeService.queryList(requestDto);

        return BaseResult.succeed("查询成功", result);
    }

}
