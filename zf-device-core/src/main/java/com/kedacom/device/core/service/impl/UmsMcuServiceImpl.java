package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.convert.McuConvert;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.UmsMcuMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.UmsMcuService;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.McuEntity;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author hexijian
 */
@Slf4j
@Service("umsMcuServiceImpl")
public class UmsMcuServiceImpl extends ServiceImpl<UmsMcuMapper, McuEntity> implements UmsMcuService {

    @Resource
    private UmsMcuMapper mapper;

    @Autowired
    private McuService mcuService;

    @Autowired
    private RegisterListenerService registerListenerService;

    @Autowired
    private DeviceNotifyUtils notifyUtils;

    @Autowired
    private McuConvert convert;

    @Override
    public BaseResult<BasePage<UmsMcuEntity>> pageQuery(McuPageQueryDTO queryDTO) {
        log.info("MCU分页查询接口入参：McuPageQueryDTO{}", queryDTO);
        Page<McuEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<McuEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(queryDTO.getIp())) {
            queryWrapper.like(McuEntity::getIp, queryDTO.getIp());
        }
        if (StrUtil.isNotBlank(queryDTO.getName())) {
            queryWrapper.like(McuEntity::getName, queryDTO.getName());
        }

        Page<McuEntity> platformEntityPage = mapper.selectPage(page, queryWrapper);
        List<McuEntity> records = platformEntityPage.getRecords();
        for (McuEntity record : records) {
             Integer status =  McuServiceImpl.mcuStatusPoll.get(record.getId());
            if (status == null) {
                //如果未登录则直接设置MCU状态为离线
                record.setStatus(DevTypeConstant.getZero);
            } else {
                //如果已登录则给设备发送心跳，成功则设置为在线否则为离线
                McuRequestDTO dto = new McuRequestDTO();
                dto.setMcuId(record.getId());
                try {
                    Integer result = mcuService.hbTest(dto);
                    if (result == 0) {
                        record.setStatus(DevTypeConstant.updateRecordKey);
                    } else {
                        record.setStatus(DevTypeConstant.getZero);
                    }
                } catch (Exception e) {
                    log.error("==============分页查询mcu，发送心跳时候发生错误{}", e);
                    //如果离线就从状态池把改mcu的ID删掉并把状态设为离线
                    McuServiceImpl.mcuStatusPoll.remove(record.getId());
                    record.setStatus(DevTypeConstant.getZero);
                    continue;
                }
            }
        }
        List<UmsMcuEntity> collect = records.stream().map(a -> convert.convertToUmsMcuEntity(a)).collect(Collectors.toList());
        BasePage<UmsMcuEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(collect);
        return BaseResult.succeed(basePage);
    }

    @Override
    public void mcuNotify(String notify) {

        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer type = (Integer) jsonObject.get("type");
        String ssid = String.valueOf(jsonObject.get("ssid"));
        if (Objects.equals(type, DeviceConstants.DEVICE_OFFLINE_NOTIFICATION) && StrUtil.isNotBlank(ssid)) {
            LambdaQueryWrapper<McuEntity> wrapper = new LambdaQueryWrapper<McuEntity>();
            wrapper.eq(McuEntity::getSsid, ssid);
            List<McuEntity> mcuEntities = mapper.selectList(wrapper);
            for (Iterator<McuEntity> it = mcuEntities.iterator(); it.hasNext(); ) {
                //收到离线通知先把MCU状态设置为离线
                McuServiceImpl.mcuStatusPoll.remove(it.next().getId());
                DeviceNotifyRequestDTO dto = new DeviceNotifyRequestDTO();
                dto.setMcuId(it.next().getId());
                List<KmListenerEntity> all = registerListenerService.getAll(MsgType.MCU_OFF_LINE.getType());
                if (!CollectionUtil.isEmpty(all)) {
                    for (KmListenerEntity kmListenerEntity : all) {
                        try {
                            notifyUtils.cuDeviceNty(kmListenerEntity.getUrl(), dto);
                        } catch (Exception e) {
                            log.error("------------发送mcu掉线通知给业务方失败", e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public UmsMcuEntity getBySsid(Integer ssid) {
        String s = String.valueOf(ssid);
        LambdaQueryWrapper<McuEntity> wrapper = new LambdaQueryWrapper<McuEntity>();
        wrapper.eq(McuEntity::getSsid, s);
        List<McuEntity> mcuEntities = mapper.selectList(wrapper);
        List<UmsMcuEntity> collect = mcuEntities.stream().map(a -> convert.convertToUmsMcuEntity(a)).collect(Collectors.toList());
        return collect.get(DevTypeConstant.getZero);
    }

}