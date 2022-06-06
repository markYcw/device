package com.kedacom.device.api.power.fallback;

import com.kedacom.common.model.Result;
import com.kedacom.device.api.power.ControlPowerApi;
import com.kedacom.power.entity.Device;
import com.kedacom.power.entity.NetDeviceConfig;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author hxj
 * @date 2022/4/13 14:19
 */
public class ControlPowerApiFallBackFactory implements FallbackFactory<ControlPowerApi> {
    @Override
    public ControlPowerApi create(Throwable throwable) {
        return new ControlPowerApi() {
            @Override
            public Result<List<PowerDeviceTypeResponseVo>> getDevType() {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Integer> deviceAdd(@Valid PowerDeviceAddVo powerDeviceAddVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Integer> deviceUpdate(@Valid PowerDeviceUpdateVo powerDeviceUpdateVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Boolean> deviceDelete(@Valid PowerDeviceDeleteVo powerDeviceDeleteVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(@Valid PowerDeviceListVo powerDeviceListVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<List<PowerDeviceListRspVo>> listDevices() {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<PowerDeviceListRspVo> getDeviceById(int id) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public void lanConfig(String ip, Long timeout, Long searchTime) {

            }

            @Override
            public Result<Set<Device>> lanSearch() {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<NetDeviceConfig> getPowerConfig(String macAddr) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Integer> portAdd(@Valid PowerConfigAddVo powerConfigAddVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Integer> portUpdate(@Valid PowerConfigUpdateVo powerConfigUpdateVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Boolean> portDelete(@Valid PowerPortVo powerPortVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<List<PowerPortListVo>> portList(PowerConfigListVo powerConfigListVo) {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<List<PowerDeviceVo>> getDeviceDatas() {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result powerStart(int id) throws IOException {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<PowerDeviceMessageVo> deviceMessage(@Valid PowerDeviceMessageReqVo powerDeviceMessageReqVo) throws IOException {
                return Result.failed(throwable.getMessage());

            }

            @Override
            public Result<List<PowerChannelStateVo>> deviceChannelState(@Valid PowerDeviceMessageReqVo powerDeviceMessageReqVo) throws IOException {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Boolean> deviceTurn(@Valid PowerDeviceTurnVO powerDeviceTurnsVo) throws IOException {
                return null;
            }

            @Override
            public Result<Boolean> deviceTurns(@Valid PowerDeviceTurnsVo powerDeviceTurnsVo) throws IOException {
                return Result.failed(throwable.getMessage());
            }

            @Override
            public Result<Boolean> powerStop() {
                return Result.failed(throwable.getMessage());
            }
        };
    }
}
