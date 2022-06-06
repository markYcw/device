package com.kedacom.device;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.kedacom.common.annotation.ZFApplicationCloud;
import com.kedacom.core.anno.EnableKmProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 15:57
 */
@SpringBootApplication(scanBasePackages = "com.kedacom")
@EnableAsync
@ZFApplicationCloud
@EnableFeignClients(basePackages = {"com.kedacom.device.core.remoteSdk", "com.kedacom.api","com.kedacom.msglog","com.kedacom.km.api"})
@EnableKmProxy(proxyPackages = "com.kedacom.device", ipAddr = "${zf.kmProxy.server_addr}", notifyPackages = "com.kedacom.device.core.event")
@MapperScan(basePackages = "com.kedacom.device.core.mapper")
public class DeviceServer {
    public static void main(String[] args) {
        SpringApplication.run(DeviceServer.class);
    }


    /**
     * 使用fastjson作为json转换器
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /**
         * TODO 第一个SerializerFeature.PrettyFormat可以省略，毕竟这会造成额外的内存消耗和流量，第二个是用来指定当属性值为null是是否输出：pro:null
         　　　　　 * SerializerFeature.SkipTransientField
         */
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //3.处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
   /*     //4.解决Long转json精度丢失的问题
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);*/
        //5.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }
}
