package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.model.Result;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.pojo.DeviceSubscribe;
import com.kedacom.cu.vo.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * cu
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-10-29 16:48:30
 */
public interface CuService extends IService<CuEntity> {

    BaseResult<BasePage<DevEntityVo>> pageQuery(DevEntityQuery queryDTO);

    void cuNotify(String notify);

    BaseResult<DevEntityVo> loginById(CuRequestDto dto);


    BaseResult<String> logoutById(CuRequestDto dto);


    BaseResult<LocalDomainVo> localDomain(CuRequestDto dto);


    BaseResult<DomainsVo> domains(CuRequestDto dto);


    BaseResult<Long> time(Integer kmId);

    BaseResult<String> hb(Integer dbId);

    BaseResult<ViewTreesVo> viewTrees(CuRequestDto dto);

    BaseResult<String> selectTree(SelectTreeDto dto);

    BaseResult<String> devGroups(DevGroupsDto dto);

    BaseResult<String> devices(DevicesDto dto);

    BaseResult<DevEntityVo> info(Integer kmId);

    BaseResult<DevEntityVo> saveDev(DevEntityVo devEntityVo);

    BaseResult<DevEntityVo> updateDev(DevEntityVo devEntityVo);

    BaseResult<String> deleteDev(List<Integer> ids);

    /**
     * PTZ控制
     * @param requestDto
     * @return
     */
    BaseResult<String> controlPtz(ControlPtzRequestDto requestDto);

    /**
     * 开启平台录像
     * @param platRecStartVo
     * @return
     */
    BaseResult<Boolean> startRec(PlatRecStartVo platRecStartVo);

    /**
     * 关闭平台录像
     * @param platRecStopVo
     * @return
     */
    BaseResult<Boolean> stopRec(PlatRecStopVo platRecStopVo);

    /**
     * 开启前端录像
     * @param puRecStartVo
     * @return
     */
    BaseResult<Boolean> startPuRec(PuRecStartVo puRecStartVo);

    /**
     * 关闭前端录像
     * @param puRecStopVo
     * @return
     */
    BaseResult<Boolean> stopPuRec(PuRecStopVo puRecStopVo);

    /**
     * 打开录像锁定
     * @param requestDto
     * @return
     */
    BaseResult<Boolean> openLockingRec(OpenLockingRecRequestDto requestDto);

    /**
     * 取消录像锁定
     * @param requestDto
     * @return
     */
    BaseResult<Boolean> cancelLockingRec(CancelLockingRecRequestDto requestDto);

    /**
     * 查询录像
     * @param requestDto
     * @return
     */
    BaseResult<QueryVideoResponseVo> queryVideo(QueryVideoRequestDto requestDto);

    /**
     * 查询录像日历（即当天是否有录像）
     * @param requestDto
     * @return
     */
    BaseResult<QueryVideoCalendarResponseVo> queryVideoCalendar(QueryVideoCalendarRequestDto requestDto);

    /**
     * 查询磁阵(磁盘)信息
     * @param requestDto
     * @return
     */
    BaseResult<QueryDiskResponseVo> queryDisk(QueryDiskRequestDto requestDto);

    /**
     * 根据条件返回监控平台树
     * @param vo
     * @return
     */
    BaseResult<DevEntityVo> findByCondition(FindCuByConditionVo vo);

    /**
     * 根据数据库ID返回监控平台树
     * @param kmId
     * @return
     */
    BaseResult<DevEntityVo> queryMonitor(Integer kmId);

    BaseResult<CuDeviceVo> getCuDeviceInfo(Integer kmId, String puId);

    BaseResult<CuChannelVo> getCuChannelInfo(Integer kmId, String puId, Integer sn);

    BaseResult<DevEntityVo> cuGroup(CuRequestDto requestDto);

    BaseResult<List<CuDeviceVo>> cuDevice(CuDevicesDto requestDto);

    BaseResult<List<CuGroupVo>> cuGroupById(CuGroupDto requestDto);

    BaseResult<List<CuChannelVo>> getCuChannelList(CuChnListDto requestDto);

    BaseResult<GbIdVo> gbId(GbIdDto requestDto);

    BaseResult<PuIdTwoVo> puIdTwo(PuIdTwoDto requestDto);

    void reTryLoginNow(Integer dbId);

    void initCu();

    void logoutCu();

    void subscribe(DeviceSubscribe deviceSubscribe);

    CuEntity getBySsid(Integer ssid);

    BaseResult<PuIdOneVo> puIdOne(PuIdOneDto requestDto);

    BaseResult<PuIdByOneVo> puIdByOne(PuIdByOneDto requestDto);
}
