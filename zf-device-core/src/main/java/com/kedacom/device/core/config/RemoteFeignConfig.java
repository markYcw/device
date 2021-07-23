package com.kedacom.device.core.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RemoteFeignConfig {

    @Bean
    public ResponseEntityDecoder feignDecoder() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(HttpMessageConfig.stringConverter());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converters);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public SpringEncoder feignEncoder() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(HttpMessageConfig.stringConverter());
        ObjectFactory<HttpMessageConverters> objectFactory = () ->
                new HttpMessageConverters(converters);
        return new SpringEncoder(objectFactory);
    }

}
