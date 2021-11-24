package com.kedacom.device.core.notify.cu.loadGroup.pojo;


import com.kedacom.device.core.notify.cu.loadGroup.ToolsUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 监控平台上的设备
 * @author ycw
 * @see PGroup
 * @see PDevice
 * @see PChannel
 *
 */
@Data
public class PDevice {

	@ApiModelProperty("设备所在的平台域的域编号")
	private String domain;

	@ApiModelProperty("所在的组ID")
	private String groupId;

	@ApiModelProperty("设备号")
	private String puId;

	@ApiModelProperty("设备名称")
	private String name;

	@ApiModelProperty("设备类型 1编码器 2解码器 4电视墙（2.0） 5NVR（2.0） 6SVR（2.0） 7报警主机（告警主机）1，5，6可以视频浏览")
	private int type;

	@ApiModelProperty("权限：（二进制位数） 1：云镜控制(1-10)级 第一位开始，共占用4位。9：前端录像。10：前端放像。11：前端录像删除。12：前端录像下载。17 ：前端录像设置。18：前端开关量输出控制。19：布防，撤防。")
	private int prilevel;

	@ApiModelProperty("设备厂商")
	private String manufact;

	@ApiModelProperty("视频源列表")
	private List<SrcChn> srcChns;

	@ApiModelProperty("视频源编号(2.0有效) 视频源名称(2.0有效)")
	private HashMap<Integer, SrcChn> channels = new HashMap<Integer, SrcChn>();

	@ApiModelProperty("设备是否在线 0:离线，1:在线")
	private Integer online;

	/**
	 * 获取全部通道
	 * @return
	 */
	public List<SrcChn> getChannels() {
		List<SrcChn>  list = new ArrayList<SrcChn> (this.channels.size());
		list.addAll(this.channels.values());
		return list;
	}

	/**
	 * 获取指定通道
	 * @param sn
	 * @return
	 */
	public SrcChn getChannel(Integer sn){
		return this.channels.get(sn);
	}
	/**
	 * 增加通道
	 * @param channel
	 */
	public void addChannel(SrcChn channel){
		channel.setPuId(this.puId);
		if(ToolsUtil.isEmpty(channel.getName()))
			channel.setName(this.getName());
		
		this.channels.put(channel.getSn(), channel);
	}
	public void addChannels(Collection<SrcChn> channels) {
		for(SrcChn chl : channels){
			this.addChannel(chl);
		}
	}
	
	/**
	 * 移除通道
	 * @param sn
	 */
	public void removeChannel(int sn){
		this.channels.remove(sn);
	}
	
	/**
	 * 清空通道
	 */
	public void clearChannel(){
		this.channels.clear();
	}
	
	
}
