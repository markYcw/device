package com.kedacom.power.utils;

import java.nio.ByteBuffer;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class BufferUtils {

    public static String getMac(ByteBuffer buffer) {
        buffer.position(5);

        byte[] bytes = new byte[6];
        buffer.get(bytes);

        return getMacAddr(bytes);
    }

    /**
     * 转mac地址
     *
     * @param bytes
     * @return
     */
    public static String getMacAddr(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xff);
            builder.append(hexString);
            if (i < bytes.length - 1) {
                builder.append("-");
            }
        }
        return builder.toString();
    }

    /**
     * 十六进制数组转字符串
     *
     * @param bytes
     * @return
     */
    public static String convertHexArrayToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            String hexString = Integer.toHexString(aByte & 0xff);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            builder.append(hexString);
        }
        return builder.toString();
    }

    public static int getInt(byte[] bytes) {
        String s = convertHexArrayToString(bytes);
        return Integer.parseInt(s, 16);
    }

    public static String hexToBin(byte b) {
        String s = Integer.toBinaryString((b & 0xff) + 0x100).substring(1);
        return s;
    }

    public static byte binToHex(String bin) {
        int i = Integer.parseInt(bin, 2);
        return (byte) i;
    }
}
