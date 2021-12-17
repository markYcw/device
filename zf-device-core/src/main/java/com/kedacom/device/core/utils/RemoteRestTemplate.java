package com.kedacom.device.core.utils;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
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
public class RemoteRestTemplate {

    private static final RestTemplate restTemplate;
    private static final HttpHeaders headers;

    static {
//        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//        httpRequestFactory.setConnectionRequestTimeout(15000);
//        httpRequestFactory.setConnectTimeout(15000);
//        httpRequestFactory.setReadTimeout(15000);

        OkHttp3ClientHttpRequestFactory httpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setReadTimeout(40000);
        httpRequestFactory.setWriteTimeout(30000);

        restTemplate = new RestTemplate(httpRequestFactory);
        setRestTemplateEncode(restTemplate);
        //设置请求头发送put请求需要设置请求头 addBy ycw
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * @author ycw
     * 设置请求头
     * @return HttpHeaders
     */
    public HttpHeaders getHttpHeaders(){
        return headers;
    }

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
