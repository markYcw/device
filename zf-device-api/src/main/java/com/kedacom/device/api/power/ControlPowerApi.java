package com.kedacom.device.api.power;

import com.kedacom.common.model.Result;
import com.kedacom.device.api.power.fallback.ControlPowerApiFallBackFactory;
import com.kedacom.power.dto.UpdatePowerLanConfigDTO;
import com.kedacom.power.entity.LanDevice;
import com.kedacom.power.entity.PowerDeviceEntity;
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

import java.io.IOException;
import java.util.List;

/**
 * @author hxj
 * @date 2022/4/13 14:17
 */
@FeignClient(value = "device-server", contextId = "controlPowerApi", path = "/api-device/power",
        fallbackFactory = ControlPowerApiFallBackFactory.class)
public interface ControlPowerApi {

    @ApiOperation(value = "添加北望电源设备")
    @PostMapping(value = "/addBwPower")
    Result<PowerDeviceEntity> addBwPower(@RequestBody BwPowerDeviceAddVo powerDeviceAddVo);

    @ApiOperation(value = "修改北望电源设备")
    @PostMapping(value = "/updateBwPower")
    Result<PowerDeviceEntity> updateBwPower(@RequestBody BwPowerDeviceUpdateVo powerDeviceUpdateVo);

    @ApiOperation(value = "删除电源设备")
    @PostMapping(value = "/device/delete")
    Result<Boolean> deviceDelete(@RequestBody PowerDeviceDeleteVo powerDeviceDeleteVo);

    @ApiOperation(value = "分页条件查询电源设备")
    @PostMapping(value = "/device/list")
    Result<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(@RequestBody PowerDeviceListVo powerDeviceListVo);

    @ApiOperation(value = "获取所有设备")
    @GetMapping(value = "/list/device")
    Result<List<PowerDeviceListRspVo>> listDevices();

    @ApiOperation(value = "根据Id获取设备")
    @GetMapping(value = "/list/getDeviceById")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "设备数据库Id")})
    Result<PowerDeviceListRspVo> getDeviceById(@RequestParam(value = "id") int id);

    @ApiOperation(value = "指定对那个ip进行局域网搜索，可不调用此方法走默认配置。默认配置：\n" +
            "ip:255.255.255.255\n" +
            "timeout:3000ms\n" +
            "searchTime:3000ms")
    @GetMapping(value = "/device/lanConfig")
    @ApiImplicitParams({@ApiImplicitParam(name = "ip", required = false, value = "广播搜索的服务器ip")
            , @ApiImplicitParam(name = "timeout", required = false, value = "超时时间")
            , @ApiImplicitParam(name = "searchTime", required = false, value = "广播搜索时间")})
    void lanConfig(@RequestParam("ip") String ip,
                   @RequestParam("timeout") Long timeout,
                   @RequestParam("searchTime") Long searchTime);

    @ApiOperation(value = "局域网搜索")
    @GetMapping(value = "/device/lanSearch")
    Result<List<LanDevice>> lanSearch();

    @ApiOperation(value = "局域网搜索-根据设备Mac获取电源设备的详细配置")
    @GetMapping(value = "/device/getPowerConfigByMac")
    @ApiImplicitParams({@ApiImplicitParam(name = "mac", required = true, value = "设备Mac地址")})
    Result<PowerLanConfigVO> getPowerConfigByMac(@RequestParam("mac") String mac);

    @ApiOperation(value = "局域网搜索-修改电源设备配置")
    @PostMapping(value = "/device/updatePowerConfigByMac")
    Result updatePowerConfigByMac(@RequestBody UpdatePowerLanConfigDTO dto);

    @ApiOperation(value = "获取所有设备，填充下拉列表（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/datas")
    Result<List<PowerDeviceVo>> getDeviceDatas();

    /*
     * ================================================电源配置-数据库==============================================================
     */

    @ApiOperation(value = "修改电源数据库配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/update")
    Result<Integer> portUpdate(@RequestBody PowerConfigUpdateVo powerConfigUpdateVo);

    @ApiOperation(value = "删除电源数据库配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/delete")
    Result<Boolean> portDelete(@RequestBody PowerPortVo powerPortVo);

    @ApiOperation(value = "查询电源数据库配置信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/port/list")
    Result<List<PowerPortListVo>> portList(@RequestBody PowerConfigListVo powerConfigListVo);

    /*
     * ================================================Bwant-IPM-08操作==============================================================
     */

    @ApiOperation(value = "启动TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/start")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true, value = "电源配置数据库Id")})
    Result powerStart(@RequestParam(value = "id") int id) throws IOException;

    @ApiOperation(value = "获取设备详细信息（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/msg")
    Result<PowerDeviceMessageVo> deviceMessage(@RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo);

    @ApiOperation(value = "获取设备下通道开关状态（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/channel/state")
    Result<List<PowerChannelStateVo>> deviceChannelState(@RequestBody PowerDeviceMessageReqVo powerDeviceMessageReqVo);

    @ApiOperation(value = "单个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turn")
    Result<Boolean> deviceTurn(@RequestBody PowerDeviceTurnVO vo);

    @ApiOperation(value = "单个电源多个通道开关（针对Bwant-IPM-08）")
    @PostMapping(value = "/device/turns")
    Result<Boolean> deviceTurns(@RequestBody PowerDeviceTurnsVo powerDeviceTurnsVo);

    @ApiOperation(value = "关闭TCP连接（针对Bwant-IPM-08）")
    @GetMapping(value = "/stop")
    Result<Boolean> powerStop();
}
