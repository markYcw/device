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
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.device.core.mapper.UmsMcuMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.service.RegisterListenerService;
import com.kedacom.device.core.service.UmsMcuService;
import com.kedacom.device.core.utils.DeviceNotifyUtils;
import com.kedacom.deviceListener.msgType.MsgType;
import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


/**
 * @author hexijian
 */
@Slf4j
@Service("umsMcuServiceImpl")
public class UmsMcuServiceImpl extends ServiceImpl<UmsMcuMapper, UmsMcuEntity> implements UmsMcuService {

    @Resource
    private UmsMcuMapper mapper;

    @Autowired
    private McuService mcuService;

    @Autowired
    private RegisterListenerService registerListenerService;

    @Autowired
    private DeviceNotifyUtils notifyUtils;

    @Override
    public BaseResult<BasePage<UmsMcuEntity>> pageQuery(McuPageQueryDTO queryDTO) {
        log.info("MCU分页查询接口入参：McuPageQueryDTO{}",queryDTO);
        Page<UmsMcuEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<UmsMcuEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(queryDTO.getIp())) {
            queryWrapper.like(UmsMcuEntity::getIp, queryDTO.getIp());
        }
        if (StrUtil.isNotBlank(queryDTO.getName())) {
            queryWrapper.like(UmsMcuEntity::getName, queryDTO.getName());
        }

        Page<UmsMcuEntity> platformEntityPage = mapper.selectPage(page, queryWrapper);
        List<UmsMcuEntity> records = platformEntityPage.getRecords();

        for (UmsMcuEntity record : records) {
            if(McuServiceImpl.mcuStatusPoll.get(record.getId())==null){
                //如果未登录则直接设置MCU状态为离线
                record.setStatus(DevTypeConstant.getZero);
            }else {
                //如果已登录则给设备发送心跳，成功则设置为在线否则为离线
                McuRequestDTO dto = new McuRequestDTO();
                dto.setMcuId(record.getId());
                BaseResult<Integer> result = null;
                try {
                    result = mcuService.hb(dto);
                    record.setStatus(DevTypeConstant.updateRecordKey);
                } catch (Exception e) {
                  log.error("==============分页查询mcu，发送心跳时候发生错误{}",e);
                    //如果离线就从状态池把改mcu的ID删掉并把状态设为离线
                    McuServiceImpl.mcuStatusPoll.remove(record.getId());
                    record.setStatus(DevTypeConstant.getZero);
                    continue;
                }
            }
        }

        BasePage<UmsMcuEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(records);
        return BaseResult.succeed(basePage);
    }

    @Override
    public void mcuNotify(String notify) {

        JSONObject jsonObject = JSONObject.parseObject(notify);
        Integer type = (Integer) jsonObject.get("type");
        String ssid = String.valueOf(jsonObject.get("ssid"));
        if (Objects.equals(type, DeviceConstants.DEVICE_OFFLINE_NOTIFICATION) && StrUtil.isNotBlank(ssid)) {
            LambdaQueryWrapper<UmsMcuEntity> wrapper = new LambdaQueryWrapper<UmsMcuEntity>();
            wrapper.eq(UmsMcuEntity::getSsid, ssid);
            List<UmsMcuEntity> mcuEntities = mapper.selectList(wrapper);
            for (Iterator<UmsMcuEntity> it = mcuEntities.iterator(); it.hasNext(); ) {
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
        LambdaQueryWrapper<UmsMcuEntity> wrapper = new LambdaQueryWrapper<UmsMcuEntity>();
        wrapper.eq(UmsMcuEntity::getSsid, s);
        List<UmsMcuEntity> mcuEntities = mapper.selectList(wrapper);
        return mcuEntities.get(DevTypeConstant.getZero);
    }

}