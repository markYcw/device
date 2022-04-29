package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.convert.VrsConvert;
import com.kedacom.device.core.service.DataService;
import com.kedacom.device.core.service.VrsFiveService;
import com.kedacom.svr.dto.FindByIpOrNameDto;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据迁移
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-04 16:58:24
 */
@Api(tags = "数据迁移接口")
@RestController
@RequestMapping("ums/data")
public class DataController {

    @Autowired
    private DataService dataService;


    @ApiOperation("返回数据迁移结果")
    @PostMapping("/dc")
    public BaseResult<Integer> dc() {

        return dataService.data();
    }




}
