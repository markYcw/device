package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.dto.CuPageQueryDTO;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.DevGroupsDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.pojo.Domains;
import com.kedacom.cu.vo.DomainsVo;
import com.kedacom.cu.vo.LocalDomainVo;
import com.kedacom.cu.vo.TimeVo;
import com.kedacom.cu.vo.ViewTreesVo;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.CuService;
import com.kedacom.svr.dto.*;
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


    @PostMapping("/pageQuery")
    @ApiOperation(value = "cu分页查询")
    public BaseResult<BasePage<CuEntity>> pageQuery(@RequestBody CuPageQueryDTO queryDTO) {
        log.info("cu分页接口入参:{}", queryDTO);

        return cuService.pageQuery(queryDTO);
    }


    @PostMapping("/info")
    @ApiOperation(value = "根据id获取cu信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<CuEntity> info(@RequestParam Integer dbId) {
        log.info("根据id获取cu信息:{}", dbId);

        return BaseResult.succeed(cuService.getById(dbId));
    }


    @PostMapping("/save")
    @ApiOperation(value = "新增cu")
    public BaseResult<CuEntity> save(@Valid @RequestBody CuEntity entity, BindingResult br) {
        log.info("新增cu:{}", entity);
        ValidUtils.paramValid(br);
        entity.setType(DevTypeConstant.updateRecordKey);
        cuService.save(entity);

        return BaseResult.succeed(entity);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改cu")
    public BaseResult<CuEntity> update(@RequestBody CuEntity entity) {
        log.info("修改cu:{}", entity);
        cuService.updateById(entity);

        return BaseResult.succeed(entity);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除cu")
    public BaseResult delete(@RequestBody Long[] ids) {
        log.info("删除cu:{}", ids);
        cuService.removeByIds(Arrays.asList(ids));

        return BaseResult.succeed("删除成功");
    }


    @PostMapping("/cuNotify")
    @ApiOperation(value = "cu通知")
    public void cuNotify(@RequestBody String notify) {

        cuService.cuNotify(notify);
    }


    @PostMapping("/loginById")
    @ApiOperation(value = "根据ID登录cu")
    public BaseResult<String> loginById(@Valid @RequestBody CuRequestDto dto) {

        return cuService.loginById(dto);
    }

    @PostMapping("/hb")
    @ApiOperation(value = "发送心跳")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<String> hb(@RequestParam Integer dbId) {

        return cuService.hb(dbId);
    }

    @ApiOperation("登出cu")
    @PostMapping("/logoutById")
    public BaseResult<String> logoutById(@Valid @RequestBody CuRequestDto dto) {

        return cuService.logoutById(dto);
    }

    @ApiOperation("获取平台域信息")
    @PostMapping("/localDomain")
    public BaseResult<LocalDomainVo> localDomain(@Valid @RequestBody CuRequestDto dto) {

        return cuService.localDomain(dto);
    }

    @ApiOperation("获取域链表")
    @PostMapping("/domains")
    public BaseResult<DomainsVo> domains(@Valid @RequestBody CuRequestDto dto) {

        return cuService.domains(dto);
    }

    @ApiOperation("获取平台时间")
    @PostMapping("/time")
    public BaseResult<TimeVo> time(@Valid @RequestBody CuRequestDto dto) {

        return cuService.time(dto);
    }

    @ApiOperation("获取多视图设备树")
    @PostMapping("/viewTrees")
    public BaseResult<ViewTreesVo> viewTrees(@Valid @RequestBody CuRequestDto dto) {

        return cuService.viewTrees(dto);
    }

    @ApiOperation("选择当前操作的设备树")
    @PostMapping("/selectTree")
    public BaseResult<String> selectTree(@Valid @RequestBody SelectTreeDto dto) {

        return cuService.selectTree(dto);
    }

    @ApiOperation("获取设备组信息")
    @PostMapping("/devGroups")
    public BaseResult<String> devGroups(@Valid @RequestBody DevGroupsDto dto) {

        return cuService.devGroups(dto);
    }


}
