package com.kedacom.device.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.BaseResult;
import com.kedacom.common.utils.ValidUtils;
import com.kedacom.device.core.service.MtService;
import com.kedacom.mt.GetDumbMuteVo;
import com.kedacom.mt.StartMeetingMtVo;
import com.kedacom.mt.TerminalQuery;
import com.kedacom.mt.TerminalVo;
import com.kedacom.mt.response.GetMtStatusResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/30
 */
@RestController
@RequestMapping("ums/mt")
@Api(value = "会议终端控制", tags = "会议终端控制")
public class MtController {

    @Resource
    MtService mtService;

    @ApiOperation("分页查询终端信息")
    @PostMapping("/mtList")
    public BaseResult<Page<TerminalVo>> mtList(@RequestBody TerminalQuery terminalQuery){

        Page<TerminalVo> page = mtService.mtPage(terminalQuery);

        return BaseResult.succeed("查询终端列表成功", page);
    }

    @ApiOperation("查询终端信息")
    @PostMapping("/queryMt")
    public BaseResult<TerminalVo> queryMt(@RequestParam Integer dbId) {

        if (dbId == null) {
            return BaseResult.failed("终端id不能为空");
        }
        TerminalVo terminalVo = mtService.queryMt(dbId);
        if (terminalVo == null) {
            return BaseResult.failed("查询终端信息为空");
        }

        return BaseResult.succeed("查询终端信息成功", terminalVo);
    }

    @ApiOperation("保存终端信息")
    @PostMapping("/saveMt")
    public BaseResult<TerminalVo> saveMt(@Valid @RequestBody TerminalVo terminalVo, BindingResult br) {

        ValidUtils.paramValid(br);
        if (mtService.isRepeatIp(terminalVo)) {
            return BaseResult.failed("终端IP重复，请重新填写");
        }
        if (mtService.isRepeatName(terminalVo)) {
            return BaseResult.failed("终端名称重复看，请重新填写");
        }

        return BaseResult.succeed("保存成功", mtService.saveMt(terminalVo));
    }

    @ApiOperation("保存终端信息")
    @PostMapping("/saveMtFeign")
    public BaseResult<TerminalVo> saveMtFeign(@RequestBody TerminalVo terminalVo) {

        if (mtService.isRepeatName(terminalVo)) {
            return BaseResult.failed("终端名称重复看，请重新填写");
        }
        if (mtService.isRepeatIp(terminalVo)) {
            return BaseResult.failed("终端IP重复，请重新填写");
        }

        return BaseResult.succeed("保存成功", mtService.saveMt(terminalVo));
    }

    @ApiOperation("修改终端信息")
    @PostMapping("/updateMt")
    public BaseResult<String> updateMt(@Valid @RequestBody TerminalVo terminalVo, BindingResult br) {

        ValidUtils.paramValid(br);
        if (mtService.isRepeatIp(terminalVo)) {
            return BaseResult.failed("终端IP重复，请重新填写");
        }
        if (mtService.isRepeatName(terminalVo)) {
            return BaseResult.failed("终端名称重复看，请重新填写");
        }
        if (mtService.updateMt(terminalVo)) {
            return BaseResult.succeed("修改成功");
        }

        return BaseResult.failed("修改失败");
    }

    @ApiOperation("修改终端信息")
    @PostMapping("/updateMtFeign")
    public BaseResult<String> updateMtFeign(@RequestBody TerminalVo terminalVo) {

        if (mtService.isRepeatName(terminalVo)) {
            return BaseResult.failed("终端名称重复看，请重新填写");
        }
        if (mtService.isRepeatIp(terminalVo)) {
            return BaseResult.failed("终端IP重复，请重新填写");
        }
        if (mtService.updateMt(terminalVo)) {
            return BaseResult.succeed("修改成功");
        }

        return BaseResult.failed("修改失败");
    }

    @ApiOperation("删除终端信息")
    @PostMapping("/deleteMt")
    public BaseResult<String> deleteMt(@RequestBody List<Integer> dbIds) {

        if (CollectionUtil.isEmpty(dbIds)) {
            return BaseResult.failed("删除终端不能为空");
        }
        if (mtService.deleteMt(dbIds)) {
            return BaseResult.succeed("终端删除成功");
        }

        return BaseResult.failed("终端删除失败");
    }

