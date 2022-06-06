package com.kedacom.device.api.power;

import com.kedacom.common.model.Result;
import com.kedacom.device.api.power.fallback.ControlPowerApiFallBackFactory;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.NetDeviceConfig;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author hxjf
 * @date 2022/4/13 14:17
 */
@FeignClient(value = "device-server", contextId = "controlPowerApi", path = "/api-device/power",
        fallbackFactory = ControlPowerApiFallBackFactory.class)
public interface ControlPowerApi {

    @ApiOperation("获取电源支持的类型")
    @GetMapping("getDevType")
    Result<List<PowerDeviceTypeResponseVo>> getDevType();

    @ApiOperation(value = "添加电源设备")
    @PostMapping(value = "/device/add")
    Result<Integer> deviceAdd(@Valid @RequestBody PowerDeviceAddVo powerDeviceAddVo);

    @ApiOperation(value = "修改电源设备")
    @PostMapping(value = "/device/update")
    Result<Integer> deviceUpdate(@Valid @RequestBody PowerDeviceUpdateVo powerDeviceUpdateVo);

    @ApiOperation(value = "删除电源设备")
    @PostMapping(value = "/device/delete")
    Result<Boolean> deviceDelete(@Valid @RequestBody PowerDeviceDeleteVo powerDeviceDeleteVo);

    @ApiOperation(value = "分页条件查询电源设备")
    @PostMapping(value = "/device/list")
    Result<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(@Valid @RequestBody PowerDeviceListVo powerDeviceListVo);

    @ApiOperation(value = "获取所有设备")
    @GetMapping(value = "/list/device")
    Result<List<PowerDeviceListRspVo>> listDevices();

    @ApiOperation(value = "根据Id获取设备")
    @GetMapping(value = "/list/getDeviceById")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "设备数据库Id")})
    Result<PowerDeviceListRspVo> getDeviceById(@RequestParam(value = "id") int id);

    @ApiOperation(value = "局域网配置")
    @GetMapping(value = "/device/lanConfig")
    void lanConfig(@RequestParam("ip") String ip,
                   @RequestParam("timeout") Long timeout,
                   @RequestParam("searchTime") Long searchTime);

    @ApiOperation(value = "局域网搜索")
    @GetMapping(value = "/device/lanSearch")
    Result<Set<Device>> lanSearch();

    @ApiOperation(value = "局域网搜索-根据设备Mac获取电源的详细配置")
    @GetMapping(value = "/device/getPowerConfig")
    Result<NetDeviceConfig> getPowerConfig(@RequestParam("macAddr") String macAddr);
    /*
     * ================================================Bwant-IPM-08操作==============================================================
     */

    @ApiOperation(value = "添加电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/add")
    Result<Integer> portAdd(@Valid @RequestBody PowerConfigAddVo powerConfigAddVo);

    @ApiOperation(value = "修改电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/update")
    Result<Integer> portUpdate(@Valid @RequestBody PowerConfigUpdateVo powerConfigUpdateVo);

    @ApiOperation(value = "删除电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/delete")
    Result<Boolean> portDelete(@Valid @RequestBody PowerPortVo powerPortVo);

    @ApiOperation(value = "查询电源配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/list")
    Result<List<PowerPortListVo>> portList(@RequestBody PowerConfigListVo powerConfigListVo);

    @ApiOperation(value = "获取所有设备，填充下拉列表（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/datas")
    Result<List<PowerDeviceVo>> getDeviceDatas();

    @ApiOperation(value = "启动TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/start")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "电源配置数据库Id")})
    Result powerStart(@RequestParam(value = "id") int id) throws IOException;

    @ApiOperation(value = "获取设备详细信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/msg")
    Result<PowerDeviceMessageVo> deviceMessage(@Valid @RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo) throws IOException;

    @ApiOperation(value = "获取设备下通道开关状态（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/channel/state")
    Result<List<PowerChannelStateVo>> deviceChannelState(@Valid @RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo) throws IOException;

    @ApiOperation(value = "单个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turn")
    Result<Boolean> deviceTurn(@Valid @RequestBody PowerDeviceTurnVO powerDeviceTurnsVo) throws IOException;

    @ApiOperation(value = "多个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turns")
    Result<Boolean> deviceTurns(@Valid @RequestBody PowerDeviceTurnsVo powerDeviceTurnsVo) throws IOException;

    @ApiOperation(value = "关闭TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/stop")
    Result<Boolean> powerStop();


}
