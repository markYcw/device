package com.kedacom.device.controller;

import com.alibaba.fastjson.JSON;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.RemotePoint;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.vo.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSException;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SVR控制相关
 *
 * @author ycw
 * @version v1.0
 * @date 2021/09/06 9:44
 * @description
 */
@Slf4j
@RestController
@RequestMapping("ums/svr")
@Api(value = "SVR控制相关", tags = "SVR控制层")
public class SvrController {

    @Autowired
    private SvrService svrService;


    @PostMapping("/pageQuery")
    @ApiOperation(value = "svr分页查询")
    public BaseResult<BasePage<SvrEntity>> pageQuery(@RequestBody SvrPageQueryDTO queryDTO) {
        log.info("svr分页接口入参:{}", queryDTO);

        return svrService.pageQuery(queryDTO);
    }


    @PostMapping("/info")
    @ApiOperation(value = "根据id获取svr信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrEntity> info(@RequestParam Integer dbId) {
        log.info("根据id获取svr信息:{}", dbId);

        return BaseResult.succeed(svrService.getById(dbId));
    }


    @PostMapping("/save")
    @ApiOperation(value = "新增svr")
    public BaseResult<SvrEntity> save(@Valid @RequestBody SvrEntity entity, BindingResult br) {
        log.info("新增svr:{}", entity);
        ValidUtils.paramValid(br);
        svrService.save(entity);

        return BaseResult.succeed(entity);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改svr")
    public BaseResult<SvrEntity> update(@RequestBody SvrEntity entity) {
        log.info("修改svr:{}", entity);
        svrService.updateById(entity);

        return BaseResult.succeed(entity);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除svr")
    public BaseResult delete(@RequestBody Long[] ids) {
        log.info("删除svr:{}", ids);
        svrService.removeByIds(Arrays.asList(ids));

        return BaseResult.succeed("删除成功");
    }


    @PostMapping("/svrNotify")
    @ApiOperation(value = "svr通知")
    public void svrNotify(@RequestBody String notify) {

        svrService.svrNotify(notify);
    }


    @PostMapping("/loginById")
    @ApiOperation(value = "根据id获取svr信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrLoginVO> loginById(@RequestParam Integer dbId) {

        return svrService.loginById(dbId);
    }

    @ApiOperation("登出svr")
    @PostMapping("/logoutById")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult logoutById(@RequestParam Integer dbId) {

        return svrService.logoutById(dbId);
    }

    @ApiOperation("获取SVR能力集")
    @PostMapping("/svrCap")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrCapVo> svrCap(@RequestParam Integer dbId) {

        return svrService.svrCap(dbId);
    }

    @ApiOperation("获取SVR时间")
    @PostMapping("/svrTime")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrTimeVo> svrTime(@RequestParam Integer dbId) {

        return svrService.svrTime(dbId);
    }

    @ApiOperation("搜索编解码设备")
    @PostMapping("/searchDev")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<String> searchDev(@RequestParam Integer dbId) {

        return svrService.searchDev(dbId);
    }

    @ApiOperation("获取编码通道列表")
    @PostMapping("/enChnList")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<EnChnListVo> enChnList(@RequestParam Integer dbId) {

        return svrService.enChnList(dbId);
    }

    @ApiOperation("添加/删除编码通道")
    @PostMapping("/enChn")
    public BaseResult<String> enChn(@RequestBody EnChnDto enChnDto) {

        return svrService.enChn(enChnDto);
    }

    @ApiOperation("获取编码器的预置位")
    @PostMapping("/enCpReset")
    public BaseResult<CpResetVo> enCpReset(EnCpResetDto dto) {

        return svrService.enCpReset(dto);
    }

    @ApiOperation("修改编码器预置位")
    @PostMapping("/CpReset")
    public BaseResult<String> CpReset(CpResetDto dto) {

        return svrService.CpReset(dto);
    }

    @ApiOperation("获取解码通道列表")
    @PostMapping("/deChnList")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<DeChnListVo> deChnList(@RequestParam Integer dbId) {

        return svrService.deChnList(dbId);
    }

    @ApiOperation("添加/删除解码通道")
    @PostMapping("/deChn")
    public BaseResult<String> deChn(@RequestBody DeChnDto dto) {

        return svrService.deChn(dto);
    }

    @ApiOperation("获取解码参数")
    @PostMapping("/decParam")
    public BaseResult<DecParamVo> decParam(@RequestBody DecParamDto dto) {

        return svrService.decParam(dto);
    }

    @ApiOperation("设置解码参数")
    @PostMapping("/enDeParam")
    public BaseResult<String> enDeParam(@RequestBody EnDecParamDto dto) {

        return svrService.enDeParam(dto);
    }


    @ApiOperation("PTZ控制")
    @PostMapping("/ptz")
    public BaseResult<String> ptz(@RequestBody PtzDto dto) {

        return svrService.ptz(dto);
    }

    @ApiOperation("启用/停止远程点")
    @PostMapping("/remotePoint")
    public BaseResult<String> remotePoint(@RequestBody RemotePointDto dto) {

         return svrService.remotePoint(dto);
    }

    @ApiOperation("获取远程点配置")
    @PostMapping("/remoteCfg")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<RemoteCfgVo> remoteCfg(@RequestParam Integer dbId) {

        return svrService.remoteCfg(dbId);
    }



}