    @ApiOperation("根据数据库ID登录设备")
    @PostMapping("/loginById")
    public BaseResult<Integer> loginById(@RequestParam Integer dbId) {

        return BaseResult.succeed("终端登录成功", mtService.loginById(dbId));
    }

    @ApiOperation("根据数据库ID登出设备")
    @PostMapping("/logOutById")
    public BaseResult<Boolean> logOutById(@RequestParam Integer dbId) {

        return BaseResult.succeed("终端登录成功", mtService.logOutById(dbId));
    }

    @ApiOperation("发送心跳")
    @PostMapping("/heartBeat")
    public BaseResult<String> heartBeat(@RequestParam Integer dbId) {

        if (mtService.heartBeat(dbId)) {
            return BaseResult.succeed("发送心跳成功");
        }

        return BaseResult.failed("发送心跳失败");
    }

    @ApiOperation("获取终端类型")
    @PostMapping("/getMTType")
    public BaseResult<Integer> getMTType(@RequestParam Integer dbId) {

        Integer mtType = mtService.getMtType(dbId);
        if (mtType == null) {
            return BaseResult.failed("获取终端类型失败");
        }

        return BaseResult.succeed("获取终端类型成功", mtType);
    }

    @ApiOperation("开启点对点会议")
    @PostMapping("/startP2P")
    public BaseResult<Boolean> startP2P(@RequestBody StartMeetingMtVo startMeetingMtVo) {

        if (mtService.check(startMeetingMtVo.getCallType(), startMeetingMtVo.getKey(), startMeetingMtVo.getValue())) {
            return BaseResult.failed("对端id为:" + startMeetingMtVo.getKey() + "的设备不存在");
        }
        if (mtService.startP2P(startMeetingMtVo)) {
            return BaseResult.succeed("开启点对点会议成功", true);
        }

        return BaseResult.failed("开启点对点会议失败");
    }

    @ApiOperation("停止点对点会议")
    @PostMapping("/stopP2P")
    public BaseResult<Boolean> stopP2P(@RequestParam Integer dbId) {

        if (mtService.stopP2P(dbId)) {
            return BaseResult.succeed("停止点对点会议成功", true);
        }

        return BaseResult.failed("停止点对点会议失败");
    }

    @ApiOperation("获取终端状态(不支持五代终端)")
    @PostMapping("/getMtStatus")
    public BaseResult<GetMtStatusResponseVo> getMtStatus(@RequestParam Integer dbId) {

        GetMtStatusResponseVo mtStatus = mtService.getMtStatus(dbId);
        if (mtStatus == null) {
            return BaseResult.failed("获取终端状态失败");
        }

        return BaseResult.succeed("获取终端状态成功", mtStatus);
    }

