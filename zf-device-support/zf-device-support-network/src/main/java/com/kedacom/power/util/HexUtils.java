package com.kedacom.power.util;

import com.kedacom.power.common.NetConstant;
import com.kedacom.power.common.Numbers;

import java.math.BigInteger;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class HexUtils {

    /**
     * 将16进制的字符串转成字符数组
     *
     * @param str
     * @return
     */
    public static byte[] getHexBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / Numbers.CONSTANT_2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * 0x01 => 01
     *
     * @param str
     * @return
     */
    public static byte stringToByte(String str) {
        String[] xes = str.split("x");
        return (byte) Integer.parseInt(xes[xes.length - 1], 16);
    }

    /**
     * 解析16进制字符串
     *
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);
        }
        return sb.toString();
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

    /**
     * 直接解析十六进制数组
     *
     * @param bytes
     * @return
     */
    public static String parseByteArray(byte[] bytes) {
        String hexString = convertHexArrayToString(bytes);
        return convertHexToString(hexString);
    }

    public static int getInt(byte[] bytes) {
        String s = convertHexArrayToString(bytes);
        return Integer.parseInt(s, 16);
    }

    public static int getFourByteInt(byte[] bytes) {
        for (int i = 0; i < 2; i++) {
            byte temp = bytes[i];
            bytes[i] = bytes[3 - i];
            bytes[3 - i] = temp;
        }
        return getInt(bytes);
    }

    public static int getTwoByteInt(byte[] bytes) {
        byte temp = bytes[0];
        bytes[0] = bytes[1];
        bytes[1] = temp;
        return getInt(bytes);
    }

    /**
     * 16进制转10进制
     *
     * @param b
     * @return
     */
    public static Integer convertHexToTen(byte b) {
        String s = Integer.toHexString(b & 0xff);
        BigInteger bigInteger = new BigInteger(s, 16);
        return bigInteger.intValue();
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
                builder.append(NetConstant.MAC_SPLIT);
            }
        }
        return builder.toString();
    }

    /**
     * 转ip
     *
     * @param bytes
     * @return
     */
    public static String getIp(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            Integer integer = HexUtils.convertHexToTen(bytes[i]);
            builder.append(integer);
            if (i < bytes.length - 1) {
                builder.append(".");
            }
        }
        return builder.toString();
    }

    /**
     * byte转short
     *
     * @param bytes
     * @return
     */
    public static short getShort(byte[] bytes) {
//        return (short)((bytes[0] & 0xff) | (bytes[1] & 0xff) << 8);
        return (short) ((bytes[0] & 0xff) | (bytes[1] << 8) & 0xff);
    }

    public static byte[] shortToByte(int s) {
        byte[] targets = new byte[2];
        targets[1] = (byte) (s >> 8 & 0xff);
        targets[0] = (byte) (s & 0xff);
        return targets;
    }

    /**
     * mac地址转16进制
     *
     * @param mac
     * @return
     */
    public static byte[] macToByte(String mac) {
        String[] macs = mac.split(NetConstant.MAC_SPLIT);
        byte[] bytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) Integer.parseInt(macs[i], 16);
        }
        return bytes;
    }

    /**
     * ip转16进制
     *
     * @param ip
     * @return
     */
    public static byte[] ipToByte(String ip) {
        String[] ips = ip.split("\\.");
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(ips[i]);
        }
        return bytes;
    }

    public static String byteToStr(byte b) {
        String s = Integer.toHexString(b & 0xff);
        if (s.length() < 2) {
            s = "0" + s;
        }
        return "0x" + s;
    }

    public static byte[] intToByte(int num) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(num));
        int length = builder.length();
        if (length < 8) {
            for (int i = 0; i < 8 - length; i++) {
                builder.insert(0, "0");
            }
        }

        String s = builder.toString();
        byte[] bytes = new byte[4];
        for (int i = s.length() - 2; i >= 0; i -= 2) {
            String substring = s.substring(i, i + 2);
            bytes[3 - i / 2] = (byte) Integer.parseInt(substring, 16);
        }
        return bytes;
    }

    public static byte[] intToTwoByte(int num) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(num));
        int length = builder.length();
        if (length < 4) {
            for (int i = 0; i < 4 - length; i++) {
                builder.insert(0, "0");
            }
        }
        String s = builder.toString();
        byte[] bytes = new byte[2];
        bytes[0] = (byte) Integer.parseInt(s.substring(2, 4), 16);
        bytes[1] = (byte) Integer.parseInt(s.substring(0, 2), 16);
        return bytes;
    }

    public static String hexToBin(byte b) {
        String s = Integer.toBinaryString((b & 0xff) + 0x100).substring(1);
        return s;
    }

    public static byte binToHex(String bin) {
        int i = Integer.parseInt(bin, 2);
        return (byte) i;
    }

    public static void main(String[] args) {
        byte b = binToHex("01");
        System.out.println("b = " + b);
    }
}