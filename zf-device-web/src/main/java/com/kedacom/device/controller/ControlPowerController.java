package com.kedacom.device.controller;

import com.kedacom.common.model.Result;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.power.ConfigPower;
import com.kedacom.device.core.service.ControlPowerService;
import com.kedacom.power.dto.UpdatePowerLanConfigDTO;
import com.kedacom.power.entity.LanDevice;
import com.kedacom.power.entity.PowerDeviceEntity;
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

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Api(tags = "电源控制")
@Slf4j
@RestController
@RequestMapping("/power")
public class ControlPowerController {

    @Autowired
    private ControlPowerService controlPowerService;

    @Autowired
    private ConfigPower configPower;

    @ApiOperation(value = "添加北望电源设备")
    @PostMapping(value = "/addBwPower")
    public Result<PowerDeviceEntity> addBwPower(@Valid @RequestBody BwPowerDeviceAddVo powerDeviceAddVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.addBwPower(powerDeviceAddVo);
    }

    @ApiOperation(value = "修改北望电源设备")
    @PostMapping(value = "/updateBwPower")
    public Result<PowerDeviceEntity> updateBwPower(@Valid @RequestBody BwPowerDeviceUpdateVo powerDeviceUpdateVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.updateBwPower(powerDeviceUpdateVo);
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

    @ApiOperation(value = "指定对那个ip进行局域网搜索，可不调用此方法走默认配置。默认配置：\n" +
            "ip:255.255.255.255\n" +
            "timeout:3000ms\n" +
            "searchTime:3000ms")
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
    public Result<List<LanDevice>> lanSearch() {
        try {
            List<LanDevice> devices = controlPowerService.searchDevices();
            return Result.succeed(devices);
        } catch (Exception e) {
            log.error("局域网搜索失败：{}", e.getMessage());
            return Result.failed("局域网搜索失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "局域网搜索-根据设备Mac获取电源设备的详细配置")
    @GetMapping(value = "/device/getPowerConfigByMac")
    @ApiImplicitParams({@ApiImplicitParam(name = "mac", required = true, value = "设备Mac地址")})
    public Result<PowerLanConfigVO> getPowerConfigByMac(@RequestParam("mac") String mac) {
        try {
            return controlPowerService.getPowerConfigByMac(mac);
        } catch (Exception e) {
            log.error("获取电源的具体配置失败：{}", e.getMessage());
            return Result.failed("获取电源的具体配置失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "局域网搜索-修改电源设备配置")
    @PostMapping(value = "/device/updatePowerConfigByMac")
    public Result updatePowerConfigByMac(@Valid @RequestBody UpdatePowerLanConfigDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        try {
            return controlPowerService.updatePowerConfigByMac(dto);
        } catch (Exception e) {
            log.error("修改电源配置失败：{}", e.getMessage());
            return Result.failed("修改电源配置失败：" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有设备，填充下拉列表（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/datas")
    public Result<List<PowerDeviceVo>> getDeviceDatas() {
        return controlPowerService.getDeviceDatas();
    }

    /*
     * ================================================电源配置-数据库==============================================================
     */

    @ApiOperation(value = "修改电源数据库配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/update")
    public Result<Integer> portUpdate(@Valid @RequestBody PowerConfigUpdateVo powerConfigUpdateVo, BindingResult br) {
        ValidUtils.paramValid(br);

        return controlPowerService.portUpdate(powerConfigUpdateVo);
    }

    @ApiOperation(value = "查询电源数据库配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/list")
    public Result<List<PowerPortListVo>> portList(@RequestBody PowerConfigListVo powerConfigListVo) {

        return controlPowerService.portList(powerConfigListVo);
    }

    /*
     * ================================================Bwant-IPM-08操作==============================================================
     */

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
