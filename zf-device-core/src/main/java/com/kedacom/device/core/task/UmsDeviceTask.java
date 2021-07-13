package com.kedacom.device.core.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.kedacom.BasePage;
import com.kedacom.common.utils.PinYinUtils;
import com.kedacom.common.utils.SpringUtil;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.manager.UmsSubDeviceManager;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.core.service.DeviceManagerService;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.QueryDeviceRequest;
import com.kedacom.device.ums.response.QuerySubDeviceInfoResponse;
import com.kedacom.ums.requestdto.UmsSubDeviceInfoQueryRequestDto;
import com.kedacom.ums.responsedto.UmsSubDeviceInfoQueryResponseDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 统一设备
 *
 * @author van.shu
 * @create 2020/8/26 16:21
 */
@Slf4j
public class UmsDeviceTask implements Runnable {

    private String umsDeviceId;

    //最大重试次数
    private static final Integer MAX_RETRY_TIME = 3;

    //是否应该启用线程分发
    private static boolean shouldDistribute = false;

    //核心线程数
    private static int CORE_SIZE = 0;

    //最大线程数
    private static int MAX_SIZE = 0;

    public UmsDeviceTask(String umsDeviceId) {

        this.umsDeviceId = umsDeviceId;
    }

    /**
     * 心跳状态
     */
    private Boolean keepConnection = true;

    /**
     * 同步状态
     */
    private AtomicBoolean syncStatus = new AtomicBoolean(false);

    //添加任务
    public void addTask() {

    }

    public <T> T getBean(Class<T> clazz) {

        return SpringUtil.getBean(clazz);
    }

    @Override
    public void run() {

        log.info("run...");

        AtomicInteger retryTime = new AtomicInteger(0);

        do {
            process();

        } while (!syncStatus.get() &&
                retryTime.incrementAndGet() < MAX_RETRY_TIME);

        //更新最近一次同步时间
        setSyncThirdTime(umsDeviceId);

    }

