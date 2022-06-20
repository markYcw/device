package com.kedacom.power.message;

import com.kedacom.power.entity.DeviceInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class MessageCache {

    private MessageCache(){};

    private static volatile MessageCache MESSAGE_CACHE = new MessageCache();

    public static MessageCache getInstance() {
        return MESSAGE_CACHE;
    }


    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    private Map<String , DeviceInfo> map = new HashMap<>(8);
    private Map<String , String> macMap = new HashMap<>(8);
    private Map<String , Boolean> operationMap = new HashMap<>(8);

    public DeviceInfo getDevice(String mac) {
        readLock.lock();

        try {
            return map.get(mac);
        } finally {
            readLock.unlock();
        }
    }

    public void setDevice(String mac, DeviceInfo deviceInfo) {
        writeLock.lock();

        try {
            map.put(mac, deviceInfo);
        } finally {
            writeLock.unlock();
        }
    }

    public String getMac(String ip) {
        readLock.lock();

        try {
            return macMap.get(ip);
        } finally {
            readLock.unlock();
        }
    }

    public void setMac(String ip, String mac) {
        writeLock.lock();

        try {
            macMap.put(ip, mac);
        } finally {
            writeLock.unlock();
        }
    }

    public Boolean getOperation(String mac) {
        readLock.lock();
        try {
            return operationMap.get(mac);
        } finally {
            readLock.unlock();
        }
    }

    public void setOperation(String mac, Boolean operation) {
        writeLock.lock();

        try {
            operationMap.put(mac, operation);
        } finally {
            writeLock.unlock();
        }
    }

    public void clearOperation(String mac) {
        writeLock.lock();

        try {
            operationMap.remove(mac);
        } finally {
            writeLock.unlock();
        }
    }

    public void clear(String mac) {
        writeLock.lock();

        try {
            map.remove(mac);
        } finally {
            writeLock.unlock();
        }
    }

    public void clearMac(String ip) {
        writeLock.lock();
        try {
            macMap.remove(ip);
        } finally {
            writeLock.unlock();
        }
    }

    public void clearAll() {
        writeLock.lock();
        try {
            map = new HashMap<>(8);
            macMap = new HashMap<>(8);
            operationMap = new HashMap<>(8);
        } finally {
            writeLock.unlock();
        }
    }

}
