package com.kedacom.device.core.basicParam;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ycw
 * @date: 2021/09/06 14:53
 * @description cu基本参数
 */
@Data
public class CuBasicParam implements Serializable {

    private String url;

    private Map<String, Long> paramMap;

}
