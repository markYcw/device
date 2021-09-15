package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:08
 * @description 追加刻录任务
 */
@Data
public class AppendBurnDto extends SvrRequestDto{

    @ApiModelProperty("附件源文件夹,默认位置：/usr/mp4pt/tmp")
    private String dir;

}
