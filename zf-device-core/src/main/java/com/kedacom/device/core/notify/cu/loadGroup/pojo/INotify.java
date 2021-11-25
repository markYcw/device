package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import com.alibaba.fastjson.JSONObject;
import com.kedacom.device.core.notify.stragegy.DeviceType;

/**
 * @author ycw
 *
 */
public interface INotify {

	/**
	 * 获取“通知”所属的设备类型。
	 * @return
	 */
	public DeviceType getDeviceType();

	/**
	 * 获取会话标识
	 * @return
	 */
	public int getSsid();

	/**
	 * 解析数据。
	 * @param jsonData 符合JSON规范的字符串。
	 */
	public void parseData(JSONObject jsonData) throws Exception;
}
