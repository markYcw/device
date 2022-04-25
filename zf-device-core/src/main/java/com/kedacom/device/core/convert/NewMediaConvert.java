package com.kedacom.device.core.convert;

import com.kedacom.newMedia.dto.NewMediaLoginDto;
import com.kedacom.newMedia.entity.NewMediaEntity;
import org.mapstruct.Mapper;


/**
 * @author ycw
 * @date: 2022/04/02 13:46
 * @description newMedia参数转换接口
 */
@Mapper(componentModel = "spring")
public interface NewMediaConvert {


    NewMediaLoginDto convertToNewMediaLoginDto(NewMediaEntity entity);




}
