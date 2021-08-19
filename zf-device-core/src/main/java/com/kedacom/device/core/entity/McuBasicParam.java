package com.kedacom.device.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author hxj
 * @date: 2021/8/14 14:53
 * @description mcu基本参数
 */
@Data
public class McuBasicParam implements Serializable {

    private String url;

    private Map<String, Long> paramMap;

}
