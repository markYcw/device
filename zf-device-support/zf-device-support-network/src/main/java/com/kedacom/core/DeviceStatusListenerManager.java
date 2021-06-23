package com.kedacom.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.ums.entity.AcceptUrlListen;
import com.kedacom.ums.entity.UmsSubDeviceStatusModel;
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

    public void publish(UmsSubDeviceStatusModel model) {
        DeviceStatusTask publishTask = new DeviceStatusTask(model);
        ThreadPoolUtil.getInstance().submit(publishTask);
    }

    static class DeviceStatusTask implements Runnable {

        private UmsSubDeviceStatusModel model;

        public DeviceStatusTask(UmsSubDeviceStatusModel model) {
            this.model = model;
        }

        @Override
        public void run() {
            try {
                //      String deviceStatus = JSON.toJSONString(model);
                if (MapUtil.isNotEmpty(map)) {
                    //           map.keySet().stream().distinct().forEach(key -> map.get(key).stream().distinct().forEach(url -> restTemplate.postForObject(url, deviceStatus, String.class)));
                    Set<String> keySet = map.keySet();
                    for (String key : keySet) {
                        Set<String> urls = map.get(key);
                        log.info("设备状态变更restTemplate类型时间通知--->key:{},url:{},deviceStatus:{}", key, urls, JSONObject.toJSONString(model));
                        for (String url : urls) {
                            //1、 url:http://127.0.0.1:10090/demo/deviceStatus     @RequestBody:UmsSubDeviceStatusModel
                            restTemplate.postForObject(url, model, String.class);
                            //2、 url:http://127.0.0.1:10090/demo/deviceStatus?deviceStatus={deviceStatus}  @RequestParam String deviceStatus
                            // restTemplate.postForObject(url, null, String.class, deviceStatus);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("DeviceStatusListenerManager publish failed,deviceStatus:{},error:{}", JSONObject.toJSONString(model), e.getMessage());
            }
        }
    }
}

