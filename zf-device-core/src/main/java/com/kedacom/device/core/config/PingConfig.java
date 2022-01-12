package com.kedacom.device.core.config;

import com.kedacom.device.core.ping.DefaultPing;
import com.kedacom.device.core.ping.IPing;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PingConfig {
    @Bean
    @ConditionalOnMissingBean(IPing.class)
    public IPing defaultPing(){
        return new DefaultPing();
    }
}
