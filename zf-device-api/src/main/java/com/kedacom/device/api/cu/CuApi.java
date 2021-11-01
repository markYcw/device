package com.kedacom.device.api.cu;


import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.CuPageQueryDTO;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.DomainsVo;
import com.kedacom.cu.vo.LocalDomainVo;
import com.kedacom.cu.vo.TimeVo;
import com.kedacom.cu.vo.ViewTreesVo;
import com.kedacom.device.api.cu.fallback.CuApiFallbackFactory;
import com.kedacom.device.api.svr.fallback.SvrApiFallbackFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


/**
 * svr相关操作
 * @author ycw
 * @date 2021/09/07 14:55
 */
@FeignClient(name = "device-server", contextId = "svrApi", path = "/api-device/ums/cu",fallbackFactory = CuApiFallbackFactory.class)
public interface CuApi {

    @PostMapping("/pageQuery")
    @ApiOperation(value = "cu分页查询")
    public BaseResult<BasePage<CuEntity>> pageQuery(@RequestBody CuPageQueryDTO queryDTO);

    @PostMapping("/info")
    @ApiOperation(value = "根据id获取cu信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<CuEntity> info(@RequestParam Integer dbId);

    @PostMapping("/save")
    @ApiOperation(value = "新增cu")
    public BaseResult<CuEntity> save(@RequestBody CuEntity entity);

    @PostMapping("/update")
    @ApiOperation(value = "修改cu")
    public BaseResult<CuEntity> update(@RequestBody CuEntity entity);

    @PostMapping("/delete")
    @ApiOperation(value = "删除cu")
    public BaseResult delete(@RequestBody Long[] ids);

    @PostMapping("/loginById")
    @ApiOperation(value = "根据ID登录cu")
    public BaseResult<String> loginById(@Valid @RequestBody CuRequestDto dto);

    @ApiOperation("登出cu")
    @PostMapping("/logoutById")
    public BaseResult<String> logoutById(@Valid @RequestBody CuRequestDto dto);

    @ApiOperation("获取平台域信息")
    @PostMapping("/localDomain")
    public BaseResult<LocalDomainVo> localDomain(@Valid @RequestBody CuRequestDto dto);

    @ApiOperation("获取域链表")
    @PostMapping("/domains")
    public BaseResult<DomainsVo> domains(@Valid @RequestBody CuRequestDto dto);

    @ApiOperation("获取平台时间")
    @PostMapping("/time")
    public BaseResult<TimeVo> time(@Valid @RequestBody CuRequestDto dto);

    @ApiOperation("获取多视图设备树")
    @PostMapping("/viewTrees")
    public BaseResult<ViewTreesVo> viewTrees(@Valid @RequestBody CuRequestDto dto);

    @ApiOperation("选择当前操作的设备树")
    @PostMapping("/selectTree")
    public BaseResult<String> selectTree(@Valid @RequestBody SelectTreeDto dto);



}
