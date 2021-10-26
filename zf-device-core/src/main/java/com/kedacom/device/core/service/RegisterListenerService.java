package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.kedacom.BaseResult;
import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.device.core.entity.KmListenerEntity;
import com.kedacom.deviceListener.RegisterListenerVo;


import java.util.List;

/**
 * KM设备消息订阅者信息
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-19 14:33:24
 */
public interface RegisterListenerService extends IService<KmListenerEntity> {

    /**
     * 根据消息类型获取所有消息监听者表记录
     * @return
     */
    List<KmListenerEntity> getAll(Integer msgType);

    /**
     * 注册设备消息订阅者信息
     * @param registerUrlVo
     * @return
     */
    BaseResult<RegisterListenerVo> registerListener(RegisterListenerVo registerUrlVo);

    /**
     * 对设备订阅者做去重校验
     * @param registerUrlVo
     * @return
     */
    boolean isRepeat(RegisterListenerVo registerUrlVo);

    /**
     * 取消订阅设备消息
     * @param registerUrlVo
     */
    void UnRegisterListener(RegisterListenerVo registerUrlVo);
}
