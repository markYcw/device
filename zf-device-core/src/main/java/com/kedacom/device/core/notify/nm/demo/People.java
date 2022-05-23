package com.kedacom.device.core.notify.nm.demo;

import lombok.Builder;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/5/20 13:02
 * @description
 */
@Data
@Builder
public class People {

    private Integer age;

    private String name;

    private String company;

    private Integer sn;

    private String school;
}
