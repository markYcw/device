package com.kedacom.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.kedacom.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: hxj
 * @Date: 2021/6/1 13:49
 */
@Slf4j
public class DeviceStatusListenerManager {

    private static volatile DeviceStatusListenerManager INSTANCE;

    private static Map<String, Set<String>> map = new ConcurrentHashMap<String, Set<String>>();

    private static RestTemplate restTemplate;

    static {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(15000);
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setReadTimeout(15000);
        restTemplate = new RestTemplate(httpRequestFactory);
    }

    public static DeviceStatusListenerManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DeviceStatusListenerManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeviceStatusListenerManager();
                }
            }
        }
        return INSTANCE;
    }


    public void register(AcceptUrlListen listener) {
        log.info("DeviceStatusListenerManager register:{}", listener);
        Set<String> urls = map.get(listener.getTopic());
        if (CollectionUtil.isNotEmpty(urls)) {
            urls.add(listener.getAcceptUrl());
            map.put(listener.getTopic(), urls);
        } else {
            Set<String> urlList = new HashSet<>();
            urlList.add(listener.getAcceptUrl());
            map.put(listener.getTopic(), urlList);
        }

    }

    public void publish(String deviceStatus) {
        log.info("DeviceStatusListenerManager publish deviceStatus:{}", deviceStatus);
        ThreadPoolUtil.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (MapUtil.isNotEmpty(map)) {
                        map.keySet().stream().forEach(key -> map.get(key).stream().forEach(url -> restTemplate.postForObject(url, deviceStatus, null)));
                    }
                } catch (Exception e) {
                    log.error("DeviceStatusListenerManager publish failed,devicestatus:{},error:{}", deviceStatus, e.getMessage());
                }
            }
        });

    }
}