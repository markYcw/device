package com.kedacom.device.core.basicParam;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ycw
 * @date: 2021/09/06 14:53
 * @description vs录播服务器基本参数
 */
@Data
public class VsBasicParam implements Serializable {

    private String url;

    private Map<String, Long> paramMap;

}
