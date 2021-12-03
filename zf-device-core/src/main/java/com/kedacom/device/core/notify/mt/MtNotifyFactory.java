package com.kedacom.device.core.notify.mt;

import com.kedacom.device.core.notify.cu.OffLineNotify;
import com.kedacom.device.core.notify.stragegy.DeviceType;
import com.kedacom.device.core.notify.stragegy.NotifyFactory;
import com.kedacom.device.core.utils.ContextUtils;
import org.springframework.stereotype.Component;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/3
 */
@Component
public class MtNotifyFactory {

    public static void init() {

        NotifyFactory factory = ContextUtils.getBean(NotifyFactory.class);

        factory.register(DeviceType.MT.getValue(), 1, new OffLineNotify());

        factory.register(DeviceType.MT.getValue(), 100, new MtSeizeNotify());

        factory.register(DeviceType.MT5.getValue(), 1, new OffLineNotify());

        factory.register(DeviceType.MT5.getValue(), 100, new MtSeizeNotify());

    }

}
