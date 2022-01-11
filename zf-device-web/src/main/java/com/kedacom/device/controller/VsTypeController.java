package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.core.service.VsTypeService;
import com.kedacom.vs.entity.VsTypeEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * VRS录播服务器型号
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-04 16:58:24
 */
@Api(tags = "VsTypeController/VRS录播服务器型号")
@RestController
@RequestMapping("ums/vsType")
public class VsTypeController {

    @Autowired
    private VsTypeService service;

    @ApiOperation("返回录播服务器所有记录")
    @PostMapping("/list")
    public BaseResult<List<VsTypeEntity>> list() {

        List<VsTypeEntity> list = service.list();
        return BaseResult.succeed("查询录播服务器设备类型成功",list);
    }





}
