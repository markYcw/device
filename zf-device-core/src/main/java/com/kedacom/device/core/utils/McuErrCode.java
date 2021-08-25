package com.kedacom.device.core.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mcu错误码
 *
 * @author hxj
 * @date: 2021/8/19 13:34
 * @description mcu错误码
 */
@Component
public class McuErrCode {

    private static Map<Integer, String> errCodeMap = new ConcurrentHashMap<>(64);

    static {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        //处理映射配置信息
        Properties mappingProperties = new Properties();
        InputStream mappingPropertiesInStream = loader.getResourceAsStream("Mcu.properties");
        assert mappingPropertiesInStream != null;
        BufferedReader bf = new BufferedReader(new InputStreamReader(mappingPropertiesInStream));
        try {
            mappingProperties.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mappingPropertiesInStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Enumeration mappingPropertiesEenumeration = mappingProperties.propertyNames();
        while (mappingPropertiesEenumeration.hasMoreElements()) {
            String key = (String) mappingPropertiesEenumeration.nextElement();
            String value = mappingProperties.getProperty(key);
            errCodeMap.put(Integer.valueOf(key), value);
        }

    }

    /**
     * 根据错误码获取错误信息
     *
     * @param errCode
     * @return
     */
    public String matchErrMsg(Integer errCode) {

        return errCodeMap.get(errCode);
    }


}

