package com.kedacom.power.udp;

import com.kedacom.power.common.NetConstant;
import com.kedacom.power.common.Numbers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class ReceiveMessage extends Thread {

    private ByteBuffer buffer = ByteBuffer.allocate(Numbers.CONSTANT_285);
    private DatagramChannel channel;

    @Override
    public void run() {
        try {
            if (null == channel) {
                channel = DatagramChannel.open();
                channel.socket().bind(new InetSocketAddress(NetConstant.RECEIVE_PORT));
            }
            buffer.clear();
            channel.receive(buffer);
            buffer.flip();
        } catch (IOException e) {
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public void setChannel(DatagramChannel channel) {
        this.channel = channel;
    }
}
