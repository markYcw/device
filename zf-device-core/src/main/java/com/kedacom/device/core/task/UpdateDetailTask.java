package com.kedacom.device.core.task;

import com.kedacom.device.core.manager.UmsSubDeviceManager;
import com.kedacom.device.core.utils.SpringGetBeanUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一设备插入、更新任务类
 * @author van.shu
 * @create 2020/11/6 10:59
 */
@Slf4j
public class UpdateDetailTask implements Runnable {

    private String umsId;

    private int curPage;

    private int pageSize;

    private UmsSubDeviceManager umsSubDeviceManager;


    public UpdateDetailTask(String umdId, int curPage, int pageSize) {

        this.umsId = umdId;

        this.curPage = curPage;

        this.pageSize = pageSize;

        this.umsSubDeviceManager = getBean(UmsSubDeviceManager.class);

    }


    public <T> T getBean(Class<T> clazz) {

        return SpringGetBeanUtils.getBean(clazz);
    }



    @Override
    public void run() {

        doTask();
    }



    private void doTask() {

        try {

            umsSubDeviceManager.selectAndInsertSubDeviceFromAvFeign(umsId, curPage, pageSize);

//            if (code == Integer.MAX_VALUE) {
//                UmsGroupManager umsGroupManager = getBean(UmsGroupManager.class);
//                log.info("开始获取设备分组信息，统一平台id为[{}]", umsId);
//                Boolean umsGroupResult = umsGroupManager.selectAndInsertUmsGroupEntity(umsId);
//
//                if (!umsGroupResult) {
//                    log.error("远程调用统一设备分组接口失败或者未获取到统一设备分组信息,统一设备Id为:[{}]", umsId);
//                }
//
//                log.info("获取设备分组信息完成。。。统一设备Id为:[{}]", umsId);
//
//            }
        } catch (Exception e) {
            log.error("同步任务执行出错，curPage:{}  e: ",curPage,e);
            e.printStackTrace();
        }

    }
}
