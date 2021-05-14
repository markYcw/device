package com.kedacom.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 网络配置类
 * @author van.shu
 * @date 2021/5/12 7:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NetworkConfig {
    /**
     * 服务端IP
     */
    public String serverIp = "127.0.0.1";

    /**
     * 服务端 PORT
     */
    private int serverPort = 45670;

    /**
     * 默认的请求-响应 超时时长 15s
     */
    private long timeout = 15 * 1000;

}
