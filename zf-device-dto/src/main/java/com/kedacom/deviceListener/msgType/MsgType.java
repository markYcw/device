package com.kedacom.deviceListener.msgType;
/**
 * @ClassName MsgType
 * @Description 消息通知枚举类
 * @Author ycw
 * @Date 2021/6/19 16:28
 */
public enum MsgType {
    /**
     * 设备通知
     */
    PUT_PICTURE(1,"签名图片通知"),
    MT_OFF_LINE(2,"终端掉线通知"),
    CU_OFF_LINE(3,"监控平台掉线通知"),
    MCU_OFF_LINE(4,"视讯平台掉线通知"),
    SVR_OFF_LINE(5,"SVR掉线通知"),
    UPU_OFF_LINE(6,"UPU掉线通知"),
    VRS_OFF_LINE(7,"VRS掉线通知"),
    MT_Robbed(8,"终端抢占通知"),
    E_PRO_OFF_LINE(9,"EPro掉线通知"),
    E_PRO_FINGER_PRINT(10,"EPro指纹图片通知"),
    E_PRO_SING_PICTURE(11,"EPro签名图片通知"),
    E_PRO_SING_PDF(12,"EPro签名文件通知"),
    E_PRO_RECORD(13,"EPro录像文件通知"),
    S_M_AUDIO_ACT_NTY(14,"流媒体接收音频功率通知"),
    S_M_BURN_STATE_NTY(15,"流媒体刻录状态通知"),
    S_M_ALARM_NTY(16,"异常告警通知"),

    ;

    private int type;
    private String name;

    MsgType(int type, String name) {
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

    public static String valueOf(int type) {
        MsgType[] values = MsgType.values();
        for (MsgType value : values) {
            if (value.getType() == type) {
                return value.getName();
            }
        }
        return null;
    }
}