package com.kedacom.newMedia.resopnse;

import com.kedacom.streamMedia.response.GetAudioCapVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/5/27 12:50
 * @description
 */
@Data
public class GetAudioResponse implements Serializable {

    private GetAudioCapVO audioCap;
}
