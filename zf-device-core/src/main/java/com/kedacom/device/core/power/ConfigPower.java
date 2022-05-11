package com.kedacom.device.core.power;

import com.kedacom.power.common.NetConstant;
import com.kedacom.power.common.RequestType;
import com.kedacom.power.common.ResponseCode;
import com.kedacom.power.entity.*;
import com.kedacom.power.udp.DeviceSearcher;
import com.kedacom.power.udp.ReceiveMessage;
import com.kedacom.power.udp.SendMessage;
import com.kedacom.power.util.DataParser;
import com.kedacom.power.util.RegUtil;
import com.kedacom.power.util.RequestData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Component
public class ConfigPower {

    private ExecutorService executorService = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(512), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public ConfigPower() {
    }

    public void init(String ip, Long timeout, Long searchTime) {
        if (null != ip) {
            NetConstant.SEND_IP = ip;
        }
        if (null != timeout) {
            NetConstant.TIMEOUT = timeout;
        }
        if (null != searchTime) {
            NetConstant.SEARCH_TIME = searchTime;
        }
    }

    private int sendRequest(Future submit, ByteBuffer request) {
        SendMessage sendMessage = new SendMessage(request);
        sendMessage.send();
        //接收消息
        try {
            submit.get(NetConstant.TIMEOUT, TimeUnit.SECONDS);
            return ResponseCode.SUCCESS;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseCode.ERROR;
        } catch (TimeoutException e) {
            submit.cancel(true);
            return ResponseCode.TIMEOUT;
        }
    }

    /**
     * 获取配置
     *
     * @param macAddr
     * @return
     */
    public NetDeviceConfig getConfig(String macAddr) {
        if (RegUtil.checkMacAddr(macAddr)) {
            return null;
        }
        NetDeviceConfig netDeviceConfig = null;
        //启动接收端
        ReceiveMessage receiveMessage = new ReceiveMessage();
        Future submit = executorService.submit(receiveMessage);
        //启动发送端
        ByteBuffer request = RequestData.getRequestData(RequestType.COMMUNICATION_ID, RequestType.GET_CONFIG_TYPE, macAddr, null, null);
        int code = sendRequest(submit, request);
        if (code == ResponseCode.SUCCESS) {
            ByteBuffer buffer = receiveMessage.getBuffer();
            //解析数据
            DataParser dataParser = new DataParser();
            netDeviceConfig = (NetDeviceConfig) dataParser.parseData(buffer);
        }
        return netDeviceConfig;
    }


    /**
     * 恢复出厂设置
     *
     * @param macAddr
     * @return
     */
    public int restore(String macAddr) {
        if (RegUtil.checkMacAddr(macAddr)) {
            return 3;
        }
        //启动接收端
        ReceiveMessage receiveMessage = new ReceiveMessage();
        Future submit = executorService.submit(receiveMessage);
        //启动发送端
        ByteBuffer request = RequestData.getRequestData(RequestType.COMMUNICATION_ID, RequestType.RESTORE_TYPE, macAddr, null, null);
        int code = sendRequest(submit, request);
        if (code == ResponseCode.SUCCESS) {
            return 0;
        } else if (code == ResponseCode.ERROR) {
            return 1;
        }
        return 2;
    }

    public int config(Config config, String macAddr) {
        if (RegUtil.checkMacAddr(macAddr)) {
            return 3;
        }
        NetDeviceConfig netDeviceConfig = null;
        DataParser dataParser = new DataParser();
        //启动接收端
        ReceiveMessage receiveMessage = new ReceiveMessage();
        Future submit = executorService.submit(receiveMessage);
        //启动发送端
        ByteBuffer request = RequestData.getRequestData(RequestType.COMMUNICATION_ID, RequestType.GET_CONFIG_TYPE, macAddr, null, null);
        int code = sendRequest(submit, request);
        if (code == ResponseCode.SUCCESS) {
            ByteBuffer buffer = receiveMessage.getBuffer();
            //解析数据
            netDeviceConfig = (NetDeviceConfig) dataParser.parseData(buffer);
        } else if (code == ResponseCode.ERROR) {
            return 1;
        } else if (code == ResponseCode.TIMEOUT) {
            return 2;
        }
        if (null == netDeviceConfig) {
            return 1;
        }
        DeviceConfig deviceConfig = netDeviceConfig.getDeviceConfig();
        deviceConfig.setComCfgEn("1");
        if (null != config.getDevIp()) {
            deviceConfig.setDevIp(config.getDevIp());
        }
        if (null != config.getDevIpMask()) {
            deviceConfig.setDevIpMask(config.getDevIpMask());
        }
        if (null != config.getDevGwIp()) {
            deviceConfig.setDevGatewayIp(config.getDevGwIp());
        }
        DevicePortConfig devicePortConfig = netDeviceConfig.getDevicePortConfigs().get(1);
        if (null != config.getDesIp()) {
            devicePortConfig.setDesIp(config.getDesIp());
        }
        if (null != config.getDesPort()) {
            devicePortConfig.setDesPort(config.getDesPort());
        }

        submit = executorService.submit(receiveMessage);
        request = RequestData.getRequestData(RequestType.COMMUNICATION_ID, RequestType.CONFIG_TYPE, macAddr, null, netDeviceConfig);
        int i = sendRequest(submit, request);
        if (i == ResponseCode.SUCCESS) {
            ByteBuffer buffer = receiveMessage.getBuffer();
            //解析数据
            netDeviceConfig = (NetDeviceConfig) dataParser.parseData(buffer);
            if (null != netDeviceConfig) {
                return 0;
            } else {
                return 1;
            }
        } else if (i == ResponseCode.ERROR) {
            return 1;
        }
        return 2;
    }

    public Set<Device> searchDevices() throws IOException, InterruptedException {
        DeviceSearcher deviceSearcher = new DeviceSearcher();
        Set<Device> devices = deviceSearcher.searchDevices();
        return devices;
    }

    public void close() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
