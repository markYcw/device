package com.kedacom.device;

import com.kedacom.common.annotation.ZFApplicationCloud;
import com.kedacom.core.anno.EnableKmProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 15:57
 */
@SpringBootApplication(scanBasePackages = "com.kedacom")
@EnableAsync
@ZFApplicationCloud
@EnableFeignClients(basePackages = {"com.kedacom.device.core.remoteSdk", "com.kedacom.api","com.kedacom.msglog"})
@EnableKmProxy(proxyPackages = "com.kedacom.device", ipAddr = "${zf.kmProxy.server_addr}", notifyPackages = "com.kedacom.device.core.event")
@MapperScan(basePackages = "com.kedacom.device.core.mapper")
@EnableRetry //add by ycw 2021/12/07
public class DeviceServer {
    public static void main(String[] args) {
        SpringApplication.run(DeviceServer.class);
    }

}
