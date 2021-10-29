package com.kedacom.device.api.mcu;

import com.kedacom.BaseResult;
import com.kedacom.device.api.mcu.fallback.McuApiFallbackFactory;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.ConfTemplateInfoVo;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author hxj
 * @Date: 2021/8/12 13:37
 * @Description 会议平台操作qpi
 */
@FeignClient(name = "device-server", contextId = "mcuApi", path = "/api-device/ums/mcu", fallbackFactory = McuApiFallbackFactory.class)
public interface McuApi {

    @ApiOperation("登录平台")
    @PostMapping("/login")
    BaseResult<McuLoginVO> login(@RequestBody McuRequestDTO dto);

    @ApiOperation("登出平台")
    @PostMapping("/logout")
    BaseResult logout(@RequestBody McuRequestDTO dto);

    @ApiOperation("创建/删除账户")
    @PostMapping("/account")
    BaseResult<AccountVo> account(@RequestBody McuAccountDTO dto);

    @ApiOperation("查询所有账户")
    @PostMapping("/accounts")
    BaseResult<AccountsVo> accounts(@Valid @RequestBody AccountsDto dto, BindingResult br);

    @ApiOperation("获取会议列表")
    @PostMapping("/confs")
    BaseResult<McuConfsVO> confs(@RequestBody McuConfsDTO dto);

    @ApiOperation("获取会议模板列表")
    @PostMapping("/templates")
    BaseResult<McuConfTemplateVO> templates(@RequestBody McuTemplatesDTO dto);

    @ApiOperation("获取会议信息")
    @PostMapping("/confinfo")
    BaseResult<McuConfInfoVO> confinfo(@RequestBody McuConfInfoDTO dto);

    @ApiOperation("创建/删除会议")
    @PostMapping("/conf")
    BaseResult<McuConfVO> conf(@RequestBody McuConfDTO dto);

    @ApiOperation("获取会议模板信息")
    @PostMapping("/templateInfo")
    BaseResult<ConfTemplateInfoVo> templateInfo(@Valid @RequestBody GetConfTemplateDTO dto);

    @ApiOperation("开启会议模板")
    @PostMapping("/confTemplate")
    BaseResult confTemplate(@RequestBody McuConfTemplateDTO dto);

    @ApiOperation("获取与会成员")
    @PostMapping("/mtMembers")
    BaseResult<McuMtmembersVO> mtMembers(@RequestBody McuMtMembersDTO dto);

    @ApiOperation("添加/删除终端")
    @PostMapping("/mt")
    BaseResult<McuMtVO> mt(@RequestBody McuMtDTO dto);

    @ApiOperation("呼叫/挂断终端")
    @PostMapping("/mtCall")
    BaseResult mtCall(@RequestBody McuMtCallDTO dto);

    @ApiOperation("设置/取消发言人")
    @PostMapping("/speaker")
    BaseResult speaker(@RequestBody McuSpeakerDTO dto);

    @ApiOperation("设置/取消主席")
    @PostMapping("/chairman")
    BaseResult chairman(@RequestBody McuChairmanDTO dto);

    @ApiOperation("静音")
    @PostMapping("/silence")
    BaseResult silence(@RequestBody McuSilenceDTO dto);

    @ApiOperation("哑音")
    @PostMapping("/mute")
    BaseResult mute(@RequestBody McuMuteDTO dto);

    @ApiOperation("调节音量")
    @PostMapping("/volume")
    BaseResult volume(@RequestBody McuVolumeDTO dto);

    @ApiOperation("终端双流控制")
    @PostMapping("/dual")
    BaseResult dual(@RequestBody McuDualDTO dto);

    @ApiOperation("开始/停止画面合成")
    @PostMapping("/videoMix")
    BaseResult videoMix(@RequestBody McuVideoMixDTO dto);

    @ApiOperation("开始/停止混音")
    @PostMapping("/audioMix")
    BaseResult audioMix(@RequestBody McuAudioMixDTO dto);

    @ApiOperation("添加/删除混音成员")
    @PostMapping("/audioMixMember")
    BaseResult<String> audioMixMember(@RequestBody McuAudioMixMemberDTO dto);

    @ApiOperation("开始/暂停/恢复/停止录像")
    @PostMapping("/rec")
    BaseResult rec(@RequestBody McuRecDTO dto);

    @ApiOperation("获取录像状态")
    @PostMapping("/recState")
    BaseResult<McuRecStatusVO> recState(@Valid @RequestBody McuRecStatusDTO dto);

    @ApiOperation("获取电视墙列表")
    @PostMapping("/tvWalls")
    BaseResult<McuTvWallsVO> tvWalls(@RequestBody McuRequestDTO dto);

    @ApiOperation("开始/停止上电视墙")
    @PostMapping("/tvwall")
    BaseResult tvwall(@RequestBody McuTvWallDTO dto);

    @ApiOperation("开始/停止码流交换")
    @PostMapping("/exchange")
    BaseResult exchange(@RequestBody McuExchangeDTO dto);

    @ApiOperation("发送短消息")
    @PostMapping("/message")
    BaseResult message(@RequestBody McuMessageDTO dto);

    @ApiOperation("发送心跳")
    @PostMapping("/hb")
    BaseResult<Integer> hb(@RequestBody McuRequestDTO dto);

    @ApiOperation("创建/删除会议模板")
    @PostMapping("/confTemplates")
    BaseResult<ConfTemplateVo> confTemplates(@RequestBody ConfTemplateDTO dto);

    @ApiOperation("查询所有部门")
    @PostMapping("/departments")
    BaseResult<DepartmentsVO> departments(@Valid @RequestBody McuRequestDTO dto);

    @ApiOperation("创建/删除部门")
    @PostMapping("/department")
    BaseResult<DepartmentVO> department(@Valid @RequestBody DepartmentDTO dto);

    @ApiOperation("获取录像列表")
    @PostMapping("/recs")
    BaseResult<RecsVO> recs(@Valid @RequestBody RecsDTO dto, BindingResult br);

}
