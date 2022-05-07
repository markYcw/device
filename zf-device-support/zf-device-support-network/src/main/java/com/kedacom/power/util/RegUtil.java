package com.kedacom.power.util;

import com.kedacom.power.common.RegConstant;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class RegUtil {
    public static boolean checkMacAddr(String macAddr) {
        if (null == macAddr || "".equals(macAddr)) {
            return true;
        }
        //校验mac地址
        return !Pattern.compile(RegConstant.MAC_REG).matcher(macAddr).find();
    }

    public static boolean checkStateMap(Map<Integer, Integer> map) {
        if (null == map || map.size() == 0) {
            return true;
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            if (key > 8 || key < 1) {
                return true;
            }
            Integer value = entry.getValue();
            if (value != 0 && value != 1) {
                return true;
            }
        }
        return false;
    }
}