    @ApiOperation("静音/哑音设置")
    @PostMapping("/setDumbMute")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "mute", value = "true:静音控制,false:哑音控制", required = true),
            @ApiImplicitParam(name = "open", value = "静音/哑音开关：true开启 false关闭", required = true)})
    public BaseResult<Boolean> setDumbMute(@RequestParam Integer dbId, @RequestParam Boolean mute, @RequestParam String open) {

        String type = mute ? "静音" : "哑音";
        if (mtService.setDumbMute(dbId, mute, open)) {
            return BaseResult.succeed(type + "设置成功", true);
        }

        return BaseResult.failed(type + "设置失败");
    }

    @ApiOperation("静音/哑音状态获取")
    @PostMapping("/getDumbMute")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "mute", value = "true:静音控制,false:哑音控制", required = true)})
    public BaseResult<GetDumbMuteVo> getDumbMute(@RequestParam Integer dbId, @RequestParam String mute) {

        String type = "true".equals(mute) ? "静音" : "哑音";
        GetDumbMuteVo dumbMute = mtService.getDumbMute(dbId, mute);
        if (dumbMute == null) {
            return BaseResult.failed(type + "状态获取失败");
        }

        return BaseResult.succeed(type + "状态获取成功", dumbMute);
    }

    @ApiOperation("音量控制(不支持五代终端)")
    @PostMapping("/volumeCtrl")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "type", value = "1:扬声器 2:麦克", required = true),
            @ApiImplicitParam(name = "volume", value = "音量大小，最大31", required = true)})
    public BaseResult<Boolean> volumeCtrl(@RequestParam Integer dbId, @RequestParam Integer type, @RequestParam Integer volume) {

        if (mtService.setVolume(dbId, type, volume)) {
            return BaseResult.succeed("音量设置成功");
        }

        return BaseResult.failed("音量设置失败");
    }

    @ApiOperation("音量获取(不支持五代终端)")
    @PostMapping("/getVolume")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "type", value = "1:扬声器 2:麦克", required = true)})
    public BaseResult<String> getVolume(@RequestParam Integer dbId, @RequestParam Integer type) {

        String typeStr = type == 1 ? "扬声器" : "麦克风";

        return BaseResult.succeed(typeStr + "音量获取成功", mtService.getVolume(dbId, type));
    }

    @ApiOperation("请求关键帧")
    @PostMapping("/keyframe")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "streamType", value = "码流类型, 0: 本地(不支持), 1：远端(不支持), 2:本地视频到任意地址, " +
                    "3:远端视频到任意地址, 4:本地音频到任意地址, 5:远端音频到任意地址, 6:本地双流到任意地址, 7:远端双流到任意地址", required = true)})
    public BaseResult<String> keyframe(@RequestParam Integer dbId, @RequestParam Integer streamType) {

        if (mtService.keyframe(dbId, streamType)) {
            return BaseResult.succeed("请求关键帧成功");
        }

        return BaseResult.failed("请求关键帧失败");
    }

    @ApiOperation("双流控制")
    @PostMapping("/mtStarteDual")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "start", value = "开始发送还是停止:true开始发送 false停止发送", required = true),
            @ApiImplicitParam(name = "isLocal", value = "true: 本地终端 false: 远端终端(远端暂不支持)", required = true)})
    public BaseResult<Boolean> mtStarteDual(@RequestParam Integer dbId, @RequestParam boolean start, @RequestParam boolean isLocal) {

        if (mtService.mtStartDual(dbId, start, isLocal)) {
            return BaseResult.succeed("双流控制请求成功", true);
        }

        return BaseResult.failed("双流控制请求失败");
    }

    @ApiOperation("ptz控制")
    @PostMapping("/ptzCtrl")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "type", value = "0:开始，1:停止", required = true),
            @ApiImplicitParam(name = "ptzCmd", value = "控制命令1:上 2:下 3:左 4:右 5:自动聚焦 6:焦距大 7:焦距小 8:zoomin  9:zoomout 10：亮 11：暗", required = true)})
    public BaseResult<Boolean> ptzCtrl(@RequestParam Integer dbId, @RequestParam Integer type, @RequestParam Integer ptzCmd) {

        if (mtService.ptzCtrl(dbId, type, ptzCmd)) {
            return BaseResult.succeed("ptz控制成功");
        }

        return BaseResult.failed("ptz控制失败");
    }

    @ApiOperation("设置画面显示模式(不支持五代终端)")
    @PostMapping("/setPipMode")
    @ApiImplicitParams({@ApiImplicitParam(name = "dbId",  value = "数据库ID", required = true),
            @ApiImplicitParam(name = "mode", value = "0：单屏双显 1：双屏双显 2：单屏三显", required = true)})
    public BaseResult<Boolean> setPipMode(@RequestParam Integer dbId, @RequestParam Integer mode) {

        if (mtService.setPipMode(dbId, mode)) {
            return BaseResult.succeed("设置画面显示模式成功");
        }

        return BaseResult.failed("设置画面显示模式失败");
    }

    @ApiOperation(value = "终端通知")
    @PostMapping("/mtNotify")
    public BaseResult<Void> mtNotify(@RequestBody String notify) {

        if (StringUtils.isEmpty(notify)) {
            return BaseResult.failed("接收终端通知信息为空");
        }
        mtService.mtNotify(notify);

        return BaseResult.succeed("终端通知消费成功");
    }

    @ApiOperation(value = "PING")
    @PostMapping("/ping")
    public BaseResult<Boolean> ping(@RequestParam Integer dbId) {

        if (mtService.ping(dbId)) {
            return BaseResult.succeed("ping 成功");
        }

        return BaseResult.failed("ping 成功");
    }

}
