package com.kedacom.device.core.utils;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author hexijian
 * @date: 2021/7/12 14:01
 */
@Configuration
public class RemoteRestTemplate {

    private static RestTemplate restTemplate;

    private static HttpHeaders headers;

    @Value("${zf.restTemplate.timeout:60000}")
    private String timeout;

    /**
     * @return HttpHeaders
     * @author ycw
     * 设置请求头
     */
    @Bean
    public HttpHeaders getHttpHeaders() {
        //设置请求头发送put请求需要设置请求头 addBy ycw
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        OkHttp3ClientHttpRequestFactory httpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setReadTimeout(Integer.parseInt(timeout));
        httpRequestFactory.setWriteTimeout(Integer.parseInt(timeout));
        restTemplate = new RestTemplate(httpRequestFactory);
        setRestTemplateEncode(restTemplate);
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
