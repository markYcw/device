package com.kedacom.device.api.svr;

import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.device.api.svr.fallback.SvrApiFallbackFactory;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * svr相关操作
 * @author ycw
 * @date 2021/09/07 14:55
 */
@FeignClient(name = "device-server", contextId = "svrApi", path = "/api-device/ums/svr",fallbackFactory = SvrApiFallbackFactory.class)
public interface SvrApi {

    @PostMapping("/pageQuery")
    @ApiOperation(value = "svr分页查询")
    BaseResult<BasePage<SvrEntity>> pageQuery(@RequestBody SvrPageQueryDTO queryDTO);

    @PostMapping("/all")
    @ApiOperation(value = "查询所有SVR")
    BaseResult<List<SvrEntity>> all();

    @PostMapping("/info")
    @ApiOperation(value = "根据id获取svr信息")
    @ApiImplicitParams({@ApiImplicitParam(name="dbId",value = "数据库ID")})
    BaseResult<SvrEntity> info(@RequestParam Integer dbId);

    @PostMapping("/save")
    @ApiOperation(value = "新增svr")
    BaseResult<SvrEntity> save(@Valid @RequestBody SvrEntity entity);

    @PostMapping("/update")
    @ApiOperation(value = "修改svr")
    BaseResult<SvrEntity> update(@RequestBody SvrEntity entity);

    @PostMapping("/delete")
    @ApiOperation(value = "删除svr")
    BaseResult delete(@RequestBody Long[] ids);

    @PostMapping("/loginById")
    @ApiOperation(value = "根据id获取svr信息")
    @ApiImplicitParams({@ApiImplicitParam(name="dbId",value = "数据库ID")})
    BaseResult<SvrLoginVO> loginById(@RequestParam Integer dbId);

    @ApiOperation("登出svr")
    @PostMapping("/logoutById")
    @ApiImplicitParams({@ApiImplicitParam(name="dbId",value = "数据库ID")})
    BaseResult logoutById(@RequestParam Integer dbId);

    @ApiOperation("获取SVR能力集")
    @PostMapping("/svrCap")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",value = "数据库ID")})
    BaseResult<SvrCapVo> svrCap(@RequestParam Integer dbId);

