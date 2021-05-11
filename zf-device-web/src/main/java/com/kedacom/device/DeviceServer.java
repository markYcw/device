package com.kedacom.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 15:57
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.kedacom.device.core.msp")
public class DeviceServer {
    public static void main(String[] args) {
        SpringApplication.run(DeviceServer.class);
    }
}
