package com.kedacom.power.message;

import java.nio.ByteBuffer;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
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
