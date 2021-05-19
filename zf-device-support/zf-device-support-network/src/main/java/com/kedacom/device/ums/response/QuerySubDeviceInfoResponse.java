package com.kedacom.device.ums.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.ums.responsedto.SubDeviceInfoResponseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
@ApiModel
public class QuerySubDeviceInfoResponse extends BaseResponse implements Serializable {

    @ApiModelProperty(value = "查询结果数")
    private Integer querycount;

    @ApiModelProperty(value = "设备信息集合")
    private List<SubDeviceInfoResponseVo> devinfo;

}
