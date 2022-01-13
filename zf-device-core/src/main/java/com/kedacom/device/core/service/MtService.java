package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kedacom.mt.GetDumbMuteVo;
import com.kedacom.mt.StartMeetingMtVo;
import com.kedacom.mt.TerminalQuery;
import com.kedacom.mt.TerminalVo;
import com.kedacom.mt.response.GetMtStatusResponseVo;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/30
 */
public interface MtService {

    /**
     * 分页查询终端信息
     * @param terminalQuery
     * @return
     */
    Page<TerminalVo> mtPage(TerminalQuery terminalQuery);

    /**
     * 查询终端信息
     * @param dbId
     * @return
     */
    TerminalVo queryMt(Integer dbId);

    /**
     * 查询所有 mtId 不为空的终端 id
     * @return
     */
    List<Integer> queryIdsByMtId();

    /**
     * 终端IP判重
     * @param terminalVo
     * @return
     */
    boolean isRepeatIp(TerminalVo terminalVo);

    /**
     * 终端名称判重
     * @param terminalVo
     * @return
     */
    boolean isRepeatName(TerminalVo terminalVo);

    /**
     * 保存终端信息
     * @param terminalVo
     * @return
     */
    TerminalVo saveMt(TerminalVo terminalVo);

    /**
     * 修改终端信息
     * @param terminalVo
     * @return
     */
    boolean updateMt(TerminalVo terminalVo);

    /**
     * 删除终端信息
     * @param dbIds
     * @return
     */
    boolean deleteMt(List<Integer> dbIds);

    /**
     * 终端登录
     * @param dbId
     * @return
     */
    Integer loginById(Integer dbId);

    /**
     * 终端退出
     * @param dbId
     * @return
     */
    boolean logOutById(Integer dbId);

    /**
     * 发送心跳
     * @param dbId
     * @return
     */
    boolean heartBeat(Integer dbId);

    /**
     * 获取终端类型
     * @param dbId
     * @return
     */
    Integer getMtType(Integer dbId);

    /**
     * 开启点对点会议
     * @param startMeetingMtVo
     * @return
     */
    boolean startP2P(StartMeetingMtVo startMeetingMtVo);

    /**
     * 停止点对点会议
     * @param dbId
     * @return
     */
    boolean stopP2P(Integer dbId);

    /**
     * 获取终端状态
     * @param dbId
     */
    GetMtStatusResponseVo getMtStatus(Integer dbId);

    /**
     * 静音哑音设置
     * @param dbId
     * @param mute
     * @param open
     * @return
     */
    boolean setDumbMute(Integer dbId, Boolean mute, String open);

    /**
     * 静音哑音状态获取
     * @param dbId
     * @param mute
     * @return
     */
    GetDumbMuteVo getDumbMute(Integer dbId, String mute);

    /**
     * 音量控制
     * @param dbId
     * @param type
     * @param volume
     * @return
     */
    boolean setVolume(Integer dbId, Integer type, Integer volume);

    /**
     * 音量获取
     * @param dbId
     * @param type
     * @return
     */
    String getVolume(Integer dbId, Integer type);

    /**
     * 请求关键帧
     * @param dbId
     * @param streamType
     * @return
     */
    boolean keyframe(Integer dbId, Integer streamType);

    /**
     * 双流控制
     * @param dbId
     * @param type
     * @param isLocal
     * @return
     */
    boolean mtStartDual(Integer dbId, boolean type, boolean isLocal);

    /**
     * ptz控制
     * @param dbId
     * @param type
     * @param ptzCmd
     * @return
     */
    boolean ptzCtrl(Integer dbId, Integer type, Integer ptzCmd);

    /**
     * 设置画面显示模式
     * @param dbId
     * @param mode
     * @return
     */
    boolean setPipMode(Integer dbId, Integer mode);

    /**
     * 终端通知
     * @param notify
     * @return
     */
    void mtNotify(String notify);

    /**
     * ping
     * @param dbId
     * @return
     */
    boolean ping(Integer dbId);

    /**
     * 校验开会参数
     * @param callType
     * @param value
     * @return
     */
    boolean check(Integer callType, String value);

}
