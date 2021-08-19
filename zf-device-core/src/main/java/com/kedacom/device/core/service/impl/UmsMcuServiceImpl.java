package com.kedacom.device.core.service.impl;

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
import java.util.List;
import java.util.Objects;


@Service("umsMeetingPlatformService")
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
        if (Objects.equals(type, DeviceConstants.DEVICE_OFFLINE_NOTIFICATION)) {
            List<UmsMcuEntity> mcuEntities = mapper.selectList(null);
            for (UmsMcuEntity umsMcuEntity : mcuEntities) {
                McuRequestDTO dto = new McuRequestDTO();
                dto.setMcuId(umsMcuEntity.getId());
                mcuService.login(dto);
            }
        }

    }
}