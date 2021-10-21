package com.kedacom.aiBox.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
@ApiModel(description = "图片对比AIBox返回参数类")
public class AiBoxContrastResponseDto implements Serializable {

    @ApiModelProperty("错误码:0:成功;非0:失败错误码")
    private Integer StatusCode;

    @ApiModelProperty("图片对比信息")
    private List<AiBoxContrastDto> SimilarityList;

    // 以下字段无用，暂时保留

    @ApiModelProperty("对应请求的URL")
    private String RequestURL;

    @ApiModelProperty("状态具体信息")
    private String StatusString;

    @ApiModelProperty("时间戳")
    private String LocalTime;

    private String NVRUuid;

    private String SessionId;

    private String MsgId;

}
