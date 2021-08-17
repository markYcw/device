package com.kedacom.device.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.mapper.UmsMeetingPlatformMapper;
import com.kedacom.device.core.service.UmsMeetingPlatformService;
import com.kedacom.mp.mcu.entity.UmsMeetingPlatformEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("umsMeetingPlatformService")
public class UmsMeetingPlatformServiceImpl extends ServiceImpl<UmsMeetingPlatformMapper, UmsMeetingPlatformEntity> implements UmsMeetingPlatformService {

    @Resource
    private UmsMeetingPlatformMapper mapper;

    @Override
    public BaseResult<BasePage<UmsMeetingPlatformEntity>> pageQuery(McuPageQueryDTO queryDTO) {
        Page<UmsMeetingPlatformEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<UmsMeetingPlatformEntity> queryWrapper = new LambdaQueryWrapper<>();

        Page<UmsMeetingPlatformEntity> platformEntityPage = mapper.selectPage(page, queryWrapper);
        List<UmsMeetingPlatformEntity> records = platformEntityPage.getRecords();

        BasePage<UmsMeetingPlatformEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(records);
        return BaseResult.succeed(basePage);
    }
}