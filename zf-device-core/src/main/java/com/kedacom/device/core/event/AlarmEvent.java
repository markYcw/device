package com.kedacom.device.core.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kedacom.core.anno.KmNotify;
import com.kedacom.core.pojo.Notify;
import com.kedacom.core.pojo.NotifyHead;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 15:53
 * @description
 */
@Data
@ToString(callSuper = true)
@KmNotify(name = "alarmnty")
public class AlarmEvent implements Notify {

    private NotifyHead nty;

    @ApiModelProperty(value = "1:告警 0:消警")
    private Integer AlarmState;

    @ApiModelProperty(value = "SVR告警上报类型 0：无效告警类型 1：刻录出错 2：磁盘错(暂不检测) 3：无硬盘 4：IP地址冲突 5：网络断开 6：网速下降 7：前端掉线 8：CPU过载" +
            "9：内存不足 10：前端视频源丢失 11：并口输入(合成通道并口输入3作为虚拟并口告警，实为开启刻录) 12：主动IPC发送能力不足 13：MP4分区空间将满（默认剩余空间少于50G，可配置）" +
            "14：MP4分区剩余空间不足（默认剩余空间少于10G，可配置） 15：多媒体主机断链 (新增加：适用2910、2720) 16：光盘剩余空间不足（具体见刻录参数配置） 17：电池电量低（默认电量少于10%，不可配）" +
            "18：红外学习成功告警 19：无音频接入 20：系统时间错误告警 21：移动侦测 22：审讯控制信号告警 23：合成通道无码流(未编码) 24：失焦 25：亮度变化 26：场景变换 27：人员聚集 28：前端并口输入" +
            "29：人脸马赛克检测 30：刻录异常停止 31：音量过高 32：音量过低 33：音量过低，区别于SVR_ALARM_TYPE_NO_AUDIO 34：磁盘丢失 35：raid失效 36：视频算法告警 37：电源掉电告警 38：录制磁盘满告警" +
            "39：录制磁盘出错告警 40：录像异常告警")
    private Integer AlarmType;

    @ApiModelProperty(value = "告警通道，为0时表示SVR本身")
    private Integer AlarmChnId;

    @ApiModelProperty(value = "告警子通道")
    private Integer AlarmChnSubId;

    @ApiModelProperty(value = "告警别名")
    private String AlarmDesc;

    private JSONObject removeHead() {

        String json = JSON.toJSONString(this);
        JSONObject jsonObject = JSON.parseObject(json);

        if (!jsonObject.containsKey("nty")) {
            throw new IllegalArgumentException("this object is not resp type");
        }
        jsonObject.remove("nty");
        return jsonObject;

    }

    @Override
    public Integer acquireSsno() {
        return nty.getSsno();
    }

    @Override
    public String acquireCommand() {
        return nty.getName();
    }

    @Override
    public <T> T acquireData(Class<T> clazz) throws JSONException {
        JSONObject jsonObject = removeHead();
        return jsonObject.toJavaObject(clazz);
    }
}
