package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.device.core.service.SvrTypeService;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.entity.SvrTypeEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.pojo.SvrTypePageQueryDTO;
import com.kedacom.svr.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * SVR设备类型
 *
 * @author ycw
 * @version v1.0
 * @date 2021/09/06 9:44
 * @description
 */
@Slf4j
@RestController
@RequestMapping("ums/svrType")
@Api(value = "SVR设备类型", tags = "SVR设备类型")
public class SvrTypeController {

    @Autowired
    private SvrTypeService service;


    @PostMapping("/pageQuery")
    @ApiOperation(value = "svr设备类型分页查询")
    public BaseResult<BasePage<SvrTypeEntity>> pageQuery(@RequestBody SvrTypePageQueryDTO queryDTO) {
        log.info("svr分页接口入参:{}", queryDTO);

        return service.pageQuery(queryDTO);
    }

    @PostMapping("/all")
    @ApiOperation(value = "查询所有SVR设备类型")
    public BaseResult<List<SvrTypeEntity>> all() {

        List<SvrTypeEntity> list = service.list();
        return BaseResult.succeed(list);
    }


    @PostMapping("/info")
    @ApiOperation(value = "根据id获取svr设备类型信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrTypeEntity> info(@RequestParam Integer dbId) {
        log.info("根据id获取svr信息:{}", dbId);

        return BaseResult.succeed(service.getById(dbId));
    }


    @PostMapping("/save")
    @ApiOperation(value = "新增svr设备类型")
    public BaseResult<SvrTypeEntity> save(@Valid @RequestBody SvrTypeEntity entity, BindingResult br) {
        ValidUtils.paramValid(br);

        return service.saveInfo(entity);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改svr设备类型")
    public BaseResult<SvrTypeEntity> update(@RequestBody SvrTypeEntity entity) {
        service.updateById(entity);

        return BaseResult.succeed(entity);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除svr设备类型")
    public BaseResult delete(@RequestBody Long[] ids) {
        log.info("删除svr:{}", ids);
        service.removeByIds(Arrays.asList(ids));

        return BaseResult.succeed("删除成功");
    }





}
