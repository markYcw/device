package com.kedacom.device.api.power;

import com.kedacom.device.api.power.fallback.ControlPowerApiFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author hxj
 * @date 2022/4/13 14:17
 */
@FeignClient(value = "device-server", contextId = "controlPowerApi", path = "/api-device/power",
        fallbackFactory = ControlPowerApiFallBackFactory.class)
public interface ControlPowerApi {


}
