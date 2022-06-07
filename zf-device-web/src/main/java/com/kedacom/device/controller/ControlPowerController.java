package com.kedacom.device.controller;

import com.kedacom.common.model.Result;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.power.ConfigPower;
import com.kedacom.device.core.service.ControlPowerService;
import com.kedacom.power.entity.LanDevice;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Api(description = "电源配置业务API", tags = {"电源配置业务controller"})
@Slf4j
@RestController
@RequestMapping("/power")
public class ControlPowerController {

    @Autowired
    private ControlPowerService controlPowerService;

    @Autowired
    private ConfigPower configPower;

    @ApiOperation("获取电源支持的类型")
    @GetMapping("getDevType")
    public Result<List<PowerDeviceTypeResponseVo>> getDevType() {
        return controlPowerService.getDevType();
    }

    @ApiOperation(value = "手动添加-添加电源设备")
    @PostMapping(value = "/device/add")
    public Result<Integer> deviceAdd(@Valid @RequestBody PowerDeviceAddVo powerDeviceAddVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceAdd(powerDeviceAddVo);
    }

    @ApiOperation(value = "局域网-添加电源设备")
    @PostMapping(value = "/device/lanDeviceAdd")
    public Result<Integer> lanDeviceAdd(@Valid @RequestBody LanPowerDeviceAddVo vo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.lanDeviceAdd(vo);
    }

    @ApiOperation(value = "修改电源设备")
    @PostMapping(value = "/device/update")
    public Result<Integer> deviceUpdate(@Valid @RequestBody PowerDeviceUpdateVo powerDeviceUpdateVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceUpdate(powerDeviceUpdateVo);
    }

    @ApiOperation(value = "删除电源设备")
    @PostMapping(value = "/device/delete")
    public Result<Boolean> deviceDelete(@Valid @RequestBody PowerDeviceDeleteVo powerDeviceDeleteVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceDelete(powerDeviceDeleteVo);
    }

    @ApiOperation(value = "分页条件查询电源设备")
    @PostMapping(value = "/device/list")
    public Result<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(@Valid @RequestBody PowerDeviceListVo powerDeviceListVo, BindingResult br) {
        ValidUtils.paramValid(br);

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

    @ApiOperation(value = "对局域网搜索进行配置")
    @GetMapping(value = "/device/lanConfig")
    @ApiImplicitParams({@ApiImplicitParam(name = "ip", required = false, value = "广播搜索的服务器ip")
            , @ApiImplicitParam(name = "timeout", required = false, value = "超时时间")
            , @ApiImplicitParam(name = "searchTime", required = false, value = "广播搜索时间")})
    public void lanConfig(@RequestParam("ip") String ip,
                          @RequestParam("timeout") Long timeout,
                          @RequestParam("searchTime") Long searchTime) {
        configPower.init(ip, timeout, searchTime);
    }

    @ApiOperation(value = "局域网搜索")
    @GetMapping(value = "/device/lanSearch")
    public Result<Set<LanDevice>> lanSearch() {
        try {
            Set<LanDevice> devices = controlPowerService.searchDevices();
            return Result.succeed(devices);
        } catch (Exception e) {
            log.error("局域网搜索失败：{}", e.getMessage());
            return Result.failed("局域网搜索失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "局域网搜索-根据设备Mac获取电源的详细配置")
    @GetMapping(value = "/device/getPowerConfigByMac")
    @ApiImplicitParams({@ApiImplicitParam(name = "macAddr", required = true, value = "设备Mac地址")})
    public Result<PowerLanConfigVO> getPowerConfigByMac(@RequestParam("macAddr") String macAddr) {
        try {
            return controlPowerService.getPowerConfigByMac(macAddr);
        } catch (Exception e) {
            log.error("获取电源的具体配置失败：{}", e.getMessage());
            return Result.failed("获取电源的具体配置失败：" + e.getMessage());
        }
    }

    /*
     * ================================================Bwant-IPM-08操作==============================================================
     */

    @ApiOperation(value = "添加电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/add")
    public Result<Integer> portAdd(@Valid @RequestBody PowerConfigAddVo powerConfigAddVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.portAdd(powerConfigAddVo);
    }

    @ApiOperation(value = "修改电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/update")
    public Result<Integer> portUpdate(@Valid @RequestBody PowerConfigUpdateVo powerConfigUpdateVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.portUpdate(powerConfigUpdateVo);
    }

    @ApiOperation(value = "删除电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/delete")
    public Result<Boolean> portDelete(@Valid @RequestBody PowerPortVo powerPortVo, BindingResult br) {
        ValidUtils.paramValid(br);

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
    public Result<PowerDeviceMessageVo> deviceMessage(@Valid @RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceMessage(powerDeviceMessageReqVo);
    }

    @ApiOperation(value = "获取设备下通道开关状态（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/channel/state")
    public Result<List<PowerChannelStateVo>> deviceChannelState(@Valid @RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceChannelState(powerDeviceMessageReqVo);
    }

    @ApiOperation(value = "单个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turn")
    public Result<Boolean> deviceTurn(@Valid @RequestBody PowerDeviceTurnVO vo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceTurn(vo);
    }

    @ApiOperation(value = "单个电源多个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turns")
    public Result<Boolean> deviceTurns(@Valid @RequestBody PowerDeviceTurnsVo powerDeviceTurnsVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.deviceTurns(powerDeviceTurnsVo);
    }

    @ApiOperation(value = "关闭TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/stop")
    public Result<Boolean> powerStop() {
        return controlPowerService.powerStop();
    }

}
