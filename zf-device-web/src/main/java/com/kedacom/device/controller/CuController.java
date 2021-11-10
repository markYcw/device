package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.model.Result;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.vo.*;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.CuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * cu控制相关
 *
 * @author ycw
 * @version v1.0
 * @date 2021/09/06 9:44
 * @description
 */
@Slf4j
@RestController
@RequestMapping("ums/cu")
@Api(value = "监控平台控制相关", tags = "CU控制层")
public class CuController {

    @Autowired
    private CuService cuService;


    @PostMapping("/list")
    @ApiOperation(value = "cu分页查询")
    public BaseResult<BasePage<DevEntityVo>> list(@RequestBody DevEntityQuery queryDTO) {
        log.info("cu分页接口入参:{}", queryDTO);

        return cuService.pageQuery(queryDTO);
    }


    @PostMapping("/info")
    @ApiOperation(value = "根据数据库id获取cu信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    public BaseResult<DevEntityVo> info(@RequestParam("kmId") Integer kmId) {

        log.info("根据id获取cu信息:{}", kmId);
        return cuService.info(kmId);
    }


    @PostMapping("/saveDevFeign")
    @ApiOperation(value = "新增cu")
    public BaseResult<DevEntityVo> saveDevFeign(@Valid @RequestBody DevEntityVo devEntityVo, BindingResult br) {

        log.info("新增cu:{}", devEntityVo);
        ValidUtils.paramValid(br);
        return cuService.saveDev(devEntityVo);
    }


    /**
     * 修改监控平台
     */
    @ApiOperation("修改监控平台信息")
    @PostMapping("/updateDev")
    public BaseResult<DevEntityVo> updateDev(@Valid @RequestBody DevEntityVo devEntityVo, BindingResult br){

        log.info("修改cu:{}", devEntityVo);
        ValidUtils.paramValid(br);
        return cuService.updateDev(devEntityVo);
    }


    /**
     * 删除
     */
    @ApiOperation("删除监控平台信息")
    @PostMapping("/deleteDev")
    public BaseResult<String> deleteDev(@RequestBody List<Integer> ids){

        log.info("删除cu:{}", ids);
        return cuService.deleteDev(ids);
    }


    @PostMapping("/cuNotify")
    @ApiOperation(value = "cu通知")
    public void cuNotify(@RequestBody String notify) {

        cuService.cuNotify(notify);
    }


    @PostMapping("/loginById")
    @ApiOperation(value = "根据ID登录cu")
    public BaseResult<String> loginById(@Valid @RequestBody CuRequestDto dto, BindingResult br) {

        ValidUtils.paramValid(br);
        return cuService.loginById(dto);
    }

    @PostMapping("/hb")
    @ApiOperation(value = "发送心跳 登录CU以后必须每9分钟调用一次这个接口，否则有可能导致C++与CU的token失效，然后你再去尝试调用接口就会失败 接口调用成功会返回0")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    public BaseResult<String> hb(@RequestParam Integer kmId) {

        return cuService.hb(kmId);
    }

    @ApiOperation("登出cu")
    @PostMapping("/logoutById")
    public BaseResult<String> logoutById(@Valid @RequestBody CuRequestDto dto, BindingResult br) {

        ValidUtils.paramValid(br);
        return cuService.logoutById(dto);
    }

    @ApiOperation("获取平台域信息")
    @PostMapping("/localDomain")
    public BaseResult<LocalDomainVo> localDomain(@Valid @RequestBody CuRequestDto dto, BindingResult br) {

        ValidUtils.paramValid(br);
        return cuService.localDomain(dto);
    }

    @ApiOperation("获取域链表")
    @PostMapping("/domains")
    public BaseResult<DomainsVo> domains(@Valid @RequestBody CuRequestDto dto, BindingResult br) {

        ValidUtils.paramValid(br);
        return cuService.domains(dto);
    }

    /**
     * 获取平台时间
     * @param kmId 监控平台标识
     * @return 时间
     */
    @ApiOperation("获取平台时间")
    @PostMapping("/time")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    public BaseResult<Long> getTime(@RequestParam Integer kmId){

        return cuService.time(kmId);

    }

    @ApiOperation("获取多视图设备树")
    @PostMapping("/viewTrees")
    public BaseResult<ViewTreesVo> viewTrees(@Valid @RequestBody CuRequestDto dto, BindingResult br) {

        ValidUtils.paramValid(br);
        return cuService.viewTrees(dto);
    }

    @ApiOperation("选择当前操作的设备树")
    @PostMapping("/selectTree")
    public BaseResult<String> selectTree(@Valid @RequestBody SelectTreeDto dto, BindingResult br) {

        ValidUtils.paramValid(br);
        return cuService.selectTree(dto);
    }


}
