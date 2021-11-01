package com.kedacom.device.core.convert;

import com.kedacom.cu.entity.CuEntity;
import com.kedacom.device.cu.request.CuLoginRequest;
import com.kedacom.device.svr.pojo.DeChnList;
import com.kedacom.device.svr.pojo.EnChnList;
import com.kedacom.device.svr.request.SvrLoginRequest;
import com.kedacom.device.svr.response.*;
import com.kedacom.svr.entity.SvrEntity;
import com.kedacom.svr.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author ycw
 * @date: 2021/09/06 13:46
 * @description cu参数转换接口
 */
@Mapper(componentModel = "spring")
public interface CuConvert {
    @Mappings({@Mapping(target = "user",source = "username")})
    CuLoginRequest convertToCuLoginRequest(CuEntity cuEntity);
}
