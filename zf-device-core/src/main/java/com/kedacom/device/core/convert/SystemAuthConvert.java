package com.kedacom.device.core.convert;

import com.kedacom.avIntegration.response.auth.SystemLoginResponse;
import com.kedacom.avIntegration.response.auth.SystemVersionResponse;
import com.kedacom.avIntegration.vo.auth.SystemLoginVO;
import com.kedacom.avIntegration.vo.auth.SystemVersionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:38
 */
@Mapper(componentModel = "spring")
public interface SystemAuthConvert {

    SystemAuthConvert INSTANCE = Mappers.getMapper(SystemAuthConvert.class);

    SystemLoginVO loginResponseToLoginVo(SystemLoginResponse response);

    SystemVersionVO versionResponseToLoginVo(SystemVersionResponse response);

}