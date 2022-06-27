package com.kedacom.device.api.power.fallback;

import com.kedacom.BaseResult;
import com.kedacom.device.api.power.ControlPowerApi;
import com.kedacom.power.dto.UpdatePowerLanConfigDTO;
import com.kedacom.power.entity.LanDevice;
import com.kedacom.power.entity.PowerDeviceEntity;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;
import feign.hystrix.FallbackFactory;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author hxj
 * @date 2022/4/13 14:19
 */
public class ControlPowerApiFallBackFactory implements FallbackFactory<ControlPowerApi> {
    @Override
    public ControlPowerApi create(Throwable throwable) {
        return new ControlPowerApi() {

            @Override
            public BaseResult<PowerDeviceEntity> addBwPower(BwPowerDeviceAddVo powerDeviceAddVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<PowerDeviceEntity> updateBwPower(BwPowerDeviceUpdateVo powerDeviceUpdateVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> deviceDelete(@Valid PowerDeviceDeleteVo powerDeviceDeleteVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(@Valid PowerDeviceListVo powerDeviceListVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<PowerDeviceListRspVo>> listDevices() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<PowerDeviceListRspVo> getDeviceById(int id) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public void lanConfig(String ip, Long timeout, Long searchTime) {

            }

            @Override
            public BaseResult<List<LanDevice>> lanSearch() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<PowerLanConfigVO> getPowerConfigByMac(String macAddr) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult updatePowerConfigByMac(UpdatePowerLanConfigDTO dto) {
                return BaseResult.failed(throwable.getMessage());
            }


            @Override
            public BaseResult<Integer> portUpdate(@Valid PowerConfigUpdateVo powerConfigUpdateVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> portDelete(@Valid PowerPortVo powerPortVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<PowerPortListVo>> portList(PowerConfigListVo powerConfigListVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<List<PowerDeviceVo>> getDeviceDatas() {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult powerStart(int id) throws IOException {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<PowerDeviceMessageVo> deviceMessage(@Valid PowerDeviceMessageReqVo powerDeviceMessageReqVo) {
                return BaseResult.failed(throwable.getMessage());

            }

            @Override
            public BaseResult<List<PowerChannelStateVo>> deviceChannelState(@Valid PowerDeviceMessageReqVo powerDeviceMessageReqVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> deviceTurn(@Valid PowerDeviceTurnVO powerDeviceTurnsVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> deviceTurns(@Valid PowerDeviceTurnsVo powerDeviceTurnsVo) {
                return BaseResult.failed(throwable.getMessage());
            }

            @Override
            public BaseResult<Boolean> powerStop() {
                return BaseResult.failed(throwable.getMessage());
            }
        };
    }
}
