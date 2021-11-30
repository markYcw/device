package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.MtTypeEntity;
import com.kedacom.mt.response.QueryMtTypeListResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/29
 */
@Mapper(componentModel = "spring")
public interface MtConvert {

    MtConvert INSTANCE = Mappers.getMapper(MtConvert.class);

    QueryMtTypeListResponseVo convertQueryMtTypeListResponseVo(MtTypeEntity entity);

    List<QueryMtTypeListResponseVo> convertQueryMtTypeListResponseVoList(List<MtTypeEntity> entityList);

}
