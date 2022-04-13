package com.kedacom.power.message;

import java.nio.ByteBuffer;

/**
 * @author gaoteng
 * @version v1.0
 * @date 2021/8/16 10:14
 * @description
 */
public class ReceiveParam {
    private String ip;

    private ByteBuffer buffer;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
