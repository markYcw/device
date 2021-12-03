package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:08
 * @description 追加刻录任务
 */
@Data
public class AppendBurnDto extends SvrRequestDto{

    @NotBlank(message = "刻录附件的文件夹名不能为空")
    @ApiModelProperty(value = "刻录附件的文件夹名，或者文件名比如2.0：ajrecord 比如3.0：ajrecord.tar",required = true)
    private String fileName;

    @ApiModelProperty(value = "解压出的文件夹名称,3.0必填")
    private String dirName;

}
