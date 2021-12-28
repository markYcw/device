package com.kedacom.device.core.convert;

import com.kedacom.device.core.entity.MtEntity;
import com.kedacom.device.core.entity.MtTypeEntity;
import com.kedacom.mt.LoginParamVo;
import com.kedacom.mt.MtStatus;
import com.kedacom.mt.TerminalVo;
import com.kedacom.mt.response.GetMtStatusResponseVo;
import com.kedacom.mt.response.QueryMtTypeListResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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

    TerminalVo convertTerminalVo(MtEntity mtEntity);

    List<TerminalVo> convertTerminalVoList(List<MtEntity> mtEntityList);

    MtEntity convertMtEntity(TerminalVo terminalVo);

    @Mappings({@Mapping(target = "type",source = "devtype"),
            @Mapping(target = "user", source = "username"), @Mapping(target = "passWord", source = "password")})
    LoginParamVo convertLoginParamVo(MtEntity mtEntity);

    GetMtStatusResponseVo convertGetMtStatusResponseVo(MtStatus mtStatus);

}
