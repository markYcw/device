package com.kedacom.device.core.service;

import com.kedacom.BaseResult;
import com.kedacom.power.dto.UpdatePowerLanConfigDTO;
import com.kedacom.power.entity.LanDevice;
import com.kedacom.power.entity.PowerConfigEntity;
import com.kedacom.power.entity.PowerDeviceEntity;
import com.kedacom.power.model.PageRespVo;
import com.kedacom.power.vo.*;

import java.util.List;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public interface ControlPowerService {

    /**
     * 添加北望电源设备
     */
    BaseResult<PowerDeviceEntity> addBwPower(BwPowerDeviceAddVo powerDeviceAddVo);

    /**
     * 修改北望电源设备
     */
    BaseResult<PowerDeviceEntity> updateBwPower(BwPowerDeviceUpdateVo powerDeviceUpdateVo);

    /**
     * @Description 配置监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:59
     */
    BaseResult<Integer> portAdd(PowerConfigAddVo powerConfigAddVo);

    /**
     * @Description 修改监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:59
     */
    BaseResult<Integer> portUpdate(PowerConfigUpdateVo powerConfigUpdateVo);

    /**
     * @Description 批量删除监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    BaseResult<Boolean> portDelete(PowerPortVo powerPortVo);

    /**
     * @Description 查询所有监听端口
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    BaseResult<List<PowerPortListVo>> portList(PowerConfigListVo powerConfigListVo);

    /**
     * @Description 根据端口号查询配置
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    PowerConfigEntity getPowerByPort(int port);


    /**
     * @Description 分页条件查询电源设备
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 13:06
     */
    BaseResult<PageRespVo<List<PowerDeviceListRspVo>>> deviceList(PowerDeviceListVo powerDeviceListVo);

    /**
     * @Description 批量删除电源设备
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 11:26
     */
    BaseResult<Boolean> deviceDelete(PowerDeviceDeleteVo powerDeviceDeleteVo);

    /**
     * @Description 启动TCP连接
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    BaseResult powerStart(int id);

    /**
     * @Description 获取所有设备，填充下拉列表
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    BaseResult<List<PowerDeviceVo>> getDeviceDatas();

    /**
     * @Description 获取所有设备
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 13:50
     */
    BaseResult<List<PowerDeviceListRspVo>> listDevices();

    /**
     * 根据Id获取设备
     *
     * @param
     * @return
     * @author zlf
     * @date 15:34 2021/7/29
     */
    BaseResult<PowerDeviceListRspVo> getDeviceById(int id);

    List<LanDevice> searchDevices() throws Exception;

    BaseResult<PowerLanConfigVO> getPowerConfigByMac(String macAddr);

    BaseResult<Boolean> updatePowerConfigByMac(UpdatePowerLanConfigDTO dto);

    /**
     * @Description 获取设备详细信息
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    BaseResult<PowerDeviceMessageVo> deviceMessage(PowerDeviceMessageReqVo powerDeviceMessageReqVo);

    /**
     * @Description 获取设备下通道状态
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:58
     */
    BaseResult<List<PowerChannelStateVo>> deviceChannelState(PowerDeviceMessageReqVo powerDeviceMessageReqVo);

    BaseResult<Boolean> deviceTurn(PowerDeviceTurnVO vo);

    /**
     * @Description 多个通道开关
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:57
     */
    BaseResult<Boolean> deviceTurns(PowerDeviceTurnsVo powerDeviceTurnsVo);

    /**
     * @Description 关闭TCP连接
     * @param:
     * @return:
     * @author:张龙飞
     * @date:2021/5/25 13:57
     */
    BaseResult<Boolean> powerStop();

    /**
     * @Description 修改电源设备状态
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/27 14:11
     */
    boolean changePowerDeviceState(String macAddr, String ip, int state);

    /**
     * @Description 项目启动时，读取数据库配置，启动tcp连接
     * @param:
     * @return:
     * @author:zlf
     * @date:2021/5/28 9:23
     */
    void tcpOnServerStart();

    /**
     * @Description 所有BWANT_IPM_08设备状态置为离线
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    void changeBwantAllDeviceStatusDown();

    /**
     * @Description 获取电源支持的类型
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    BaseResult<List<PowerDeviceTypeResponseVo>> getDevType();


}
