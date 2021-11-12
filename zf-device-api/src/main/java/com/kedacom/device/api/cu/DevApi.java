package com.kedacom.device.api.cu;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.*;
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
    BaseResult<BasePage<DevEntityVo>> list(@RequestBody DevEntityQuery queryDTO);

    @PostMapping("/info")
    @ApiOperation(value = "根据数据库id获取cu信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    BaseResult<DevEntityVo> info(@RequestParam("kmId") Integer kmId);

    @PostMapping("/saveDevFeign")
    @ApiOperation(value = "新增cu")
    BaseResult<DevEntityVo> saveDevFeign(@RequestBody DevEntityVo devEntityVo);

    @ApiOperation("修改监控平台信息")
    @PostMapping("/updateDev")
    BaseResult<DevEntityVo> updateDev(@RequestBody DevEntityVo devEntityVo);

    @PostMapping("/delete")
    @ApiOperation(value = "删除cu")
    BaseResult delete(@RequestBody Long[] ids);

    @PostMapping("/loginById")
    @ApiOperation(value = "根据ID登录cu")
    BaseResult<DevEntityVo> loginById(@RequestBody CuRequestDto dto);

    @ApiOperation("登出cu")
    @PostMapping("/logoutById")
    BaseResult<String> logoutById(@RequestBody CuRequestDto dto);

    @ApiOperation("获取平台域信息")
    @PostMapping("/localDomain")
    BaseResult<LocalDomainVo> localDomain(@RequestBody CuRequestDto dto);

    @ApiOperation("获取域链表")
    @PostMapping("/domains")
    BaseResult<DomainsVo> domains(@RequestBody CuRequestDto dto);

    @ApiOperation("获取平台时间")
    @PostMapping("/time")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    BaseResult<Long> getTime(@RequestParam Integer kmId);

    @ApiOperation("获取多视图设备树")
    @PostMapping("/viewTrees")
    BaseResult<ViewTreesVo> viewTrees(@RequestBody CuRequestDto dto);

    @ApiOperation("选择当前操作的设备树")
    @PostMapping("/selectTree")
    BaseResult<String> selectTree(@RequestBody SelectTreeDto dto);

    @PostMapping("/hb")
    @ApiOperation(value = "发送心跳 登录CU以后必须每9分钟调用一次这个接口，否则有可能导致C++与CU的token失效，然后你再去尝试调用接口就会失败 接口调用成功会返回0")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    BaseResult<String> hb(@RequestParam Integer kmId);

    @ApiOperation("PTZ控制")
    @PostMapping("/controlPtz")
    BaseResult<String> controlPtz(@RequestBody ControlPtzRequestDto requestDto);

}
