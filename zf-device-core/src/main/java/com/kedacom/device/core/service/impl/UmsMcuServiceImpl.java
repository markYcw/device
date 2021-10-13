package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.constant.DeviceConstants;
import com.kedacom.device.core.constant.DeviceErrorEnum;
import com.kedacom.device.core.exception.MpException;
import com.kedacom.device.core.mapper.UmsMcuMapper;
import com.kedacom.device.core.service.McuService;
import com.kedacom.device.core.service.UmsMcuService;
import com.kedacom.device.core.utils.DateUtils;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.entity.UmsMcuEntity;
import com.kedacom.mp.mcu.pojo.McuPageQueryDTO;
import com.kedacom.mp.mcu.response.McuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public BaseResult<BasePage<McuVo>> pageQuery(McuPageQueryDTO queryDTO) {
        log.info("========分页接口入参{}",queryDTO);
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
        ArrayList<McuVo> mcuVos = new ArrayList<>();
        for (UmsMcuEntity record : records) {
            McuVo vo = new McuVo();
            BeanUtils.copyProperties(record,vo);
            vo.setCreateTime(DateUtils.getDefaultDate(record.getCreateTime()));
            vo.setModifyTime(DateUtils.getDefaultDate(record.getModifyTime()));
            mcuVos.add(vo);
        }
        BasePage<McuVo> basePage = new BasePage<>();
        basePage.setTotal(platformEntityPage.getTotal());
        basePage.setTotalPage(platformEntityPage.getPages());
        basePage.setCurPage(queryDTO.getCurPage());
        basePage.setPageSize(queryDTO.getPageSize());
        basePage.setData(mcuVos);
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

    @Override
    public BaseResult<McuVo> info(Long id) {
        log.info("======根据ID查询mcu接口入参",id);
        UmsMcuEntity umsMcuEntity = mapper.selectById(id);
        if(ObjectUtils.isEmpty(umsMcuEntity)){
                throw new MpException(DeviceErrorEnum.DEVICE_NOT_FOUND);
        }
        McuVo mcuVo = new McuVo();
        BeanUtils.copyProperties(umsMcuEntity,mcuVo);
        mcuVo.setCreateTime(DateUtils.getDefaultDate(umsMcuEntity.getCreateTime()));
        mcuVo.setModifyTime(DateUtils.getDefaultDate(umsMcuEntity.getModifyTime()));
        return BaseResult.succeed("查询成功",mcuVo);
    }

    @Override
    public BaseResult<McuVo> saveMcu(McuVo mcuVo) {
        log.info("=========保存mcu接口入参{}",mcuVo);
        UmsMcuEntity entity = new UmsMcuEntity();
        BeanUtils.copyProperties(mcuVo,entity);
        mapper.insert(entity);
        BeanUtils.copyProperties(entity,mcuVo);
        return BaseResult.succeed("新增成功",mcuVo);
    }

    @Override
    public BaseResult updateByVo(McuVo vo) {
        log.info("=========修改mcu接口入参{}",vo);
        UmsMcuEntity entity = new UmsMcuEntity();
        BeanUtils.copyProperties(vo,entity);
        mapper.updateById(entity);
        return BaseResult.succeed("修改成功",vo);
    }

}