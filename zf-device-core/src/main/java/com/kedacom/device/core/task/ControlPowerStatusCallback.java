package com.kedacom.device.core.task;

import com.alibaba.fastjson.JSON;
import com.kedacom.device.core.service.ControlPowerService;
import com.kedacom.device.core.utils.ContextUtils;
import com.kedacom.power.callback.Callback;
import com.kedacom.power.entity.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ControlPowerStatusCallback
 * @Description 电源事件回调
 * @Author zlf
 * @Date 2021/5/26 14:53
 */
@Slf4j
@Component
public class ControlPowerStatusCallback implements Callback {

    /**
     * 设备上线
     *
     * @param device
     */
    @Override
    public void online(Device device) {
        log.info("电源配置-设备上线：{}", JSON.toJSONString(device));
        ControlPowerService controlPowerService = ContextUtils.getBean(ControlPowerService.class);
        controlPowerService.changePowerDeviceState(device.getMacAddr(), device.getIpAddr(), 1);
    }

    /**
     * 设备离线
     *
     * @param device
     */
    @Override
    public void offline(Device device) {
        log.info("电源配置-设备离线：{}", JSON.toJSONString(device));
        ControlPowerService controlPowerService = ContextUtils.getBean(ControlPowerService.class);
        controlPowerService.changePowerDeviceState(device.getMacAddr(), device.getIpAddr(), 0);
    }

    /**
     * 通道打开
     *
     * @param channels
     */
    @Override
    public void turnon(List<Integer> channels) {
        log.info("电源配置-通道打开：{}", JSON.toJSONString(channels));
    }

    /**
     * 通道关闭
     *
     * @param channels
     */
    @Override
    public void turnoff(List<Integer> channels) {
        log.info("电源配置-通道关闭：{}", JSON.toJSONString(channels));
    }
}
