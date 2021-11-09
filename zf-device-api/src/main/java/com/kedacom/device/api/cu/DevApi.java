package com.kedacom.device.api.cu;


import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.DevEntityQuery;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.*;
import com.kedacom.device.api.cu.fallback.CuApiFallbackFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * svr相关操作
 * @author ycw
 * @date 2021/09/07 14:55
 */
@FeignClient(name = "device-server", contextId = "cuApi", path = "/api-device/ums/cu",fallbackFactory = CuApiFallbackFactory.class)
public interface DevApi {


    @PostMapping("/list")
    @ApiOperation(value = "cu分页查询")
    public BaseResult<BasePage<DevEntityVo>> list(@RequestBody DevEntityQuery queryDTO);

    @PostMapping("/info")
    @ApiOperation(value = "根据数据库id获取cu信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    public BaseResult<DevEntityVo> info(@RequestParam("kmId") Integer kmId);


    @PostMapping("/saveDevFeign")
    @ApiOperation(value = "新增cu")
    public BaseResult<DevEntityVo> saveDevFeign(@RequestBody DevEntityVo devEntityVo);

    @ApiOperation("修改监控平台信息")
    @PostMapping("/updateDev")
    public BaseResult<DevEntityVo> updateDev(@RequestBody DevEntityVo devEntityVo);

    @PostMapping("/delete")
    @ApiOperation(value = "删除cu")
    public BaseResult delete(@RequestBody Long[] ids);

    @PostMapping("/loginById")
    @ApiOperation(value = "根据ID登录cu")
    public BaseResult<String> loginById(@RequestBody CuRequestDto dto);

    @ApiOperation("登出cu")
    @PostMapping("/logoutById")
    public BaseResult<String> logoutById(@RequestBody CuRequestDto dto);

    @ApiOperation("获取平台域信息")
    @PostMapping("/localDomain")
    public BaseResult<LocalDomainVo> localDomain(@RequestBody CuRequestDto dto);

    @ApiOperation("获取域链表")
    @PostMapping("/domains")
    public BaseResult<DomainsVo> domains(@RequestBody CuRequestDto dto);

    @ApiOperation("获取平台时间")
    @PostMapping("/time")
    public BaseResult<TimeVo> time(@RequestBody CuRequestDto dto);

    @ApiOperation("获取多视图设备树")
    @PostMapping("/viewTrees")
    public BaseResult<ViewTreesVo> viewTrees(@RequestBody CuRequestDto dto);

    @ApiOperation("选择当前操作的设备树")
    @PostMapping("/selectTree")
    public BaseResult<String> selectTree(@RequestBody SelectTreeDto dto);



}
