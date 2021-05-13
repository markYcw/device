package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
@ApiModel(value = "查询录像列表请求参数类")
public class UmsQueryRecListResponseDto implements Serializable {

    @ApiModelProperty(value = "录像文件总数")
    private Integer totalSize;

    @ApiModelProperty(value = "录像列表")
    private List<UmsQueryRecInfoResponseDto> recList;

}
