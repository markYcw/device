package com.kedacom.power.listener;

import com.kedacom.power.entity.Device;
import com.kedacom.power.util.DataParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class Listener extends Thread {
    private final int listenPort;

    private final CountDownLatch countDownLatch;

    private final Set<Device> deviceSet = new HashSet<>();

    private boolean done = false;

    private DatagramChannel dc = DatagramChannel.open();

    private ByteBuffer buffer = ByteBuffer.allocate(285);

    private int type;

    public Listener(int listenPort, CountDownLatch countDownLatch, int type) throws IOException {
        super();
        this.listenPort = listenPort;
        this.countDownLatch = countDownLatch;
        this.type = type;
    }

    @Override
    public void run() {
        super.run();
        countDownLatch.countDown();
        DataParser dataParser = new DataParser();
        try {
            dc.socket().bind(new InetSocketAddress(listenPort));
            buffer.clear();
            if (0 == type) {
                while (!done) {
                    buffer.clear();
                    dc.receive(buffer);
                    buffer.flip();
                    Device device = (Device) dataParser.parseData(buffer);
                    deviceSet.add(device);
                }
            }
            if (1 == type) {
                dc.receive(buffer);
            }
        } catch (IOException e) {
        } finally {
            close();
        }
    }

    private void close() {
        if (null != dc) {
            try {
                dc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dc = null;
        }
    }

    public Set<Device> getDeviceAndClose() {
        done = true;
        close();
        return deviceSet;
    }

    public ByteBuffer getBuffer() {
        done = true;
        close();
        return buffer;
    }
}
