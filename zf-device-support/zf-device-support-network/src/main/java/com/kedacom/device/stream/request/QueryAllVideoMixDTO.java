package com.kedacom.device.stream.request;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:47
 */
public class QueryAllVideoMixDTO extends StreamMediaDTO{

    private static final String COMMAND = "queryallvideomix";

    @Override
    String getCommand() {
        return COMMAND;
    }

}
