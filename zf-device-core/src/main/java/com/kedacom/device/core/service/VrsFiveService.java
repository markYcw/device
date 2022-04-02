package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.svr.dto.FindByIpOrNameDto;
import com.kedacom.svr.vo.RemoteChnListVo;
import com.kedacom.vs.entity.VsEntity;
import com.kedacom.vs.vo.*;

import java.util.List;

/**
 * VRS2100/4100录播服务器5.0/5.1
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-06-04 17:09:24
 */
public interface VrsFiveService extends IService<VsEntity> {
    /**
     * 分页查询vrs信息
     * @param vrsQuery
     * @return
     */
    BaseResult<BasePage<VsEntity>> vrsList(VrsQuery vrsQuery);

    /**
     * 保存VRS
     * @param vrsVo
     * @return
     */
    BaseResult<VrsVo> saveVrs(VrsVo vrsVo);

    /**
     * 对VRS进行名字去重校验
     * @param vrsVo
     * @return
     */
    boolean isRepeat(VrsVo vrsVo);

    /**
     * 删除VRS
     * @param ids
     */
    BaseResult delete(List<Integer> ids);

    /**
     * 修改vrs
     * @param vrsVo
     * @return
     */
    BaseResult<VrsVo> updateVrs(VrsVo vrsVo);

    /**
     * 查询VRS信息
     * @param id
     * @return
     */
    BaseResult<VrsVo> vtsInfo(Integer id);


    /**
     * 分页HTTP录像查询
     * @param queryRecListVo
     * @return
     */
    BaseResult<VrsRecInfoDecVo> vrsQueryHttpRec(QueryRecListVo queryRecListVo);

    BaseResult<VrsRecInfoVo> queryRec(QueryRecVo vo);

    BaseResult<LiveInfoVo> queryLive(QueryLiveVo vo);

    BaseResult<VrsRecInfoVo> queryRecByIp(QueryRecByIpVo vo);

    VsEntity getBySsid(Integer ssid);

    void logoutSsid(Integer ssid);

    BaseResult<VrsVo> findByIpOrName(FindByIpOrNameDto dto);

    BaseResult<String> asyList();
}
