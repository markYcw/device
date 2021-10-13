package com.kedacom.device.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ycw
 * @date 2021/10/13 11:13
 */
public class DateUtils {

    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public final static String stringDate = "yyyyMMddHHmmss";

    public final static String defaultDate = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    public static String getDateString(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(stringDate);
        return format.format(date);
    }

    public static String getDefaultDate(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(defaultDate);
        return format.format(date);
    }


}
