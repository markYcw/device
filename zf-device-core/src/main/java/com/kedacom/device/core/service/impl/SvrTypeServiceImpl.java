package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.exception.CuException;
import com.kedacom.device.core.mapper.SvrTypeMapper;
import com.kedacom.device.core.service.SvrTypeService;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.entity.SvrTypeEntity;
import com.kedacom.svr.pojo.SvrTypePageQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/1/6 10:22
 * @description
 */
@Slf4j
@Service
public class SvrTypeServiceImpl extends ServiceImpl<SvrTypeMapper, SvrTypeEntity> implements SvrTypeService {

    @Autowired
    private SvrTypeMapper mapper;

    @Override
    public BaseResult<BasePage<SvrTypeEntity>> pageQuery(SvrTypePageQueryDTO queryDTO) {
        log.info("=======SVR设备类型分页接口入参：{}",queryDTO);
        Page<SvrTypeEntity> page = new Page<>();
        page.setCurrent(queryDTO.getCurPage());
        page.setSize(queryDTO.getPageSize());

        LambdaQueryWrapper<SvrTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtil.isNotNull(queryDTO.getType())){
            queryWrapper.like(SvrTypeEntity::getType,queryDTO.getType());
        }

        Page<SvrTypeEntity> platformEntityPage = mapper.selectPage(page, queryWrapper);
        List<SvrTypeEntity> records = platformEntityPage.getRecords();

        BasePage<SvrTypeEntity> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(records);
        return BaseResult.succeed(basePage);
    }

    @Override
    public BaseResult<SvrTypeEntity> saveInfo(SvrTypeEntity entity) {
        log.info("=======SVR设备类型保存接口入参：{}",entity);
        if(!isRepeat(entity)){
            throw new CuException(DeviceErrorEnum.IP_OR_NAME_REPEAT);
        }
        mapper.insert(entity);
        return BaseResult.succeed("保存成功",entity);
    }

    /**
     * 对名称和IP做唯一校验
     * @return
     */
    public boolean isRepeat(SvrTypeEntity devEntity) {
        Integer id = devEntity.getId();
        String type = devEntity.getType();
        LambdaQueryWrapper<SvrTypeEntity> wrapper = new LambdaQueryWrapper<>();
        if (id == null) {
            wrapper.eq(SvrTypeEntity::getType, type);
            List<SvrTypeEntity> devEntitiesInsert = mapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesInsert)) {
                log.info("=============添加SVR设备类型时名称重复===============");
                return false;
            }
        } else {
            wrapper.eq(SvrTypeEntity::getType, type).ne(SvrTypeEntity::getId,id);
            List<SvrTypeEntity> devEntitiesUpdate = mapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(devEntitiesUpdate)) {
                log.info("==================修改SVR设备类型时名称重复========================");
                return false;
            }
        }

        return true;
    }
}
