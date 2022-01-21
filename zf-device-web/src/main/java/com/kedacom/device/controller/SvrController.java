package com.kedacom.device.controller;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.enums.DeviceModelType;
import com.kedacom.device.core.service.SvrService;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.vo.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * SVR控制相关
 *
 * @author ycw
 * @version v1.0
 * @date 2021/09/06 9:44
 * @description
 */
@Slf4j
@RestController
@RequestMapping("ums/svr")
@Api(value = "SVR控制相关", tags = "SVR控制层")
public class SvrController {

    @Autowired
    private SvrService svrService;


    @PostMapping("/pageQuery")
    @ApiOperation(value = "svr分页查询")
    public BaseResult<BasePage<SvrEntity>> pageQuery(@RequestBody SvrPageQueryDTO queryDTO) {
        log.info("svr分页接口入参:{}", queryDTO);

        return svrService.pageQuery(queryDTO);
    }

    @PostMapping("/all")
    @ApiOperation(value = "查询所有SVR")
    public BaseResult<List<SvrEntity>> all() {

        List<SvrEntity> list = svrService.list();
        return BaseResult.succeed(list);
    }


    @PostMapping("/info")
    @ApiOperation(value = "根据id获取svr信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrEntity> info(@RequestParam Integer dbId) {
        log.info("根据id获取svr信息:{}", dbId);

        return BaseResult.succeed(svrService.getById(dbId));
    }


