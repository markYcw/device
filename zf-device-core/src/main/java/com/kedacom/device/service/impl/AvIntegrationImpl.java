package com.kedacom.device.service.impl;

import com.kedacom.acl.network.unite.AvIntegrationInterface;
import com.kedacom.device.service.AvIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 16:34
 */
@Slf4j
@Service
public class AvIntegrationImpl implements AvIntegrationService {

    @Resource
    private AvIntegrationInterface avIntegrationInterface;


}
