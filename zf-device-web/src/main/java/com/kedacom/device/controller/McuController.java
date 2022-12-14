package com.kedacom.device.controller;

import com.kedacom.BaseResult;
import com.kedacom.device.common.utils.ValidUtils;
import com.kedacom.device.core.service.McuService;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.ConfTemplateInfoVo;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author hxj
 * @date: 2021/8/13 13:48
 * @description 会议平台操作接口
 */
@RestController
@RequestMapping("ums/mcu")
@Api(value = "会议平台操作接口", tags = "会议平台操作接口")
public class McuController {

    @Autowired
    private McuService mcuService;

    @ApiOperation("登录平台")
    @PostMapping("/login")
    public BaseResult<McuLoginVO> login(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.login(dto);
    }

    @ApiOperation("登出平台")
    @PostMapping("/logout")
    public BaseResult logout(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.logout(dto);
    }

    @ApiOperation("发送心跳 登录会议平台以后必须每9分钟调用一次这个接口，否则有可能导致C++与MCU的token失效，然后你再去尝试调用MCU接口就会失败 接口调用成功会返回0")
    @PostMapping("/hb")
    public BaseResult<Integer> hb(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.hb(dto);
    }

    @ApiOperation("创建/删除账户 注意！不允许删除号码组的账号，只能删除自己创建的账号")
    @PostMapping("/account")
    public BaseResult<AccountVo> account(@Valid @RequestBody McuAccountDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.account(dto);
    }

    @ApiOperation("查询所有账户")
    @PostMapping("/accounts")
    public BaseResult<AccountsVo> accounts(@Valid @RequestBody AccountsDto dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.accounts(dto);
    }

    @ApiOperation("获取会议列表")
    @PostMapping("/confs")
    public BaseResult<McuConfsVO> confs(@Valid @RequestBody McuConfsDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.confs(dto);
    }

    @ApiOperation("获取会议模板列表")
    @PostMapping("/templates")
    public BaseResult<McuConfTemplateVO> templates(@Valid @RequestBody McuTemplatesDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.templates(dto);
    }

    @ApiOperation("获取会议信息")
    @PostMapping("/confinfo")
    public BaseResult<McuConfInfoVO> confinfo(@Valid @RequestBody McuConfInfoDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.confinfo(dto);
    }

    @ApiOperation("创建/结束会议")
    @PostMapping("/conf")
    public BaseResult<McuConfVO> conf(@Valid @RequestBody McuConfDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.conf(dto);
    }

    @ApiOperation("获取会议模板信息")
    @PostMapping("/templateInfo")
    public BaseResult<ConfTemplateInfoVo> templateInfo(@Valid @RequestBody GetConfTemplateDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.templateInfo(dto);
    }

    @ApiOperation("创建/删除会议模板")
    @PostMapping("/confTemplates")
    public BaseResult<ConfTemplateVo> confTemplates(@Valid @RequestBody ConfTemplateDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.confTemplates(dto);
    }

    @ApiOperation("开启会议模板")
    @PostMapping("/confTemplate")
    public BaseResult confTemplate(@Valid @RequestBody McuConfTemplateDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.confTemplate(dto);
    }

    @ApiOperation("获取与会成员")
    @PostMapping("/mtMembers")
    public BaseResult<McuMtmembersVO> mtMembers(@Valid @RequestBody McuMtMembersDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.mtMembers(dto);
    }

    @ApiOperation("添加/删除终端")
    @PostMapping("/mt")
    public BaseResult<McuMtVO> mt(@Valid @RequestBody McuMtDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.mt(dto);
    }

    @ApiOperation("呼叫/挂断终端")
    @PostMapping("/mtCall")
    public BaseResult mtCall(@Valid @RequestBody McuMtCallDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.mtCall(dto);
    }

    @ApiOperation("设置/取消发言人")
    @PostMapping("/speaker")
    public BaseResult speaker(@Valid @RequestBody McuSpeakerDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.speaker(dto);
    }

    @ApiOperation("设置/取消主席")
    @PostMapping("/chairman")
    public BaseResult chairman(@Valid @RequestBody McuChairmanDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.chairman(dto);
    }

    @ApiOperation("静音")
    @PostMapping("/silence")
    public BaseResult silence(@Valid @RequestBody McuSilenceDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.silence(dto);
    }

    @ApiOperation("哑音")
    @PostMapping("/mute")
    public BaseResult mute(@Valid @RequestBody McuMuteDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.mute(dto);
    }

    @ApiOperation("调节音量")
    @PostMapping("/volume")
    public BaseResult volume(@Valid @RequestBody McuVolumeDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.volume(dto);
    }

    @ApiOperation("终端双流控制")
    @PostMapping("/dual")
    public BaseResult dual(@Valid @RequestBody McuDualDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.dual(dto);
    }

    @ApiOperation("开始/停止画面合成")
    @PostMapping("/videoMix")
    public BaseResult videoMix(@Valid @RequestBody McuVideoMixDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.videoMix(dto);
    }

    @ApiOperation("开始/停止混音")
    @PostMapping("/audioMix")
    public BaseResult audioMix(@Valid @RequestBody McuAudioMixDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.audioMix(dto);
    }

    @ApiOperation("添加/删除混音成员")
    @PostMapping("/audioMixMember")
    public BaseResult<String> audioMixMember(@Valid @RequestBody McuAudioMixMemberDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.audioMixMember(dto);
    }

    @ApiOperation("开始/暂停/恢复/停止录像")
    @PostMapping("/rec")
    public BaseResult rec(@Valid @RequestBody McuRecDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.rec(dto);
    }

    @ApiOperation("获取录像状态")
    @PostMapping("/recState")
    public BaseResult<McuRecStatusVO> recState(@Valid @RequestBody McuRecStatusDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.recState(dto);
    }

    @ApiOperation("获取电视墙列表")
    @PostMapping("/tvWalls")
    public BaseResult<McuTvWallsVO> tvWalls(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.tvWalls(dto);
    }

    @ApiOperation("开始/停止上电视墙")
    @PostMapping("/tvwall")
    public BaseResult tvwall(@Valid @RequestBody McuTvWallDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.tvwall(dto);
    }

    @ApiOperation("开始/停止码流交换 第一：选看的目的终端必须是主席！！！ 第二：选看的画面合成必须是非广播的！！！如果合成是广播的，那就没必要选看，因为已经广播过去了")
    @PostMapping("/exchange")
    public BaseResult exchange(@Valid @RequestBody McuExchangeDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.exchange(dto);
    }

    @ApiOperation("发送短消息")
    @PostMapping("/message")
    public BaseResult message(@Valid @RequestBody McuMessageDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.message(dto);
    }

    @ApiOperation("查询所有部门")
    @PostMapping("/departments")
    public BaseResult<DepartmentsVO> departments(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.departments(dto);
    }

    @ApiOperation("创建/删除部门")
    @PostMapping("/department")
    public BaseResult<DepartmentVO> department(@Valid @RequestBody DepartmentDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.department(dto);
    }


    @ApiOperation("获取录像列表")
    @PostMapping("/recs")
    public BaseResult<RecsVO> recs(@Valid @RequestBody RecsDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.recs(dto);
    }


    @ApiOperation("获取vrs列表")
    @PostMapping("/vrs")
    public BaseResult<VrsVO> vrs(@Valid @RequestBody McuRequestDTO dto, BindingResult br) {
        ValidUtils.paramValid(br);

        return mcuService.vrs(dto);
    }



}
