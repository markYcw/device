package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.BaseResult;
import com.kedacom.cu.dto.CuPageQueryDTO;
import com.kedacom.cu.dto.CuRequestDto;
import com.kedacom.cu.dto.DevGroupsDto;
import com.kedacom.cu.dto.SelectTreeDto;
import com.kedacom.cu.entity.CuEntity;
import com.kedacom.cu.vo.DomainsVo;
import com.kedacom.cu.vo.LocalDomainVo;
import com.kedacom.cu.vo.TimeVo;
import com.kedacom.cu.vo.ViewTreesVo;
import com.kedacom.svr.dto.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.pojo.SvrPageQueryDTO;
import com.kedacom.svr.vo.*;

import java.util.List;

/**
 * cu
 *
 * @author ycw
 * @email yucongwang@kedacom.com
 * @date 2021-10-29 16:48:30
 */
public interface CuService extends IService<CuEntity> {

    BaseResult<BasePage<CuEntity>> pageQuery(CuPageQueryDTO queryDTO);

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
}
