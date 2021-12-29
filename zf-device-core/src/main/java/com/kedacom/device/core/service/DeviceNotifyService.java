package com.kedacom.device.core.service;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/29 9:53
 * @description
 */
public interface DeviceNotifyService {
    /**
     * 处理设备通知
     * @param notify
     */
    void handleNotify(String notify);
}
