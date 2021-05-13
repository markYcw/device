package com.kedacom.acl.network.unite;

import com.kedacom.acl.network.anno.HeadParam;
import com.kedacom.acl.network.anno.RequestBody;
import com.kedacom.acl.network.ums.requestvo.LoginPlatformRequestVo;
import com.kedacom.acl.network.ums.responsevo.DeviceInfoVo;
import com.kedacom.acl.network.ums.responsevo.UmsAlarmTypeQueryResponseVo;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/12
 */
public interface UmsManagerInterface {

    /**
     * 登录统一平台
     * @param requestDto
     * @return ssid
     */
    Integer login(@RequestBody LoginPlatformRequestVo requestDto);

    /**
     * 注销统一平台
     * @param ssid
     * @return errorcode 状态码
     */
    Integer logout(@HeadParam String ssid);

    /**
     * 通知第三方服务同步设备列表，第三方同步完成会发送kafka消息
     * @param ssid
     * @return
     */
    Boolean notifyThirdServiceSyncData(@HeadParam String ssid);

    /**
     * 手动同步设备数据
     * @param ssid
     * @return 统一平台信息
     */
    DeviceInfoVo syncDeviceData(@HeadParam String ssid);

    /**
     * 手动触发从远程更新告警类型列表
     * @return
     */
    List<UmsAlarmTypeQueryResponseVo> updateUmsAlarmTypeList();

}
