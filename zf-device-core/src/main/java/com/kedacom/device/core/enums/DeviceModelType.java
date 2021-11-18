package com.kedacom.device.core.enums;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/18 9:39
 * @description 设备型号枚举类
 */
public enum DeviceModelType {
    /**
     * 监控平台
     */
    CU(0,"CU"),
    /**
     * 会议平台4.7
     */
    MCU(1,"MCU"),

    /**
     * 会议终端3代高清
     */
    MT(0,"MT"),

    /**
     * 会议平台5.0
     */
    MCU5(4,"MCU5"),

    /**
     * GK服务器
     */
    GK(5,"GK"),

    /**
     * 监控平台2.0
     */
    CU2(1,"CU2"),

    /**
     * 存储柜
     */
    CUPBOARD(8,"CUPBOARD"),
    /**
     * Svr
     */
    SVR(10,"SVR"),

    /**
     * SVR_2931
     */
    SVR_NVRV7(30,"SVR_NVRV7"),

    /**
     * SVR_2832
     */
    SVR_2832(2,"SVR_2832"),

    /**
     * 会议终端5.0
     */
    MT5(1,"MT5"),

    /**
     * 录播服务器5.0
     */
    VRS50(14,"VRS50"),

    /**
     * 录播服务器5.1
     */
    VRS51(15,"VRS51"),

    /**
     * 硬盘刻录机SVR-2kb
     */
    VRS2000B(18,"VRS2000B"),

    /**
     * 人脸对比服务
     */
    FACE(20,"FACE"),

    /**
     * RK100
     */
    RK100(22,"RK100"),

    /**
     * UPU
     */
    UPU(23,"UPU"),

    /**
     * NVR设备
     */
    NVR(19,"NVR"),

    /**
     * 软终端（科达天行）
     */
    SKY(1,"SKY"),

    /**
     * 签名板PRO
     */
    E10PRO(31,"E10PRO"),
    ;

    /**
     * type值用于登录时区分哪个协议
     */
    private final Integer code;

    /**
     * 设备型号 每一种设备型号对应一个Type 但是一个Type 可以对应多个设备型号
     */
    private final String modelType;

    DeviceModelType(Integer code, String modelType) {
        this.code = code;
        this.modelType = modelType;
    }

    public Integer getCode() {
        return code;
    }

    public String getModelType() {
        return modelType;
    }

    /**
     * 通过设备型号获得设备所对应的Type
     * @param modelType
     * @return
     */
    public static Integer getEnum(String modelType) {
        for(DeviceModelType anEnum : DeviceModelType.values()){
            if(anEnum.getModelType().equals(modelType)){
                return anEnum.getCode();
            }
        }
        return null;
    }
}
