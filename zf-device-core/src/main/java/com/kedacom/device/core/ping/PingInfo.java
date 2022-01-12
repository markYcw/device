package com.kedacom.device.core.ping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PingInfo {
    private String ip;
    //ping 次数
    private int times=3;
    //超时时间，单位ms
    private int timeout=5;

    public PingInfo(String ip) {
        this.ip = ip;
    }

}
