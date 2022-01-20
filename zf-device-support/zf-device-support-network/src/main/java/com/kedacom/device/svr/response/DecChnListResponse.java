package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.pojo.DeChnList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 15:09
 * @description 解码器通道列表
 */
@Data
public class DecChnListResponse extends SvrResponse {

    @ApiModelProperty("解码器通道列表")
    private List<DeChnList>  chnList;

}
