package com.kedacom.acl.network.unite;

import com.kedacom.acl.network.anno.HeadParam;
import com.kedacom.acl.network.anno.RequestParam;
import com.kedacom.ums.responsedto.QueryMediaResponseDto;
import com.kedacom.ums.responsedto.UmsScheduleGroupQueryMediaResponseDto;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
public interface UmsOperateInterface {

    /**
     * 查询调度组媒体源
     * @param ssid
     * @param GroupID
     * @return
     */
    UmsScheduleGroupQueryMediaResponseDto queryScheduleGroupMedia(@HeadParam String ssid, @RequestParam String GroupID);

    /**
     * 设置调度组成员媒体源
     * @param ssid
     * @param GroupID
     * @param Members
     * @return
     */
    Integer setscheduling(@HeadParam(name = "ssid") String ssid, @RequestParam(name = "GroupID") String GroupID, @RequestParam(name = "Members") List<QueryMediaResponseDto> Members);



}
