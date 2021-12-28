package com.kedacom.device.core.service;

import com.kedacom.mt.request.QueryMtTypeListRequestDto;
import com.kedacom.mt.response.QueryMtTypeListResponseVo;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/29
 */
public interface MtTypeService {

    /**
     * 查询终端设备类型信息集合
     * @param requestDto
     * @return
     */
    List<QueryMtTypeListResponseVo> queryList(QueryMtTypeListRequestDto requestDto);

}
