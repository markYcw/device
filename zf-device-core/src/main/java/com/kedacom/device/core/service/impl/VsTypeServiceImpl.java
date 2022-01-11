package com.kedacom.device.core.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.device.core.mapper.VsTypeMapper;
import com.kedacom.device.core.service.VsTypeService;
import com.kedacom.vs.entity.VsTypeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class VsTypeServiceImpl extends ServiceImpl<VsTypeMapper, VsTypeEntity> implements VsTypeService {

}
