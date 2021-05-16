package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:47
 */
@ToString(callSuper = true)
@Data
public class QueryAllVideoMixDTO extends BaseRequest {

    private static final String COMMAND = "queryallvideomix";

    @Override
    public String name() {
        return COMMAND;
    }

}
