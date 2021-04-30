package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.data.RecordInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 09:22
 */
@Data
public class QueryrecResponseVO implements Serializable {

    @ApiModelProperty("查询到的录像总数")
   private Integer sumNumber;

    @ApiModelProperty("当前显示的录像信息数量(分页信息)")
   private Integer number;

    @ApiModelProperty("录像信息")
   private List<RecordInfo> recordInfoList;

}
