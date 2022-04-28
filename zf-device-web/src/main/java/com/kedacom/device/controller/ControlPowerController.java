package com.kedacom.device.controller;

import com.kedacom.common.model.Result;
import com.kedacom.device.core.service.ControlPowerService;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ControlPowerController
 * @Description 电源配置相关接口
 * @Aauthor zlf
 * @Date 2021/5/25 9:13
 */
@Api(description = "电源配置业务API", tags = {"电源配置业务controller"})
@Slf4j
@RestController
@RequestMapping("/power")
public class ControlPowerController {

    @Autowired
    private ControlPowerService controlPowerService;

    @ApiOperation("获取电源支持的类型")
    @GetMapping("getDevType")
    public Result<List<PowerDeviceTypeResponseVo>> getDevType() {
        return controlPowerService.getDevType();
    }

    @ApiOperation(value = "添加电源设备")
    @PostMapping(value = "/device/add")
    public Result<Integer> deviceAdd(@Valid @RequestBody PowerDeviceAddVo powerDeviceAddVo) {
        return controlPowerService.deviceAdd(powerDeviceAddVo);
    }

    @ApiOperation(value = "修改电源设备")
    @PostMapping(value = "/device/update")
    public Result<Integer> deviceUpdate(@Valid @RequestBody PowerDeviceUpdateVo powerDeviceUpdateVo) {
        return controlPowerService.deviceUpdate(powerDeviceUpdateVo);
    }

    @ApiOperation(value = "删除电源设备")
    @PostMapping(value = "/device/delete")
    public Result<Boolean> deviceDelete(@Valid @RequestBody PowerDeviceDeleteVo powerDeviceDeleteVo) {
        return controlPowerService.deviceDelete(powerDeviceDeleteVo);
    }

    @ApiOperation(value = "分页条件查询电源设备")
    @PostMapping(value = "/device/list")
    public Result<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(@Valid @RequestBody PowerDeviceListVo powerDeviceListVo) {
        return controlPowerService.deviceList(powerDeviceListVo);
    }

    @ApiOperation(value = "获取所有设备")
    @GetMapping(value = "/list/device")
    public Result<List<PowerDeviceListRspVo>> listDevices() {
        return controlPowerService.listDevices();
    }

    @ApiOperation(value = "根据Id获取设备")
    @GetMapping(value = "/list/getDeviceById")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "设备数据库Id")})
    public Result<PowerDeviceListRspVo> getDeviceById(@RequestParam(value = "id") int id) {
        return controlPowerService.getDeviceById(id);
    }

    /*
     * ================================================Bwant-IPM-08操作==============================================================
     */

    @ApiOperation(value = "添加电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/add")
    public Result<Integer> portAdd(@Valid @RequestBody PowerConfigAddVo powerConfigAddVo) {
        return controlPowerService.portAdd(powerConfigAddVo);
    }

    @ApiOperation(value = "修改电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/update")
    public Result<Integer> portUpdate(@Valid @RequestBody PowerConfigUpdateVo powerConfigUpdateVo) {
        return controlPowerService.portUpdate(powerConfigUpdateVo);
    }

    @ApiOperation(value = "删除电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/delete")
    public Result<Boolean> portDelete(@Valid @RequestBody PowerPortVo powerPortVo) {
        return controlPowerService.portDelete(powerPortVo);
    }

    @ApiOperation(value = "查询电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/list")
    public Result<List<PowerPortListVo>> portList(@RequestBody PowerConfigListVo powerConfigListVo) {
        return controlPowerService.portList(powerConfigListVo);
    }

    @ApiOperation(value = "获取所有设备，填充下拉列表（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/datas")
    public Result<List<PowerDeviceVo>> getDeviceDatas() {
        return controlPowerService.getDeviceDatas();
    }

    @ApiOperation(value = "启动TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/start")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "电源配置数据库Id")})
    public Result powerStart(@RequestParam(value = "id") int id) throws IOException {
        return controlPowerService.powerStart(id);
    }

    @ApiOperation(value = "获取设备详细信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/msg")
    public Result<PowerDeviceMessageVo> deviceMessage(@Valid @RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo) throws IOException {
        return controlPowerService.deviceMessage(powerDeviceMessageReqVo);
    }

    @ApiOperation(value = "获取设备下通道开关状态（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/channel/state")
    public Result<List<PowerChannelStateVo>> deviceChannelState(@Valid @RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo) throws IOException {
        return controlPowerService.deviceChannelState(powerDeviceMessageReqVo);
    }

    @ApiOperation(value = "单个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turn")
    public Result<Boolean> deviceTurn(@Valid @RequestBody PowerDeviceTurnVO vo) throws IOException {
        return controlPowerService.deviceTurn(vo);
    }

    @ApiOperation(value = "单个电源多个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turns")
    public Result<Boolean> deviceTurns(@Valid @RequestBody PowerDeviceTurnsVo powerDeviceTurnsVo) throws IOException {
        return controlPowerService.deviceTurns(powerDeviceTurnsVo);
    }

    @ApiOperation(value = "关闭TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/stop")
    public Result<Boolean> powerStop() {
        return controlPowerService.powerStop();
    }

}
