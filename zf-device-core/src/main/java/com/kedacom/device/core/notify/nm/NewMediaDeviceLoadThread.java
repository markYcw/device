package com.kedacom.device.core.notify.nm;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.api.WebsocketFeign;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.core.convert.CuConvert;
import com.kedacom.device.core.mapper.CuMapper;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.*;
import com.kedacom.device.core.notify.nm.pojo.*;
import com.kedacom.device.core.service.CuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.newMedia.pojo.NMDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 监控设备加载线程
 *
 * @author ycw
 */
@Slf4j
@Component
public class NewMediaDeviceLoadThread {

    @Autowired
    private CuService cuService;

    @Autowired
    private CuMapper cuMapper;

    @Autowired
    private WebsocketFeign websocketFeign;

    @Autowired
    private RegisterListenerService registerListenerService;

    @Autowired
    private DeviceNotifyUtils notifyUtils;

    @Autowired
    private CuConvert convert;

    /**状态变更类型：设备上下线*/
    public static final int TYPE_DEVICE_STATUS = 1;

    /**状态变更类型：gps信息*/
    public static final int TYPE_GPS = 2;

    /**状态变更类型：设备入网通知*/
    public static final int TYPE_DEVICE_IN = 3;

    /**状态变更类型：设备修改通知*/
    public static final int TYPE_DEVICE_UPDATE = 4;

    /**状态变更类型：设备退网通知*/
    public static final int TYPE_DEVICE_OUT = 5;

    /**自动审核通过*/
    public static final int TYPE_OPERATE_OK = 6;

    /**表示设备与分组关系发生变化*/
    public static final int TYPE_OPERATE_CHANGE = 7;

    private static volatile NewMediaDeviceLoadThread instance;

    private NewMediaDeviceLoadThread(){}

    public static NewMediaDeviceLoadThread getInstance(){
        if (null == instance){
            synchronized (NewMediaDeviceLoadThread.class){
                if(null == instance){
                    instance = new NewMediaDeviceLoadThread();
                }
            }
        }
        return instance;
    }

    /**
     * 收到通知：分组
     *
     * @param notify
     */
    public void onDeviceGroupNotify(NmGetGroupNotify notify) {
        List<NmGroup> groupList = notify.getGroupList();
        NewMediaDeviceCache.getInstance().addDeviceGroups(groupList);
    }

    /**
     * 收到分组修改通知
     *
     * @param notify
     */
    public void onGroupChangeNotify(NmGroupModifyNotify notify) {
        Integer type = notify.getOperateType();
        List<NmGroup> groupList = notify.getGroupList();
        if(type==10){
            NewMediaDeviceCache.getInstance().addDeviceGroups(groupList);
        }else {
            NewMediaDeviceCache.getInstance().deleteDeviceGroups(groupList);
        }
    }

    /**
     * 收到分组和设备关系修改通知
     *
     * @param notify
     */
    public void onDeviceGroupChangeNotify(NmGroupDeviceChangeNotify notify) {
        Integer type = notify.getOperateType();
        if(type == 7){
            log.info("============收到分组设备关系变更通知");
            List<NmGroupDeviceIds> groupList = notify.getGroupList();
            NewMediaDeviceCache.getInstance().deviceGroupChange(groupList);
        }
    }

    /**
     * +
     * 根据分组ID获取分组
     *
     * @param ssid
     * @return
     */
    public NmGroup getPGroupById(Integer ssid, String groupId) {
            return NewMediaDeviceCache.getInstance().getPGroupById(groupId);
    }


    /**
     * 给设备下每一个通道设置设备PuId
     *
     * @param pDevice
     */
    public void setSnPuId(PDevice pDevice) {
        List<SrcChn> srcChns = pDevice.getSrcChns();
        if (CollectionUtil.isNotEmpty(srcChns)) {
            Iterator<SrcChn> iterator = srcChns.iterator();
            while (iterator.hasNext()) {
                SrcChn next = iterator.next();
                next.setPuId(pDevice.getPuId());
            }
        }

    }

    /**
     * 存储新媒体设备
     * @param list
     */
    public void onDevice(List<NMDevice> list){
        log.info("新媒体获取收到设备：onDevice:{}", list);
        NewMediaDeviceCache.getInstance().addDevices(list);
    }

    /**
     * 收到设备状态
     *
     * @param notify
     */
    public void onDeviceStatus(NmDeviceStatusNotify notify) {
        Integer type = notify.getOperateType();
        switch (type) {
            case TYPE_DEVICE_STATUS:
                log.info("===========新媒体设备上下线通知",notify);
                //设备上下线
                NewMediaDeviceCache.getInstance().updateDeviceStatus(notify.getDevList());
                break;

            case TYPE_DEVICE_IN:
                log.info("===========新媒体设备新增通知",notify);
                //设备新增
                NewMediaDeviceCache.getInstance().addDevices(notify.getDevList());
                break;

            case TYPE_DEVICE_UPDATE:
                log.info("===========新媒体设备修改通知",notify);
                //设备修改
                NewMediaDeviceCache.getInstance().updateDevices(notify.getDevList());
                break;

            case TYPE_DEVICE_OUT:
                log.info("===========新媒体设备删除通知",notify);
                //设备删除
                NewMediaDeviceCache.getInstance().removeDevices(notify.getDevList());
                break;

            default:
                break;
        }
    }

    /**
     * 获取数据库ID
     *
     * @param ssid
     */
    private Integer getDbId(Integer ssid) {
        LambdaQueryWrapper<CuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CuEntity::getSsid, ssid);
        List<CuEntity> cuEntities = cuMapper.selectList(wrapper);
        return cuEntities.get(DevTypeConstant.getZero).getId();
    }


    //GPS
    private void onGps(int ssid, String puid, List<Gps> gpsList) {
        if (gpsList == null || gpsList.size() <= 0) {
            return;
        }
    }



}
