package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.mp.mcu.McuRequestDTO;
import com.kedacom.mp.mcu.pojo.ConfTemplateInfoVo;
import com.kedacom.mp.mcu.request.*;
import com.kedacom.mp.mcu.response.*;

/**
 * @author hxj
 * @date: 2021/8/13 13:50
 * @description 会议平台业务接口
 */
public interface McuService {

    BaseResult<McuLoginVO> login(McuRequestDTO dto);

    BaseResult logout(McuRequestDTO dto);

    BaseResult<AccountVo> account(McuAccountDTO dto);

    BaseResult<McuConfsVO> confs(McuConfsDTO dto);

    BaseResult<McuConfTemplateVO> templates(McuTemplatesDTO dto);

    BaseResult<McuConfInfoVO> confinfo(McuConfInfoDTO dto);

    BaseResult<McuConfVO> conf(McuConfDTO dto);

    BaseResult confTemplate(McuConfTemplateDTO dto);

    BaseResult<McuMtmembersVO> mtMembers(McuMtMembersDTO dto);

    BaseResult<McuMtVO> mt(McuMtDTO dto);

    BaseResult mtCall(McuMtCallDTO dto);

    BaseResult speaker(McuSpeakerDTO dto);

    BaseResult chairman(McuChairmanDTO dto);

    BaseResult silence(McuSilenceDTO dto);

    BaseResult mute(McuMuteDTO dto);

    BaseResult volume(McuVolumeDTO dto);

    BaseResult dual(McuDualDTO dto);

    BaseResult videoMix(McuVideoMixDTO dto);

    BaseResult audioMix(McuAudioMixDTO dto);

    BaseResult<McuAudioMixMemberVO> audioMixMember(McuAudioMixMemberDTO dto);

    BaseResult rec(McuRecDTO dto);

    BaseResult<McuTvWallsVO> tvWalls(McuRequestDTO dto);

    BaseResult tvwall(McuTvWallDTO dto);

    BaseResult exchange(McuExchangeDTO dto);

    BaseResult message(McuMessageDTO dto);

    BaseResult<String> hb(McuRequestDTO dto);

    BaseResult<ConfTemplateVo> confTemplates(ConfTemplateDTO dto);

    BaseResult<ConfTemplateInfoVo> templateInfo(GetConfTemplateDTO dto);

    BaseResult<AccountsVo> accounts(AccountsDto dto);

    BaseResult<McuRecStatusVO> recState(McuRecStatusDTO dto);

    BaseResult<DepartmentsVO> departments(McuRequestDTO dto);

    BaseResult<DepartmentVO> department(DepartmentDTO dto);
}