    @PostMapping("/save")
    @ApiOperation(value = "新增svr")
    public BaseResult<SvrEntity> save(@Valid @RequestBody SvrEntity entity, BindingResult br) {
        log.info("新增svr:{}", entity);
        ValidUtils.paramValid(br);

        return svrService.saveInfo(entity);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改svr")
    public BaseResult<SvrEntity> update(@RequestBody SvrEntity entity) {
        log.info("修改svr:{}", entity);
        entity.setDevType(DeviceModelType.getEnum(entity.getModelType()));
        svrService.updateById(entity);

        return BaseResult.succeed(entity);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除svr")
    public BaseResult delete(@RequestBody Long[] ids) {
        log.info("删除svr:{}", ids);
        svrService.removeByIds(Arrays.asList(ids));

        return BaseResult.succeed("删除成功");
    }


    @PostMapping("/loginById")
    @ApiOperation(value = "根据ID登录SVR")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrLoginVO> loginById(@RequestParam Integer dbId) {

        return svrService.loginById(dbId);
    }

    @PostMapping("/hb")
    @ApiOperation(value = "发送心跳")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<String> hb(@RequestParam Integer dbId) {

        return svrService.hb(dbId);
    }

    @ApiOperation("登出SVR")
    @PostMapping("/logoutById")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult logoutById(@RequestParam Integer dbId) {

        return svrService.logoutById(dbId);
    }

    @ApiOperation("获取SVR能力集")
    @PostMapping("/svrCap")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<SvrCapVo> svrCap(@RequestParam Integer dbId) {

        return svrService.svrCap(dbId);
    }

    @ApiOperation("获取SVR时间")
    @PostMapping("/getBurnTime")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<String> getBurnTime(@NotNull(message = "Svr设备数据库ID不能为空") @RequestParam Integer dbId) {

        return svrService.svrTime(dbId);
    }

    @ApiOperation("搜索编解码设备")
    @PostMapping("/searchDev")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<String> searchDev(@RequestParam Integer dbId) {

        return svrService.searchDev(dbId);
    }

    @ApiOperation("获取编码通道列表")
    @PostMapping("/enChnList")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<List<EnChnListVo>> enChnList(@RequestParam Integer dbId) {

        return svrService.enChnList(dbId);
    }

    @ApiOperation("添加/删除编码通道")
    @PostMapping("/enChn")
    public BaseResult<String> enChn(@Valid @RequestBody EnChnDto enChnDto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.enChn(enChnDto);
    }

    @ApiOperation("获取编码器的预置位")
    @PostMapping("/getIpcItem")
    public BaseResult<CpResetVo> getIpcItem(@Valid @RequestBody GetIpcItemRequestVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.enCpReset(dto);
    }

    @ApiOperation("修改编码器预置位")
    @PostMapping("/CpReset")
    public BaseResult<String> CpReset(@Valid @RequestBody CpResetDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.CpReset(dto);
    }

    @ApiOperation("获取解码通道列表")
    @PostMapping("/deChnList")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<DeChnListVo> deChnList(@RequestParam Integer dbId) {

        return svrService.deChnList(dbId);
    }

    @ApiOperation("添加/删除解码通道")
    @PostMapping("/deChn")
    public BaseResult<String> deChn(@Valid @RequestBody DeChnDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.deChn(dto);
    }

    @ApiOperation("获取解码参数")
    @PostMapping("/decParam")
    public BaseResult<DecParamVo> decParam(@Valid @RequestBody DecParamDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.decParam(dto);
    }

    @ApiOperation("设置解码参数")
    @PostMapping("/enDeParam")
    public BaseResult<String> enDeParam(@Valid @RequestBody EnDecParamDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.enDeParam(dto);
    }

    @ApiOperation("PTZ控制")
    @PostMapping("/ptzCtrl")
    public BaseResult<String> ptzCtrl(@Valid @RequestBody PtzCtrlRequestVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.ptz(dto);
    }

    @ApiOperation("启用远程点")
    @PostMapping("/remotePointOn")
    public BaseResult<String> remotePointOn(@Valid @RequestBody RemotePointOnVo vo,BindingResult br) {
         ValidUtils.paramValid(br);

         return svrService.remotePoint(vo);
    }

    @ApiOperation("停用远程点")
    @PostMapping("remotePointOff")
    public BaseResult<String> remotePointOff(@Valid @RequestBody RemotePointOffVo vo,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.remotePointOff(vo);
    }

    @ApiOperation("获取远程点配置")
    @PostMapping("/remoteCfg")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<RemoteCfgVo> remoteCfg(@RequestParam Integer dbId) {

        return svrService.remoteCfg(dbId);
    }

    @ApiOperation("修改远程点配置")
    @PostMapping("/remotePutCfg")
    public BaseResult<String> remotePutCfg(@Valid @RequestBody RemotePutCfgDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.remotePutCfg(dto);
    }

    @ApiOperation("发送双流")
    @PostMapping("/startDual")
    public BaseResult<String> startDual(@Valid @RequestBody StartDualRequestVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.dual(dto);
    }

    @ApiOperation("刻录控制")
    @PostMapping("/burn")
    public BaseResult<String> burn(@Valid @RequestBody BurnDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.burn(dto);
    }

    @ApiOperation("根据时间补刻 如果有刻录任务ID请选择刻录控制接口")
    @PostMapping("/supplementBurn")
    public BaseResult<String> supplementBurn(@Valid @RequestBody SupplementBurnVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.reBurn(dto);
    }

    @ApiOperation("追加刻录任务")
    @PostMapping("/appendBurn")
    public BaseResult<String> appendBurn(@Valid @RequestBody AppendBurnDto dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.appendBurn(dto);
    }

    @ApiOperation("新建刻录任务")
    @PostMapping("/createBurn")
    public BaseResult<String> createBurn(@Valid @RequestBody CreateBurnRequestVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.createBurn(dto);
    }

    @ApiOperation("获取刻录任务")
    @PostMapping("/burnTaskList")
    public BaseResult<GetBurnTaskResponseVo> getBurnTask(@Valid @RequestBody GetBurnTaskRequestVo dto,BindingResult br) {
        ValidUtils.paramValid(br);
        return svrService.burnTaskList(dto);
    }

    @ApiOperation("获取当前刻录状态")
    @PostMapping("/burnInfo")
    public BaseResult<BurnStatesInfoVo> burnInfo(@Valid @RequestBody SvrRequestDto dto,BindingResult br) {
         ValidUtils.paramValid(br);

        return svrService.burnInfo(dto);
    }

    @ApiOperation("DVD仓门控制")
    @PostMapping("/ctrlDvdDoor")
    public BaseResult<String> ctrlDvdDoor(@Valid @RequestBody DvdDoorCtrlVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.dvdDoor(dto);
    }

    @ApiOperation("查询录像")
    @PostMapping("/queryRec")
    public BaseResult<RecListResponse> queryRec(@Valid @RequestBody QueryRecVo dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.recList(dto);
    }

    @ApiOperation("获取画面合成")
    @PostMapping("/getSvrComposePic")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<GetSvrComposePicResponseVo> getSvrComposePic(@RequestParam Integer dbId) {

        return svrService.getMerge(dbId);
    }

    @ApiOperation("设置画面合成")
    @PostMapping("/setSvrComposePic")
    public BaseResult<String> setSvrComposePic(@Valid @RequestBody SetSvrComposePicVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.merge(dto);
    }

    @ApiOperation("获取画面叠加")
    @PostMapping("/getOsd")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    public BaseResult<GetOsdVo> getOsd(@RequestParam Integer dbId) {

        return svrService.getOsd(dbId);
    }

    @ApiOperation("设置画面叠加")
    @PostMapping("/setOsd")
    public BaseResult<String> setOsd(@RequestBody OsdSetVo dto) {

        return svrService.osd(dto);
    }

    @ApiOperation("语音激励控制 3.0协议暂不支持")
    @PostMapping("/setAudioActNty")
    public BaseResult<String> setAudioActNty(@Valid @RequestBody SetAudioActNtyRequestVo dto,BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.audioAct(dto);
    }

    @ApiOperation("获取远程点设备列表")
    @PostMapping("/remoteDevList")
    public BaseResult<RemoteDevListVo> remoteDevList(@Valid @RequestBody RemoteDevListDto dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.remoteDevList(dto);
    }

    @ApiOperation("获取远程点通道列表")
    @PostMapping("/remoteChnList")
    public BaseResult<RemoteChnListVo> remoteChnList(@Valid @RequestBody SvrRequestDto dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return svrService.remoteChnList(dto);
    }




}
