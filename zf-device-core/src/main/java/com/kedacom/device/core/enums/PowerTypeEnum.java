package com.kedacom.device.core.enums;

/**
 * @ClassName PowerTypeEnum
 * @Description 电源设备类型枚举类
 * @Author zlf
 * @Date 2021/6/10 18:28
 */
public enum PowerTypeEnum {

    RK100(1, "RK100"),
    BWANT_IPM_08(2, "Bwant-IPM-08");

    private int type;
    private String name;

    PowerTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PowerTypeEnum valueOf(int type) {
        PowerTypeEnum[] values = PowerTypeEnum.values();
        for (PowerTypeEnum value : values) {
            if (value.getType() == type) {
                return value;
            }
        }
        return null;
    }
}
