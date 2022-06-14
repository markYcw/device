package com.kedacom.power.udp;

import com.kedacom.power.common.NetConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class SendMessage {

    private ByteBuffer buffer;

    public SendMessage(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public void send() {
        try {
            DatagramChannel channel = DatagramChannel.open();
            // Linux下UDP广播java.net.SocketException: Permission denied，打开客户端将数据报发送到特定的广播地址
            channel.socket().setBroadcast(true);
            buffer.flip();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(NetConstant.SEND_IP, NetConstant.SEND_PORT);
            for (int i = 0; i < 3; i++) {
                channel.send(buffer, inetSocketAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
