package com.kedacom.power.udp;

import com.kedacom.power.common.NetConstant;
import com.kedacom.power.common.RequestType;
import com.kedacom.power.entity.Device;
import com.kedacom.power.listener.Listener;
import com.kedacom.power.util.RequestData;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class DeviceSearcher {
    /**
     * 监听回复端口
     */
    private static final int LISTEN_PORT = 60000;

    public Set<Device> searchDevices() throws IOException, InterruptedException {
        System.out.println("Search Devices   Start...");
        //开启监听
        Listener listener = listen();
        //发送广播
        sendBroadcast();

        Thread.sleep(NetConstant.SEARCH_TIME);

        Set<Device> devices = listener.getDeviceAndClose();
        return devices;
    }

    private Listener listen() throws InterruptedException, IOException {
        System.out.println("Search Device   Listener start...");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch, 0);
        listener.start();
        countDownLatch.await();

        return listener;
    }

    private void sendBroadcast() {
        System.out.println("Search Device   Send Broadcast start...");
        ByteBuffer requestData = RequestData.getRequestData(RequestType.COMMUNICATION_ID, RequestType.SEARCH_TYPE, null, null, null);
        SendMessage sendMessage = new SendMessage(requestData);
        sendMessage.send();
    }
}
