package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.mapper.UmsMcuMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.service.UmsMcuService;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


/**
 * @author hexijian
 */
@Service("umsMcuServiceImpl")
public class UmsMcuServiceImpl extends ServiceImpl<UmsMcuMapper, UmsMcuEntity> implements UmsMcuService {

    @Resource
    private UmsMcuMapper mapper;

    @Autowired
    private McuService mcuService;

    @Override
    public BaseResult<BasePage<UmsMcuEntity>> pageQuery(McuPageQueryDTO queryDTO) {
        Page<UmsMcuEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<UmsMcuEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(queryDTO.getIp())) {
            queryWrapper.like(UmsMcuEntity::getIp, queryDTO.getIp());
        }
        if (StrUtil.isNotBlank(queryDTO.getName())) {
            queryWrapper.like(UmsMcuEntity::getIp, queryDTO.getName());
        }

        Page<UmsMcuEntity> platformEntityPage = mapper.selectPage(page, queryWrapper);
        List<UmsMcuEntity> records = platformEntityPage.getRecords();

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
        String ssid = (String) jsonObject.get("ssid");
        if (Objects.equals(type, DeviceConstants.DEVICE_OFFLINE_NOTIFICATION) && StrUtil.isNotBlank(ssid)) {
            LambdaQueryWrapper<UmsMcuEntity> wrapper = new LambdaQueryWrapper<UmsMcuEntity>();
            wrapper.eq(UmsMcuEntity::getSsid, ssid);
            List<UmsMcuEntity> mcuEntities = mapper.selectList(wrapper);
            for (Iterator<UmsMcuEntity> it = mcuEntities.iterator(); it.hasNext(); ) {
                McuRequestDTO dto = new McuRequestDTO();
                dto.setMcuId(it.next().getId());
                mcuService.login(dto);
            }
        }
    }

}