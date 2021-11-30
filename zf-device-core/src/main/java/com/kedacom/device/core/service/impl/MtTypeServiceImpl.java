package com.kedacom.device.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kedacom.device.core.convert.MtConvert;
import com.kedacom.device.core.entity.MtTypeEntity;
import com.kedacom.device.core.mapper.MtTypeMapper;
import com.kedacom.device.core.service.MtTypeService;
import com.kedacom.mt.request.QueryMtTypeListRequestDto;
import com.kedacom.mt.response.QueryMtTypeListResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/29
 */
@Slf4j
@Service
public class MtTypeServiceImpl implements MtTypeService {

    @Resource
    MtTypeMapper mtTypeMapper;

    @Override
    public List<QueryMtTypeListResponseVo> queryList(QueryMtTypeListRequestDto requestDto) {

        log.info("查询终端设备类型信息集合请求参数类信息 : {}", requestDto);
        LambdaQueryWrapper<MtTypeEntity> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(requestDto.getId())) {
            queryWrapper.eq(MtTypeEntity::getId, requestDto.getId());
        }
        if (StrUtil.isNotBlank(requestDto.getMtName())) {
            queryWrapper.like(MtTypeEntity::getMtName, requestDto.getMtName());
        }
        if (requestDto.getMtType() != null) {
            queryWrapper.eq(MtTypeEntity::getMtType, requestDto.getMtType());
        }
        if (requestDto.getMtVersion() != null) {
            queryWrapper.eq(MtTypeEntity::getMtVersion, requestDto.getMtVersion());
        }
        List<MtTypeEntity> selectList = mtTypeMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(selectList)) {
            return null;
        }

        return MtConvert.INSTANCE.convertQueryMtTypeListResponseVoList(selectList);
    }

}
