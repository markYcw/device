package com.kedacom.power.callback;

import com.kedacom.power.entity.Device;

import java.util.List;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public interface Callback {
    /**
     * 设备上线
     *
     * @param device
     */
    void online(Device device);

    /**
     * 设备离线
     *
     * @param device
     */
    void offline(Device device);

    /**
     * 通道打开
     *
     * @param channels
     */
    void turnon(List<Integer> channels);

    /**
     * 通道关闭
     *
     * @param channels
     */
    void turnoff(List<Integer> channels);
}
