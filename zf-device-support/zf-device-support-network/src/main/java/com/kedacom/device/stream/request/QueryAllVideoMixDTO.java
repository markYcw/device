package com.kedacom.device.stream.request;

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
public class QueryAllVideoMixDTO extends StreamMediaDTO{

    private static final String COMMAND = "queryallvideomix";

    @Override
    public String getCommand() {
        return COMMAND;
    }

}
