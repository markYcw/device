package com.kedacom.device.svr.response;

import com.kedacom.device.svr.SvrResponse;
import com.kedacom.device.svr.pojo.EnChnList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 14:24
 * @description 编解码通道列表信息
 */
@Data
public class ChnListExtendsResponse extends SvrResponse {

    @ApiModelProperty("编码器通道列表")
    private List<EnChnList> chnList;
}
