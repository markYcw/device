package com.kedacom.device;

import com.kedacom.core.anno.EnableKmProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 15:57
 */
@SpringBootApplication(scanBasePackages = "com.kedacom")
@EnableFeignClients(basePackages = "com.kedacom.device.core.msp")
@EnableKmProxy(proxyPackages = "com.kedacom.device",ipAddr = "172.16.129.214:45670")
public class DeviceServer {
    public static void main(String[] args) {
        SpringApplication.run(DeviceServer.class);
    }
}
