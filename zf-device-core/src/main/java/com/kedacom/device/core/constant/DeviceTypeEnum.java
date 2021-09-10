package com.kedacom.device.core.constant;

/**
 * @Author hxj
 * @Date: 2021/8/12 18:30
 * @Description 设备类型枚举值
 */
public enum DeviceTypeEnum {

    /**
     * Svr
     */
    SVR(10),

    /**
     * SVR_2931
     */
    SVR_NVRV7(30),

    // 会议平台4.7 mcu4.7
    MCUFOURSEVEN(1),
    // 会议平台5.0 mcu5.0
    MCUFIVE(4);


    private Integer code;

    public Integer getCode() {
        return code;
    }

    DeviceTypeEnum(Integer code) {
        this.code = code;
    }
}
