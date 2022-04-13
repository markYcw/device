package com.kedacom.power.common;

/**
 * @author gaoteng
 * @version v1.0
 * @date 2021/4/7 16:50
 * @description
 */
public class DataPack {


    public static byte[] token = new byte[]{0x0A, 0x0B};

    public static byte[] voltage = new byte[]{0x00, 0x01};

    public static byte[] temperature = new byte[]{0x00, (byte) 0xC9};

    public static byte[] electricity = new byte[]{0x00, 0x64};

    public static byte[] onOrOff = new byte[]{0x02, 0x2E};

    public static byte[] heart = new byte[]{0x03, 0x6C};

    public static byte[] name = new byte[]{0x05, (byte) 0x99};

    public static byte[] channels = new byte[]{0x02, 0x2C};

    //包头 注意现在包头包尾都是固定的
    public static byte[] header = new byte[]{0x02, 0x01, 0x01, 0x00};

    //包尾
    public static byte[] end = new byte[]{0x01, 0x00, 0x0D, 0x0A};

}
