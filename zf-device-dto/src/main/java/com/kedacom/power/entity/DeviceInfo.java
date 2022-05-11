package com.kedacom.power.entity;

import java.util.Map;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class DeviceInfo extends Device {

    private byte[] id;

    private int voltage;

    private int temperature;

    private Map<Integer, Float> electricityMap;

    private int channels;

    private Map<Integer, Integer> channelStatus;

    private int type;

    private boolean operationResult;

    public boolean isOperationResult() {
        return operationResult;
    }

    public void setOperationResult(boolean operationResult) {
        this.operationResult = operationResult;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Map<Integer, Float> getElectricityMap() {
        return electricityMap;
    }

    public void setElectricityMap(Map<Integer, Float> electricityMap) {
        this.electricityMap = electricityMap;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public Map<Integer, Integer> getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Map<Integer, Integer> channelStatus) {
        this.channelStatus = channelStatus;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "channels=" + channels +
                ", type=" + type +
                '}';
    }
}
