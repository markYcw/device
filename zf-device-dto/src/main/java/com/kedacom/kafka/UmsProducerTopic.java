package com.kedacom.kafka;

/**
 * 统一设备通知topic
 *
 * @author van.shu
 * @create 2020/9/25 15:08
 */
public interface UmsProducerTopic {

    /**
     * 统一设备状态通知topic
     */
    String DEVICE_STATUS_CHANGE = "zf_ums_topic_AV_DEVICE_STATUS";

    String DEVICE_CHANGE = "zf_ums_topic_AV_DEVICE_CHANGE";

    /**
     * 统一设备子设备添加通知topic
     */
    String DEVICE_ADD = "zf_ums_topic_AV_DEVICE_ADD";


    /**
     * 统一设备子设备删除通知topic
     */
    String DEVICE_DEL = "zf_ums_topic_AV_DEVICE_DELETE";


    /**
     * 统一设备子设备更新通知topic
     */
    String DEVICE_MOD = "zf_ums_topic_AV_DEVICE_UPDATE";


    /**
     * 统一设备分组改变通知topic
     */
    String DEVICE_GROUP_CHANGE = "zf_ums_topic_AV_DEVICE_GROUP_CHANGE";


    /**
     * 统一设备分组添加通知topic
     */
    String DEVICE_GROUP_ADD = "zf_ums_topic_AV_GROUP_ADD";

    /**
     * 统一设备分组删除通知topic
     */
    String DEVICE_GROUP_DEL = "zf_ums_topic_AV_GROUP_DELETE";


    /**
     * 统一设备告警通知topic
     */
    String DEVICE_ALARM = "zf_ums_topic_AV_DEVICE_ALARM";


    /**
     * 统一设备GPS信息topic
     */
    String DEVICE_GPS = "zf_ums_topic_AV_DEVICE_GPS";


    /**
     * 调度组状态信息通知topic
     */
    String DEVICE_SCHEDULE_GROUP_STATUS_NTY = "zf_ums_topic_AV_GROUP_STATUS_NTY";

    /**
     * 分组状态变更通知
     */
    String DEVICE_GROUP_STATUS_NTY = "zf_ums_topic_DEVICE_GROUP_STATUS_NTY";
    /**
     * 设备与分组状态变更通知
     */
    String DEVICE_AND_GROUP_STATUS_NTY = "zf_ums_topic_DEVICE_AND_GROUP_STATUS_NTY";

    /**
     * 发送透明通道数据
     */
    public static final String DEVICE_TRANS_DATA_NOTIFY = "device_trans_data_notify";

}
