package com.kedacom.aiBox.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/28
 */
@Data
public class AiBoxContrastRequestDto implements Serializable {

    private String RealTimeImg;

    private List<AiBoxContrastDto> FeatureLibImgList;

    private String SessionId;

    private String NVRUuid;

    private String MsgId;

}
