package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
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

    @PostMapping("/all")
    @ApiOperation(value = "查询所有CU")
    public BaseResult<List<CuEntity>> all() {

        List<CuEntity> list = cuService.list();
        return BaseResult.succeed("查询所有设备成功",list);
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
    public BaseResult<DevEntityVo> loginById(@Valid @RequestBody CuRequestDto dto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.loginById(dto);
    }

    @PostMapping("/hb")
    @ApiOperation(value = "发送心跳 登录CU以后必须每9分钟调用一次这个接口，否则有可能导致C++与CU的token失效，然后你再去尝试调用接口就会失败 接口调用成功会返回0")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID",required = true)})
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


    @ApiOperation("获取平台时间")
    @PostMapping("/getTime")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID",required = true)})
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

    @ApiOperation("PTZ控制")
    @PostMapping("/controlPtz")
    public BaseResult<String> controlPtz(@Valid @RequestBody ControlPtzRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.controlPtz(requestDto);
    }

    @ApiOperation("开启平台录像")
    @PostMapping("/startRec")
    public BaseResult<Boolean> startRec(@Valid @RequestBody PlatRecStartVo platRecStartVo, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.startRec(platRecStartVo);
    }

    @ApiOperation("关闭平台录像")
    @PostMapping("/stopRec")
    public BaseResult<Boolean> stopRec(@Valid @RequestBody PlatRecStopVo platRecStopVo, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.stopRec(platRecStopVo);
    }

    @ApiOperation("开启前端录像")
    @PostMapping("/startPuRec")
    public BaseResult<Boolean> startPuRec(@Valid @RequestBody PuRecStartVo puRecStartVo, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.startPuRec(puRecStartVo);
    }

    @ApiOperation("关闭前端录像")
    @PostMapping("/stopPuRec")
    public BaseResult<Boolean> stopPuRec(@Valid @RequestBody PuRecStopVo puRecStopVo, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.stopPuRec(puRecStopVo);
    }

    @ApiOperation("打开平台录像锁定")
    @PostMapping("/openLockingRec")
    public BaseResult<Boolean> openLockingRec(@Valid @RequestBody OpenLockingRecRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.openLockingRec(requestDto);
    }

    @ApiOperation("取消平台录像锁定")
    @PostMapping("/cancelLockingRec")
    public BaseResult<Boolean> cancelLockingRec(@Valid @RequestBody CancelLockingRecRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.cancelLockingRec(requestDto);
    }

    @ApiOperation("查询录像")
    @PostMapping("/queryVideo")
    public BaseResult<QueryVideoResponseVo> queryVideo(@Valid @RequestBody QueryVideoRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.queryVideo(requestDto);
    }

    @ApiOperation("查询录像日历（即当天是否有录像）")
    @PostMapping("/queryVideoCalendar")
    public BaseResult<QueryVideoCalendarResponseVo> queryVideoCalendar(@Valid @RequestBody QueryVideoCalendarRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.queryVideoCalendar(requestDto);
    }

    @ApiOperation("查询磁阵(磁盘)信息")
    @PostMapping("/queryDisk")
    public BaseResult<QueryDiskResponseVo> queryDisk(@Valid @RequestBody QueryDiskRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.queryDisk(requestDto);
    }

    @PostMapping("/findByCondition")
    public BaseResult<DevEntityVo> findByCondition(@RequestBody FindCuByConditionVo findCuByConditionVo){

        return cuService.findByCondition(findCuByConditionVo);
    }


    @PostMapping("/queryMonitor")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId", value = "数据库ID")})
    public BaseResult<DevEntityVo> queryMonitor(@RequestParam("kmId") Integer kmId){

        return cuService.queryMonitor(kmId);

    }

    @ApiOperation("获取设备详细信息")
    @PostMapping("getCuDeviceInfo")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId",  value = "数据库ID",required = true), @ApiImplicitParam(name = "puId", value = "设备号",required = true)})
    public BaseResult<CuDeviceVo> getCuDeviceInfo(@RequestParam("kmId") Integer kmId,@RequestParam("puId") String puId){

        return cuService.getCuDeviceInfo(kmId,puId);

    }

    @ApiOperation("获取设备具体通道信息")
    @PostMapping("getCuChannelInfo")
    @ApiImplicitParams({@ApiImplicitParam(name = "kmId",  value = "数据库ID",required = true), @ApiImplicitParam(name = "puId", value = "设备号",required = true),
            @ApiImplicitParam(name = "sn", value = "通道号",required = true)})
    public BaseResult<CuChannelVo> getCuChannelInfo(@RequestParam("kmId") Integer kmId, @RequestParam("puId") String puId, @RequestParam("sn") Integer sn){

        return cuService.getCuChannelInfo(kmId,puId,sn);

    }

    @ApiOperation("获取设备通道集合")
    @PostMapping("getCuChannelList")
    public BaseResult<List<CuChannelVo>> getCuChannelList(@RequestBody CuChnListDto requestDto, BindingResult br){
        ValidUtils.paramValid(br);

        return cuService.getCuChannelList(requestDto);

    }

    @ApiOperation("获取监控平台根分组信息")
    @PostMapping("/cuGroup")
    public BaseResult<DevEntityVo> cuGroup(@Valid @RequestBody CuRequestDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.cuGroup(requestDto);
    }

    @ApiOperation("获取监控平台子分组信息")
    @PostMapping("/cuGroupById")
    public BaseResult<List<CuGroupVo>> cuGroupById(@Valid @RequestBody CuGroupDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.cuGroupById(requestDto);
    }

    @ApiOperation("获取监控平台设备信息")
    @PostMapping("/cuDevice")
    public BaseResult<List<CuDeviceVo>> cuDevice(@Valid @RequestBody CuDevicesDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.cuDevice(requestDto);
    }

    @ApiOperation("获取国标id")
    @PostMapping("/gbId")
    public BaseResult<GbIdVo> gbId(@Valid @RequestBody GbIdDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.gbId(requestDto);
    }

    @ApiOperation("获取平台2.0puId")
    @PostMapping("/puIdTwo")
    public BaseResult<PuIdTwoVo> puIdTwo(@Valid @RequestBody PuIdTwoDto requestDto, BindingResult br) {

        ValidUtils.paramValid(br);

        return cuService.puIdTwo(requestDto);
    }

}
