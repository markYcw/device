package com.kedacom.device.controller;

import com.kedacom.device.core.service.TvPlayService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 09:47
 */
@RestController
@RequestMapping("/api/v1/manage/tvplay")
@Api(value = "浏览")
@Slf4j
public class TvPlayController {

    @Resource
    private TvPlayService tvPlayService;

}
