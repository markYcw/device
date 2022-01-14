package com.kedacom.device.core.notify.cu;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.core.notify.cu.loadGroup.CuDeviceLoadThread;
import com.kedacom.device.core.notify.cu.loadGroup.notify.DeviceNotify;
import com.kedacom.device.core.notify.cu.loadGroup.notify.DeviceStatusNotify;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.GetDeviceNotify;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.GetDeviceStatusNotify;
import com.kedacom.device.core.notify.stragegy.INotify;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/3 15:06
 * @description 设备订阅通知
 */
@Slf4j
public class DevicesStatusSubscribeNotify extends INotify {

    @Override
    protected void consumeMessage(Integer ssid, String message) {
        DeviceStatusNotify deviceNotify = JSON.parseObject(message, DeviceStatusNotify.class);
        CuService service = ContextUtils.getBean(CuService.class);
        CuEntity entity = service.getBySsid(ssid);
        if(ObjectUtil.isNull(entity)){
            return;
        }
        GetDeviceStatusNotify content = deviceNotify.getContent();
        content.setSsid(deviceNotify.getSsid());
        content.setSsno(deviceNotify.getSsno());
        CuDeviceLoadThread cuDeviceLoadThread = ContextUtils.getBean(CuDeviceLoadThread.class);
        cuDeviceLoadThread.onDeviceStatus(content);
        log.info("加载设备ID为"+content.getPuId()+"的设备状态");
    }
}
