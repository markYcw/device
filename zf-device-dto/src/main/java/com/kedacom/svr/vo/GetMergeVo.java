package com.kedacom.svr.vo;

import com.kedacom.svr.pojo.MergeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:10
 * @description
 */
@Data
public class GetMergeVo {

    @ApiModelProperty("合成参数")
    private List<MergeInfo> recList;
}
