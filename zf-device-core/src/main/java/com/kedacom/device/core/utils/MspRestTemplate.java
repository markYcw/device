package com.kedacom.device.core.utils;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/7/12 14:01
 */
@Component
public class MspRestTemplate {

    private static final RestTemplate restTemplate;

    static {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(15000);
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setReadTimeout(15000);
        restTemplate = new RestTemplate(httpRequestFactory);
        setRestTemplateEncode(restTemplate);
    }

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/decoder/";

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static void setRestTemplateEncode(RestTemplate restTemplate) {
        if (null == restTemplate || ObjectUtil.isEmpty(restTemplate.getMessageConverters())) {
            return;
        }

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }
    }
}