    private void process() {

        log.info("开始执行任务.....,统一设备平台Id为:[{}]", umsDeviceId);
        int queryindex = 1;
        int querycount = 100;
        Integer total = queryCountOfSubDeviceFromThird(umsDeviceId, queryindex, querycount);
        if (!keepConnection) {
            log.error("远程服务连接异常.....");
            return;
        }
        if (total == 0) {
            log.error("查询到的子设备数量为0，统一设备Id:[{}]", umsDeviceId);
            return;
        }

        UmsSubDeviceManager umsSubDeviceManager = getBean(UmsSubDeviceManager.class);

        log.info("连接正常,开始查询入库,总数为:[{}]", total);

        //这里有一个非常基础但是容易被忽视的问题:整数相除会造成进度丢失，先转成double类型
        int totalPage = (int) Math.ceil((double) total / querycount);
        setPoolSize(totalPage);
        //将正常状态的数据设置为同步中
//        umsSubDeviceManager.updateUmsSubDeviceMod(UmsMod.NORMAL, UmsMod.UPGRADING);
        log.info("开始更新同步。。。。");
        boolean syncResult;
        if (shouldDistribute) {
            syncResult = distribute(0, querycount, umsSubDeviceManager);
        } else {
            syncResult = doAlone(0, querycount, umsSubDeviceManager);
        }

        //同步完成，判断是否同步正常
        //此时查询自己的数据库，判断当前设备总数是否和远程一致
        BasePage<UmsSubDeviceInfoQueryResponseDto> localResult = getUmsSubDeviceFromLocal(querycount);
        long localTotal = localResult.getTotal();
        log.info("localResult total: {}", localTotal);
        //如果总数一样并且同步过程正常，修改同步状态
        if ((total == localTotal) && syncResult) {
            log.info("---------同步成功---------");
            syncStatus.compareAndSet(false, true);
            SubDeviceMapper subDeviceMapper = getBean(SubDeviceMapper.class);
            LambdaQueryWrapper<SubDeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
            List<SubDeviceInfoEntity> selectList = subDeviceMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(selectList)) {
                log.info("统一设备项目设备同步成功后,设备名称拼音转化");
                for (SubDeviceInfoEntity entity : selectList) {
                    String name = entity.getName();
                    String hanZiPinYin = PinYinUtils.getHanZiPinYin(name);
                    String hanZiInitial = PinYinUtils.getHanZiInitials(name);
                    String lowerCase = PinYinUtils.StrToLowerCase(hanZiInitial);
                    entity.setPinyin(hanZiPinYin + "&&" + lowerCase);
                    entity.setUpdateTime(new Date());
                    subDeviceMapper.updateById(entity);
                }
                log.info("设备名称拼音转化完成");
            }
        }
    }

    //任务分发
    private Boolean distribute(int totalPage, int pageSize, UmsSubDeviceManager umsSubDeviceManager) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("ums-distribute-pool-%d")
                .setUncaughtExceptionHandler(new UmsSyncUncaughtExceptionHandler())
                .build();

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                CORE_SIZE,
                MAX_SIZE,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                namedThreadFactory
        );

        //期望完成的任务数量
        long expectedCompletedTaskCount = totalPage + 1;

        monitor(executorService);

        for (int i = 1; i <= totalPage + 1; i++) {

            try {
                executorService.execute(new UpdateDetailTask(umsDeviceId, i, pageSize));
            } catch (Exception ex) {
                log.error("统一设备[{}]同步任务执行第[{}]页出错 ", umsDeviceId, i, ex);
            }

        }

        executorService.shutdown();

        while (true) {
            if (executorService.isTerminated()) {
                log.info("所有同步统一设备任务已执行完成。。。。");

                //此时还需要检查一下线程池的完成任务数是否和期望的任务数一致
                long completedTaskCount = executorService.getCompletedTaskCount();
                log.info("expectedCompletedTaskCount:{} ; actualCompletedTaskCount:{}",
                        expectedCompletedTaskCount,
                        completedTaskCount);
                if (expectedCompletedTaskCount == completedTaskCount) {
                    //到这里说明所有设备已经更新完成，那么模式还是同步中的设备则说明这次没有同步到，将模式改为未同步到
//                    umsSubDeviceManager.updateUmsSubDeviceMod(UmsMod.UPGRADING, UmsMod.EXIST);
                    //开始同步分组信息
                    try {
                        log.info("本次更新所有统一设备模式已更正。。。。");
                        DeviceManagerService deviceManagerService = getBean(DeviceManagerService.class);
                        log.info("开始获取设备分组信息，统一平台id为[{}]", umsDeviceId);
                        Boolean umsGroupResult = deviceManagerService.queryDeviceGroupNotify(umsDeviceId);
                        if (!umsGroupResult) {
                            log.error("获取所有设备分组信息失败，统一设备Id为:[{}]", umsDeviceId);
                        }
                        log.info("获取设备分组信息完成。。。统一设备Id为:[{}]", umsDeviceId);
                    } catch (Exception e) {
                        log.error("同步设备分组信息异常。。。统一设备Id为:[{}]，e:", umsDeviceId, e);
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                } else {
                    log.error("本次更新过程中有任务丢失，" +
                                    "expectedCompletedTaskCount:{} ; " +
                                    "actualCompletedTaskCount:{}",
                            expectedCompletedTaskCount,
                            completedTaskCount);
                    return false;
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean doAlone(int curPage, int pageSize, UmsSubDeviceManager umsSubDeviceManager) {

        int retryTime = 0;

        while (true) {

            Integer code = umsSubDeviceManager.selectAndInsertSubDeviceFromAvFeign(umsDeviceId, curPage, pageSize);
            log.info("返回的code数据为:[{}]", code);
            if (code == Integer.MAX_VALUE) {
                //到这里说明所有设备已经更新完成，那么模式还是同步中的设备则说明这次没有同步到，将模式改为未同步到
//                umsSubDeviceManager.updateUmsSubDeviceMod(UmsMod.UPGRADING, UmsMod.EXIST);
                DeviceManagerService deviceManagerService = getBean(DeviceManagerService.class);
                log.info("开始获取设备分组信息，统一平台id为{}", umsDeviceId);
                Boolean umsGroupResult = deviceManagerService.queryDeviceGroupNotify(umsDeviceId);
                if (!umsGroupResult) {
                    log.error("远程调用统一设备分组接口失败或者未获取到统一设备分组信息,统一设备Id为:[{}]", umsDeviceId);
                    return false;
                }
                return true;
            }
//            if (curPage == (code)) {
//                retryTime++;
//                if (retryTime >= MAX_RETRY_TIME) {
//                    log.error("第{}页数据查询已达到最大查询失败次数:[{}]次,请检查远程服务连接是否正常！！!", code, retryTime);
//                    return false;
//                }
//                log.error("第{}页数据第{}次查询失败", code, retryTime);
//                continue;
//            }
            //重试次数复位
            if (retryTime != 0) {
                retryTime = 0;
            }
            curPage++;
        }
    }

    private Integer queryCountOfSubDeviceFromThird(String umsDeviceId, Integer queryindex, Integer querycount) {

        int countNum = 0;
        for (int i = 0; i <= countNum; i++) {
            QuerySubDeviceInfoResponse umsSubDeviceFromThird = getUmsSubDeviceFromThird(umsDeviceId, queryindex, querycount);
            if (ObjectUtil.isNull(umsSubDeviceFromThird.getQuerycount()) || CollectionUtil.isEmpty(umsSubDeviceFromThird.getDevinfo())) {
                log.error("从远端获取设备信息为空");
                return 0;
            }
            Integer resultCount = umsSubDeviceFromThird.getQuerycount();
            if (resultCount < querycount) {
                if (queryindex == 1) {
                    return resultCount;
                }
                return queryindex * querycount + resultCount;
            }
            countNum++;
            queryindex++;
        }

        return 0;
    }

    /**
     * 从远端获取统一设备下子设备
     *
     * @param umsDeviceId 统一设备id
     * @param queryindex  查询起始数
     * @param querycount  查询总数
     * @return 结果
     */
    private QuerySubDeviceInfoResponse getUmsSubDeviceFromThird(String umsDeviceId, Integer queryindex, Integer querycount) {

        UmsClient umsClient = getBean(UmsClient.class);

        DeviceMapper bean = getBean(DeviceMapper.class);
        String sessionId = bean.selectById(umsDeviceId).getSessionId();

        QueryDeviceRequest queryDeviceRequest = new QueryDeviceRequest();
        queryDeviceRequest.setSsid(Integer.valueOf(sessionId));
        queryDeviceRequest.setQueryindex(queryindex);
        queryDeviceRequest.setQuerycount(querycount);

        return umsClient.querydev(queryDeviceRequest);
    }

    /**
     * 从本地服务获取统一设备下的子设备
     *
     * @param pageSize 每一页大小
     * @return 结果
     */
    private BasePage<UmsSubDeviceInfoQueryResponseDto> getUmsSubDeviceFromLocal(Integer pageSize) {

        DeviceManagerService deviceManagerService = getBean(DeviceManagerService.class);

        UmsSubDeviceInfoQueryRequestDto requestVo = new UmsSubDeviceInfoQueryRequestDto();

        requestVo.setPageSize(pageSize);

        return deviceManagerService.selectUmsSubDeviceList(requestVo);
    }

    /**
     * 更新最近一次同步时间
     *
     * @param umsDeviceId 统一平台id
     */
    private void setSyncThirdTime(String umsDeviceId) {
        DeviceMapper umsDeviceInfoMapper = getBean(DeviceMapper.class);

        DeviceInfoEntity entity = new DeviceInfoEntity();
        entity.setId(umsDeviceId);
        entity.setLastSyncThirdDeviceTime(DateUtil.now());

        umsDeviceInfoMapper.updateById(entity);
    }

    //检查连接
    //TODO 检测链接暂时先不要
/*    public void checkConnection(BaseResponseVo baseResponseVo) {

        if (!(UdmsCodeResult.SUCCESS.equals(baseResponseVo.getCode()) &&
                Boolean.TRUE.equals(baseResponseVo.isRet()))) {

            log.error("远程连接错误，错误信息为:[{}]", baseResponseVo.getMessage());
            keepConnection = false;
        } else {
            keepConnection = true;
        }
    }*/

    /**
     * 这个方法的参数设定会根据线上具体的情况验证后来设定
     *
     * @param totalPage 总页数
     */
    private void setPoolSize(int totalPage) {
        if (totalPage <= 50) {
            return;
        }

        if (totalPage <= 100) {
            shouldDistribute = true;
            CORE_SIZE = 5;
            MAX_SIZE = 10;
            return;
        }

        if (totalPage <= 500) {
            shouldDistribute = true;
            CORE_SIZE = 10;
            MAX_SIZE = 15;
            return;
        }

        shouldDistribute = true;
        CORE_SIZE = 15;
        MAX_SIZE = 20;

    }

    /**
     * 自定义线程UncaughtException处理
     */
    static class UmsSyncUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("UncaughtException occur in thread[{}]", t.getName(), e);
        }
    }

    private void monitor(ThreadPoolExecutor executorService) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        if (executorService == null) {
                            return;
                        }

                        int corePoolSize = executorService.getCorePoolSize();
                        int largestPoolSize = executorService.getLargestPoolSize();
                        int maximumPoolSize = executorService.getMaximumPoolSize();
                        int activeCount = executorService.getActiveCount();
                        int poolSize = executorService.getPoolSize();
                        long taskCount = executorService.getTaskCount();
                        boolean shutdown = executorService.isShutdown();
                        boolean terminated = executorService.isTerminated();
                        long completedTaskCount = executorService.getCompletedTaskCount();
                        log.info("executorService:{} corePoolSize:{} largestPoolSize:{} maximumPoolSize:{} activeCount:{} poolSize:{} taskCount:{} completedTaskCount:{} shutdown:{} terminated:{}",
                                executorService, corePoolSize, largestPoolSize, maximumPoolSize, activeCount, poolSize, taskCount, completedTaskCount, shutdown, terminated);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        log.error("moniter error:", e);
                    }


                }
            }
        }).start();

    }

    public static void main(String[] args) {

        AtomicInteger time = new AtomicInteger(0);

        do {
            System.out.println(time.get());
        } while (time.incrementAndGet() < 3);

    }

}
