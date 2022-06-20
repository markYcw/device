package com.kedacom.power.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Builder
@Data
@ApiModel(value = "电源配置")
public class Config {

    private String devIp;

    private String devIpMask;

    private String desIp;

    private Integer desPort;

    private String devGwIp;

}
