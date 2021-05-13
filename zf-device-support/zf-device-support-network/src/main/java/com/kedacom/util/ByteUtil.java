package com.kedacom.util;



/**
 * @author van.shu
 * @create 2021/4/25 17:48
 */
public class ByteUtil {

    private ByteUtil() {

    }


    /**
     * 合并两个字节数组
     * @param b1
     * @param b2
     * @return
     */
    public static byte[] merger(byte[] b1, byte[] b2) {

        byte[] b3 = new byte[b1.length + b2.length];

        System.arraycopy(b1, 0, b3, 0, b1.length);

        System.arraycopy(b2, 0, b3, b1.length, b2.length);

        return b3;

    }

    /**
     * 将int值转成固定长度的字节数组
     * 不足高位补0（size=123 limit=6 result={'0','0','0','1','2','3'}）
     * 超过高位截断（size=123456 limit=3 result={'4','5','6'}）
     * @param value
     * @param limit
     * @return
     */
    public static byte[] int2byteArray(int value, int limit) {

        char[] dstChar = new char[limit];

        for (int i = 0; i < limit; i++) {
            dstChar[i] = '0';
        }

        String valStr = String.valueOf(value);

        char[] valCharArray = valStr.toCharArray();


        int valLength = valCharArray.length;

        int destPos = 0;
        int srcPos = 0;
        int copyBit;
        if (valLength < limit) {
            destPos = limit - valLength;
            copyBit = valLength;
        }else {
            srcPos = valLength - limit;
            copyBit = limit;
        }

        System.arraycopy(valCharArray, srcPos, dstChar, destPos, copyBit);

        String dst = new String(dstChar);

        return dst.getBytes();

    }

    /**
     * 将字节数组转成int值
     * 如[0,0,1,2,3,4]-->1234
     * [666600]-->666600
     * @param bytes
     * @return
     */
    public static int byteArray2Int(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return 0;
        }
        String s = new String(bytes);
        return Integer.parseInt(s);
    }


    public static void main(String[] args) {
//        byte[] merger = merger(new byte[]{}, new byte[]{'1', 'd'});
//
//        String s = new String(merger);
//
//        System.out.println("s = " + s);
//
//        byte[] b1 = int2byteArray(123, 7);
//
//        System.out.println(new String(b1));
//
//        byte[] b2 = int2byteArray(1234567, 3);
//
//        System.out.println(new String(b2));

        byte[] bytes = new byte[]{'0', '0', '3', '0', '2', '1'};

        String str = new String(bytes);

        System.out.println("str = " + str);

        int i = Integer.parseInt(str);

        System.out.println("i = " + i);


    }




}
