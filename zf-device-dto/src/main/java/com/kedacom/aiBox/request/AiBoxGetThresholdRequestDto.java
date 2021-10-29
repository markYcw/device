package com.kedacom.aiBox.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/29
 */
@Data
public class AiBoxGetThresholdRequestDto implements Serializable {

    private String MsgId;

    private String NVRUuid;

    private String SessionId;

}
