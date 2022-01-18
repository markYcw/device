package com.kedacom.device.core.notify.mt;

import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/3
 */
@Slf4j
@Component
public class MtNotifyFactory {

    public static void init() {

        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);

        factory.register(DeviceType.MT.getValue(), 1, new MtNotifyHandle());

        factory.register(DeviceType.MT.getValue(), 100, new MtNotifyHandle());

        factory.register(DeviceType.MT5.getValue(), 1, new MtNotifyHandle());

        factory.register(DeviceType.MT5.getValue(), 100, new MtNotifyHandle());

    }

}
