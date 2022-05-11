package com.kedacom.power.entity;

import java.util.List;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class NetDeviceConfig {

    private DeviceConfig deviceConfig;

    private List<DevicePortConfig> devicePortConfigs;

    public DeviceConfig getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(DeviceConfig deviceConfig) {
        this.deviceConfig = deviceConfig;
    }

    public List<DevicePortConfig> getDevicePortConfigs() {
        return devicePortConfigs;
    }

    public void setDevicePortConfigs(List<DevicePortConfig> devicePortConfigs) {
        this.devicePortConfigs = devicePortConfigs;
    }

    @Override
    public String toString() {
        return "NetDeviceConfig{" +
                "deviceConfig =" + deviceConfig.toString() +
                ", devicePortConfig0 =" + devicePortConfigs.get(0).toString() +
                ", devicePortConfig1 =" + devicePortConfigs.get(1).toString() +
                '}';
    }
}
