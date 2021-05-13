package com.kedacom.core.config;

import lombok.Data;

/**
 * @author van.shu
 * @date 2021/5/12 7:10
 */
@Data
public class NetworkConfig {

    public String serverIp = "127.0.0.1";

    private int serverPort = 45670;

    private long timeout = 15 * 1000;

}
