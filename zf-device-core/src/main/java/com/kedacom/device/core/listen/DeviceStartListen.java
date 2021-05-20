package com.kedacom.device.core.listen;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.convert.UmsDeviceConvert;
import com.kedacom.device.core.entity.DeviceInfoEntity;
import com.kedacom.device.core.entity.SubDeviceInfoEntity;
import com.kedacom.device.core.mapper.DeviceMapper;
import com.kedacom.device.core.mapper.SubDeviceMapper;
import com.kedacom.device.core.service.UmsManagerService;
import com.kedacom.device.core.utils.PinYinUtils;
import com.kedacom.device.ums.UmsClient;
import com.kedacom.device.ums.request.LoginRequest;
import com.kedacom.device.ums.response.LoginResponse;
import com.kedacom.ums.requestdto.UmsDeviceInfoSyncRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 09:51
 */
@Component
@Slf4j
public class DeviceStartListen implements ApplicationListener<ApplicationStartedEvent> {
    @Resource
    private UmsClient umsClient;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private SubDeviceMapper subDeviceMapper;

    @Resource
    private UmsManagerService umsManagerService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        LambdaQueryWrapper<DeviceInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        List<DeviceInfoEntity> beforeLoginList = deviceMapper.selectList(queryWrapper);
        try {
            if (CollectionUtil.isNotEmpty(beforeLoginList)) {
                log.info("统一设备项目初始化-设备登录前 DeviceInfoEntity:{}", beforeLoginList);
                for (DeviceInfoEntity deviceInfoEntity : beforeLoginList) {
                    LoginRequest loginRequest = UmsDeviceConvert.INSTANCE.convertDeviceInfo(deviceInfoEntity);
                    LoginResponse response = umsClient.login(loginRequest);
                    deviceInfoEntity.setSessionId(String.valueOf(response.acquireSsid()));
                    deviceMapper.updateById(deviceInfoEntity);
                }
            }

            List<DeviceInfoEntity> afterLoginList = deviceMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(afterLoginList)) {
                log.info("统一设备项目初始化-设备登录后 DeviceInfoEntity:{}", afterLoginList);
                for (DeviceInfoEntity deviceInfoEntity : afterLoginList) {
                    UmsDeviceInfoSyncRequestDto request = new UmsDeviceInfoSyncRequestDto();
                    request.setUmsId(deviceInfoEntity.getId());
                    umsManagerService.syncDeviceData(request);
                }
            }
        } catch (Exception e) {
            log.error("统一设备项目初始化设备和设备分组失败:{}", e.getMessage());
        }

        LambdaQueryWrapper<SubDeviceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        List<SubDeviceInfoEntity> selectList = subDeviceMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(selectList)) {
            log.info("统一设备项目项目初始化监听事件登录后， 设备名称拼音转化");
            for (SubDeviceInfoEntity entity : selectList) {
                String name = entity.getName();
                String hanZiPinYin = PinYinUtils.getHanZiPinYin(name);
                String hanZiInitial = PinYinUtils.getHanZiInitials(name);
                String lowerCase = PinYinUtils.StrToLowerCase(hanZiInitial);
                entity.setPinyin(hanZiPinYin + "&&" + lowerCase);
                subDeviceMapper.updateById(entity);
            }
            log.info("设备名称拼音转化完成");
        }
    }

}
