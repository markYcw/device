package com.kedacom.svr.vo;

import com.kedacom.svr.pojo.DeChnList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/9 14:15
 * @description 获取解码通道列表
 */
@Data
public class DeChnListVo implements Serializable {

    @ApiModelProperty("解码器通道列表")
   private List<DeChnList> chnList;

}
