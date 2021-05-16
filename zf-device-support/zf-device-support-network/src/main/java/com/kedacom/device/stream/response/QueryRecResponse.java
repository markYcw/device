package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.RecordInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 09:22
 */
@Data
@ApiModel("查询录像记录应答")
public class QueryRecResponse extends BaseResponse {

    @ApiModelProperty("查询到的录像总数")
   private Integer sum_number;

    @ApiModelProperty("当前显示的录像信息数量(分页信息)")
   private Integer number;

    @ApiModelProperty("录像信息")
   private List<RecordInfo> record_info;

}
