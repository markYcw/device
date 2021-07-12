package com.kedacom.device.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    }

    @Value("${zf.msp.server_addr}")
    private String mspUrl;

    private static final String mspPath = "/api/v1/manage/decoder/";

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

//    public String mspInterface(String mspPath, String mspRequest, String request) {
//
//        request "1";
//    }


}
