package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.common.model.Result;
import com.kedacom.cu.dto.*;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.*;

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

    BaseResult<String> loginById(CuRequestDto dto);


    BaseResult<String> logoutById(CuRequestDto dto);


    BaseResult<LocalDomainVo> localDomain(CuRequestDto dto);


    BaseResult<DomainsVo> domains(CuRequestDto dto);


    BaseResult<TimeVo> time(CuRequestDto dto);

    BaseResult<String> hb(Integer dbId);

    BaseResult<ViewTreesVo> viewTrees(CuRequestDto dto);

    BaseResult<String> selectTree(SelectTreeDto dto);

    BaseResult<String> devGroups(DevGroupsDto dto);

    BaseResult<String> devices(DevicesDto dto);

    BaseResult<DevEntityVo> info(Integer kmId);

    BaseResult<DevEntityVo> saveDev(DevEntityVo devEntityVo);

    BaseResult<DevEntityVo> updateDev(DevEntityVo devEntityVo);

    BaseResult<String> deleteDev(List<Integer> ids);
}