    @ApiOperation("获取SVR时间")
    @PostMapping("/getBurnTime")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value="数据库ID")})
    BaseResult<String> getBurnTime(@RequestParam Integer dbId);

    @ApiOperation("搜索编解码设备")
    @PostMapping("/searchDev")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",value = "数据库ID")})
    BaseResult<String> searchDev(@RequestParam Integer dbId);

    @ApiOperation("获取编码通道列表")
    @PostMapping("/enChnList")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",value = "数据库ID")})
    BaseResult<EnChnListVo> enChnList(@RequestParam Integer dbId);

    @ApiOperation("添加/删除编码通道")
    @PostMapping("/enChn")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",value = "数据库ID")})
    BaseResult<String> enChn(@RequestBody EnChnDto enChnDto);

    @ApiOperation("获取编码器的预置位")
    @PostMapping("/getIpcItem")
    BaseResult<CpResetVo> getIpcItem(GetIpcItemRequestVo dto);

    @ApiOperation("修改编码器预置位")
    @PostMapping("/CpReset")
    BaseResult<String> CpReset(CpResetDto dto);

    @ApiOperation("获取解码通道列表")
    @PostMapping("/deChnList")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",value = "数据库ID")})
    BaseResult<DeChnListVo> deChnList(@RequestParam Integer dbId);


    @ApiOperation("添加/删除解码通道")
    @PostMapping("/enChn")
    BaseResult<String> deChn(@RequestBody DeChnDto dto);

    @ApiOperation("获取解码参数")
    @PostMapping("/decParam")
    BaseResult<DecParamVo> decParam(@RequestBody DecParamDto dto);

    @ApiOperation("设置解码参数")
    @PostMapping("/enDeParam")
    BaseResult<String> enDeParam(@RequestBody EnDecParamDto dto);

    @ApiOperation("PTZ控制")
    @PostMapping("/ptzCtrl")
    BaseResult<String> ptzCtrl(@RequestBody PtzCtrlRequestVo dto);

    @ApiOperation("启用远程点")
    @PostMapping("/remotePointOn")
    BaseResult<String> remotePointOn(@RequestBody RemotePointOnVo dto);

    @ApiOperation("停用远程点")
    @PostMapping("remotePointOff")
    public BaseResult<String> remotePointOff(@RequestBody RemotePointOffVo vo);

    @ApiOperation("获取远程点配置")
    @PostMapping("/remoteCfg")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    BaseResult<RemoteCfgVo> remoteCfg(@RequestParam Integer dbId);

    @ApiOperation("修改远程点配置")
    @PostMapping("/remotePutCfg")
    BaseResult<RemoteCfgVo> remotePutCfg(@RequestBody RemotePutCfgDto dto);

    @ApiOperation("发送双流")
    @PostMapping("/startDual")
    BaseResult<String> startDual(@RequestBody StartDualRequestVo dto);

    @ApiOperation("刻录控制")
    @PostMapping("/burn")
    BaseResult<String> burn(@RequestBody BurnDto dto);

    @ApiOperation("补刻")
    @PostMapping("/supplementBurn")
    BaseResult<String> supplementBurn(@RequestBody SupplementBurnVo dto);

    @ApiOperation("追加刻录任务")
    @PostMapping("/appendBurn")
    BaseResult<String> appendBurn(@RequestBody AppendBurnDto dto);

    @ApiOperation("新建刻录任务")
    @PostMapping("/createBurn")
    BaseResult<String> createBurn(@RequestBody CreateBurnRequestVo dto);

    @ApiOperation("获取刻录任务")
    @PostMapping("/getBurnTask")
    BaseResult<GetBurnTaskResponseVo> getBurnTask(@RequestBody GetBurnTaskRequestVo dto);

    @ApiOperation("DVD仓门控制")
    @PostMapping("/ctrlDvdDoor")
    BaseResult<String> ctrlDvdDoor(@RequestBody DvdDoorCtrlVo dto);

    @ApiOperation("查询录像")
    @PostMapping("/queryRec")
    BaseResult<List<RecInfoVo>> queryRec(@RequestBody QueryRecVo dto);

    @ApiOperation("获取画面合成")
    @PostMapping("/getSvrComposePic")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    BaseResult<GetSvrComposePicResponseVo> getSvrComposePic(@RequestParam Integer dbId);

    @ApiOperation("设置画面合成")
    @PostMapping("/setSvrComposePic")
    BaseResult<String> setSvrComposePic(@RequestBody SetSvrComposePicVo dto);

    @ApiOperation("获取画面叠加")
    @PostMapping("/getOsd")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    BaseResult<GetOsdVo> getOsd(@RequestParam Integer dbId);

    @ApiOperation("设置画面叠加")
    @PostMapping("/setOsd")
    BaseResult<String> setOsd(@RequestBody OsdSetVo dto);

    @ApiOperation("语音激励控制")
    @PostMapping("/setAudioActNty 3.0暂不支持")
    BaseResult<String> setAudioActNty(@RequestBody SetAudioActNtyRequestVo dto);

    @PostMapping("/hb")
    @ApiOperation(value = "发送心跳")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId", value = "数据库ID")})
    BaseResult<String> hb(@RequestParam Integer dbId);

    @ApiOperation("获取当前刻录状态")
    @PostMapping("/burnInfo")
    BaseResult<BurnStatesInfoVo> burnInfo(@RequestBody SvrRequestDto dto);

    @ApiOperation("获取远程点设备列表")
    @PostMapping("/remoteDevList")
    BaseResult<RemoteDevListVo> remoteDevList(@RequestBody RemoteDevListDto dto);

    @ApiOperation("获取远程点通道列表")
    @PostMapping("/remoteChnList")
    BaseResult<RemoteChnListVo> remoteChnList(@RequestBody SvrRequestDto dto);

}
