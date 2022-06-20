package com.kedacom.device.core.basicParam;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ycw
 * @date: 2022/04/02 14:53
 * @description 新媒体基本参数
 */
@Data
public class NewMediaBasicParam implements Serializable {

    private String url;

    private Map<String, Long> paramMap;

}
