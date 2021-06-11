package com.kedacom.device.core.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/11
 */
public class PinYinUtils {

    protected static Logger logger = LoggerFactory.getLogger(PinYinUtils.class);

    /**
     * 获取汉字首字母的方法。如： 张三 --> ZS
     * 说明：暂时解决不了多音字的问题，只能使用取多音字的第一个音的方案
     * @param hanZi 汉子字符串
     * @return 大写汉子首字母; 如果都转换失败,那么返回null
     */
    public static String getHanZiInitials(String hanZi) {
        String result = null;

        try{
            if(null != hanZi && !"".equals(hanZi)) {
                char[] charArray = hanZi.toCharArray();
                StringBuffer sb = new StringBuffer();
                for (char ch : charArray) {
                    // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                    String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                    if(null != stringArray) {
                        sb.append(stringArray[0].charAt(0));
                    }
                }

                if(sb.length() > 0) {
                    result = sb.toString().toUpperCase();
                }
            }
        }catch(Exception e){
            logger.error("getHanziInitials", e);
        }

        return result;
    }

    /**
     * 获取汉字拼音的方法。如： 张三 --> zhangsan
     * 说明：暂时解决不了多音字的问题，只能使用取多音字的第一个音的方案
     * @param hanZi 汉子字符串
     * @return 汉字拼音; 如果都转换失败,那么返回null
     */
    public static String getHanZiPinYin(String hanZi) {
        String result = null;

        try{
            if(null != hanZi && !"".equals(hanZi)) {
                char[] charArray = hanZi.toCharArray();
                StringBuffer sb = new StringBuffer();
                for (char ch : charArray) {
                    // 逐个汉字进行转换， 每个汉字返回值为一个String数组（因为有多音字）
                    String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                    if(null != stringArray) {
                        // 把第几声这个数字给去掉
                        sb.append(stringArray[0].replaceAll("\\d", ""));
                    }
                }

                if(sb.length() > 0) {
                    result = sb.toString();
                }
            }
        }catch(Exception e){
            logger.error("getHanziPinYin", e);
        }

        return result;
    }

    /**
     * 汉字转拼音组合
     * @param name
     * @return
     */
    public static String toPinYin(String name){
        String szm  = PinYinUtils.getHanZiInitials(name);//首字母组合
        String qpy = PinYinUtils.getHanZiPinYin(name);//全拼音

        StringBuffer namePy = new StringBuffer();

        if(StringUtils.isNotEmpty(szm))
            namePy.append(szm.toLowerCase());

        if(StringUtils.isNotEmpty(qpy)){
            namePy.append("&&");
            namePy.append(qpy.toLowerCase());
        }

        if(namePy.length() == 0)
            namePy.append(name);

        return namePy.toString();
    }

    /**
     * 将拼音字符串中大写转成小写
     * @param str
     * @return
     */
    public static String StrToLowerCase(String str){

        StringBuffer stringBuffer = new StringBuffer();
        if(StrUtil.isNotBlank(str)){
            for(int i = 0; i < str.length(); i++){
                char c = str.charAt(i);
                if(Character.isUpperCase(c)){
                    stringBuffer.append(Character.toLowerCase(c));
                } else {
                    stringBuffer.append(c);
                }
            }
        }

        return stringBuffer.toString();
    }

    public static boolean checkString(String str) {

        boolean containHanZi = PinYinUtils.isContainHanZi(str);
        boolean containUpperCase = PinYinUtils.isContainUpperCase(str);
        if (!containHanZi && containUpperCase) {
            return true;
        }
        return false;

    }

    public static boolean isContainUpperCase(String str) {

        if (StrUtil.isBlank(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(Character.isUpperCase(c)){
                return true;
            }
        }
        return false;

    }

    public static boolean isContainHanZi(String str) {

        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        return matcher.find();

    }

    public static String checkHanZi(String str) {

        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        StringBuilder stringBuilder = new StringBuilder();
        if (StrUtil.isBlank(str)) {
            return null;
        }
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            Matcher matcher = pat.matcher(s);
            if (matcher.find()) {
                String pinYin = PinYinUtils.getHanZiPinYin(str.substring(i, i + 1));
                stringBuilder.append(pinYin);
            } else {
                stringBuilder.append(s);
            }
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        System.out.println(PinYinUtils.getHanZiPinYin("鲁-潍坊监狱-8监区1#习艺楼一层西卫生间1"));
        System.out.println(PinYinUtils.checkString("fZJd"));

    }

}
