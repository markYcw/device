package com.kedacom.power.common;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class RequestType {

    /**
     * 通信标识
     */
    public static final String COMMUNICATION_ID = "CH9121_CFG_FLAG";

    /**
     * 搜索命令头
     */
    public static final Integer SEARCH_TYPE = 4;

    /**
     * 搜索应答命令头
     */
    public static final String SEARCH_BACK_TYPE = "84";

    public static final Integer GET_CONFIG_TYPE = 2;

    public static final String GET_CONFIG_BACK_TYPE = "82";

    public static final Integer CONFIG_TYPE = 1;

    public static final String CONFIG_BACK_TYPE = "81";

    public static final Integer RESTORE_TYPE = 3;

    public static final String RESTORE_BACK_TYPE = "83";
}
