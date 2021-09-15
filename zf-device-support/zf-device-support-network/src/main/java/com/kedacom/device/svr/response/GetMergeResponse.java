package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.pojo.MergeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:30
 * @description 获取画面合成
 */
@Data
public class GetMergeResponse extends SvrResponse {

    @ApiModelProperty("合成参数")
    private MergeInfo mergeInfo;

}
