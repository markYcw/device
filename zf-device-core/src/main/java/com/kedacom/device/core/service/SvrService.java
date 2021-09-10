package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.vo.*;

/**
 * svr
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-09-06 10:48:30
 */
public interface SvrService extends IService<SvrEntity> {

    /**
     * SVR分页接口
     * @param queryDTO
     * @return
     */
    BaseResult<BasePage<SvrEntity>> pageQuery(SvrPageQueryDTO queryDTO);

    /***
     * SVR通知
     * @param notify
     */
    void svrNotify(String notify);

    /**
     * 根据数据库ID登录SVR
     * @param id
     * @return
     */
    BaseResult<SvrLoginVO> loginById(Integer id);

    /**
     * 根据数据库ID登出SVR
     * @param id
     * @return
     */
    BaseResult logoutById(Integer id);

    /**
     * 获取svr能力集
     * @param dbId
     * @return
     */
    BaseResult<SvrCapVo> svrCap(Integer dbId);

    /**
     * 获取SVR时间
     * @param dbId
     * @return
     */
    BaseResult<SvrTimeVo> svrTime(Integer dbId);

    /**
     * 搜索编解码设备
     * @param dbId
     * @return
     */
    BaseResult<String> searchDev(Integer dbId);

    /**
     * 获取编解码通道列表
     * @param dbId
     * @return
     */
    BaseResult<EnChnListVo> enChnList(Integer dbId);

    /**
     * 添加/删除编码通道
     * @param enChnDto
     * @return
     */
    BaseResult<String> enChn(EnChnDto enChnDto);

    /**
     * 5.9获取编码器的预置位
     * @param dto
     * @return
     */
    BaseResult<CpResetVo>  enCpReset(EnCpResetDto dto);

    /**
     * 修改编码器预置位
     * @param dto
     * @return
     */
    BaseResult<String> CpReset(CpResetDto dto);

    /**
     * 获取解码通道列表
     * @param dbId
     * @return
     */
    BaseResult<DeChnListVo> deChnList(Integer dbId);

    /**
     * 添加/删除解码通道
     * @param dto
     * @return
     */
    BaseResult<String> deChn(DeChnDto dto);

    /**
     * 获取解码参数
     * @param dto
     * @return
     */
    BaseResult<DecParamVo> decParam(DecParamDto dto);

    /**
     * 设置解码参数
     * @param dto
     * @return
     */
    BaseResult<String> enDeParam(EnDecParamDto dto);

    /**
     * ptz控制
     * @param dto
     * @return
     */
    BaseResult<String> ptz(PtzDto dto);

    /**
     * 启用/停止远程点
     * @param dto
     * @return
     */

    BaseResult<String> remotePoint(RemotePointDto dto);

    /**
     * 获取远程点配置
     * @param dbId
     * @return
     */
    BaseResult<RemoteCfgVo> remoteCfg(Integer dbId);

}
