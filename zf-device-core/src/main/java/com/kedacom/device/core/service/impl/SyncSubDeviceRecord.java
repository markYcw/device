package com.kedacom.device.core.service.impl;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author van.shu
 * @create 2021/3/22 15:41
 */
public class SyncSubDeviceRecord {

    private Map<String, Sync> record = new HashMap<>();

    private SyncSubDeviceRecord() {

    }

    private static SyncSubDeviceRecord INSTANCE = new SyncSubDeviceRecord();

    public static SyncSubDeviceRecord getInstance() {
        return INSTANCE;
    }

    public Map<String, Sync> getRecord() {

        return record;
    }

    @Data
    static class Sync{

        private String umsId;

        //上一次同步时间
        private String lastSyncTime;
    }
}
