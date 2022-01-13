package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.core.convert.VrsConvert;
import com.kedacom.device.core.service.VrsFiveService;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * VRS录播服务器5.1设备
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-04 16:58:24
 */
@Api(tags = "VrsFiveController/Vrs2100/4100接口")
@RestController
@RequestMapping("ums/vs")
public class VrsFiveController {

    @Autowired
    private VrsFiveService vrsFiveService;

    @Autowired
    private VrsConvert convert;

    @ApiOperation("返回VRS2100/4100所有记录")
    @PostMapping("/vrsFiveList")
    public BaseResult<List<VrsVo>> vrsFiveList() {

        List<VsEntity> list = vrsFiveService.list();
        List<VrsVo> collect = list.stream().map(a -> convert.convertToVrsVo(a)).collect(Collectors.toList());
        return BaseResult.succeed("查询录播服务器集合成功",collect);
    }

    @ApiOperation("分页查询vrs信息")
    @PostMapping("/vrsList")
    public BaseResult<BasePage<VsEntity>> terminalList(@RequestBody VrsQuery vrsQuery) {

        return vrsFiveService.vrsList(vrsQuery);
    }

    @ApiOperation("保存VRS信息")
    @PostMapping("/saveVrs")
    public BaseResult<VrsVo> saveVrs(@RequestBody VrsVo vrsVo) {

        return vrsFiveService.saveVrs(vrsVo);
    }

    @ApiOperation("删除VRS信息")
    @PostMapping("/deleteVrs")
    public BaseResult deleteVrs(@RequestBody List<Integer> ids) {

        return vrsFiveService.delete(ids);
    }

    @ApiOperation("修改VRS信息")
    @PostMapping("/updateVrs")
    public BaseResult<VrsVo> updateVrs(@RequestBody VrsVo vrsVo) {

        return vrsFiveService.updateVrs(vrsVo);
    }

    @ApiOperation("查询VRS信息")
    @PostMapping("/vtsInfo")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "数据库ID")})
    public BaseResult<VrsVo> vtsInfo(@RequestParam Integer id){

        return vrsFiveService.vtsInfo(id);
    }

    @ApiOperation("分页查询HTTP录像 5.1版本才支持")
    @PostMapping("/vrsQueryHttpRec")
    public BaseResult<VrsRecInfoDecVo> vrsQueryHttpRec(@RequestBody QueryRecListVo queryRecListVo){

        return vrsFiveService.vrsQueryHttpRec(queryRecListVo);
    }

    @ApiOperation("查询录像")
    @PostMapping("/queryRec")
    public BaseResult<VrsRecInfoVo> queryRec(@RequestBody QueryRecVo vo){

        return vrsFiveService.queryRec(vo);
    }

    @ApiOperation("查询直播 5.1版本才支持")
    @PostMapping("/queryLive")
    public BaseResult<LiveInfoVo> queryLive(@RequestBody QueryLiveVo vo){

        return vrsFiveService.queryLive(vo);
    }

    @ApiOperation("根据IP查询录像：不许要传数据库ID只需传入设备IP用户名密码就可")
    @PostMapping("/queryRecByIp")
    public BaseResult<VrsRecInfoVo> queryRecByIp(@RequestBody QueryRecByIpVo vo){

        return vrsFiveService.queryRecByIp(vo);
    }




}
