package com.kedacom.device.api.cu;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.*;
import com.kedacom.device.api.cu.fallback.CuApiFallbackFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/all")
    @ApiOperation(value = "查询所有CU")
    BaseResult<List<CuEntity>> all();

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

    @ApiOperation("开启平台录像")
    @PostMapping("/startRec")
    BaseResult<Boolean> startRec(@RequestBody PlatRecStartVo platRecStartVo);

    @ApiOperation("关闭平台录像")
    @PostMapping("/stopRec")
    BaseResult<Boolean> stopRec(@RequestBody PlatRecStopVo platRecStopVo);

    @ApiOperation("开启前端录像")
    @PostMapping("/startPuRec")
    BaseResult<Boolean> startPuRec(@RequestBody PuRecStartVo puRecStartVo);

    @ApiOperation("关闭前端录像")
    @PostMapping("/stopPuRec")
    BaseResult<Boolean> stopPuRec(@RequestBody PuRecStopVo puRecStopVo);

    @ApiOperation("打开录像锁定")
    @PostMapping("/openLockingRec")
    BaseResult<Boolean> openLockingRec(@RequestBody OpenLockingRecRequestDto requestDto);

    @ApiOperation("取消录像锁定")
    @PostMapping("/cancelLockingRec")
    BaseResult<Boolean> cancelLockingRec(@RequestBody CancelLockingRecRequestDto requestDto);

    @ApiOperation("查询录像")
    @PostMapping("/queryVideo")
    BaseResult<QueryVideoResponseVo> queryVideo(@RequestBody QueryVideoRequestDto requestDto);

    @ApiOperation("查询录像日历（即当天是否有录像）")
    @PostMapping("/queryVideoCalendar")
    BaseResult<QueryVideoCalendarResponseVo> queryVideoCalendar(@RequestBody QueryVideoCalendarRequestDto requestDto);

    @ApiOperation("查询磁阵(磁盘)信息")
    @GetMapping("/queryDisk")
    BaseResult<QueryDiskResponseVo> queryDisk(@RequestBody QueryDiskRequestDto requestDto);

    @ApiOperation("获取监控平台分组信息")
    @PostMapping("/cuGroup")
    BaseResult<DevEntityVo> cuGroup(@RequestBody CuRequestDto requestDto);

    @ApiOperation("获取监控平台设备信息")
    @PostMapping("/cuDevice")
    BaseResult<List<CuDeviceVo>> cuDevice(@RequestBody CuDevicesDto requestDto);

    @ApiOperation("获取监控平台子分组信息")
    @PostMapping("/cuGroupById")
    BaseResult<List<CuGroupVo>> cuGroupById(@RequestBody CuGroupDto requestDto);

    @ApiOperation("获取国标id")
    @PostMapping("/gbId")
    BaseResult<GbIdVo> gbId(@RequestBody GbIdDto requestDto);

    @ApiOperation("获取平台2.0puId")
    @PostMapping("/puIdTwo")
    BaseResult<PuIdTwoVo> puIdTwo(@RequestBody PuIdTwoDto requestDto);

    @ApiOperation("获取平台1.0puId")
    @PostMapping("/puIdOne")
    BaseResult<PuIdOneVo> puIdOne(@RequestBody PuIdOneDto requestDto);

    @ApiOperation("根据平台1.0PuId获取平台2.0puId")
    @PostMapping("/puIdByOne")
    BaseResult<PuIdByOneVo> puIdByOne(@RequestBody PuIdByOneDto requestDto);

    @ApiOperation("获取设备通道集合")
    BaseResult<List<CuChannelVo>> getCuChannelList(@RequestBody CuChnListDto requestDto);


}
