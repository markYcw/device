package com.kedacom.device.core.notify.nm.pojo;

import com.kedacom.newMedia.pojo.NMDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 分组
 * @author ycw
 */
@Data
public class NmGroup implements Cloneable{

	/**
	 * “未分组”的ID
	 */
	@ApiModelProperty("“未分组”的ID")
	public static String unNamgedGroupId = "IsUnnamedGroup";
	
	/**
	 * 组id
	 */
	@ApiModelProperty("组id")
	private String id;
	/**
	 * 分组名称
	 */
	@ApiModelProperty("分组名称")
	private String name;
	/**
	 * 上级组id
	 */
	@ApiModelProperty("上级组id")
	private String parentId;
	/**
	 * 子设备组
	 */
	@ApiModelProperty("子设备组")
	private List<NmGroup> sortChildGroups = new LinkedList<NmGroup>();

	@ApiModelProperty("分组类型 0-新媒体分组，1-自定义分组")
	private Integer type;

	@ApiModelProperty("分组目录")
	private String groupCatalog;

	@ApiModelProperty("分组码")
	private String groupCode;

	@ApiModelProperty("排序索引")
	private String sortIndex;

	@ApiModelProperty(value = "当前分组设备总数")
	private Integer deviceTotalNum = 0;

	@ApiModelProperty(value = "当前分组在线设备总数")
	private Integer deviceOnlineNum = 0;

	@ApiModelProperty(value = "当前分组离线设备总数")
	private Integer deviceOfflineNum = 0;

	@ApiModelProperty(value = "当前分组故障设备总数")
	private Integer deviceFaultNum = 0;


	public void addChildGroup(NmGroup group){
		synchronized(sortChildGroups){
			if(group == null)
				return;
			//排序加入缓存
			for(int index = sortChildGroups.size()-1; index >= 0; index -- ){
				NmGroup temp = sortChildGroups.get(index);
				if(temp.getName().compareToIgnoreCase(group.getName())<0){
					sortChildGroups.add(index+1, group);
					return;
				}
			}
			sortChildGroups.add(0,group);
		}
	}

	/**
	 * 更新分组信息如设备总数在线总数离线总数等
	 * @param device
	 */
	public void updateMessage(NMDevice device){
		this.deviceTotalNum = deviceTotalNum + 1;
		Integer status = device.getStatus();
		if(status==0){
			this.deviceOnlineNum = deviceOnlineNum + 1;
		}else {
			this.deviceOfflineNum = deviceOfflineNum +1;
		}

	}
	
	/**
	 * 返回当前分组是否是监控平台的内置“未分组”。
	 */
	public boolean isRootGroup(NmGroup pGroup){
		return rootGroupId(pGroup);
	}

	/**
	 * 判断当前分组是否跟分组
	 * @param pGroup
	 * @return
	 */
	public static boolean rootGroupId(NmGroup pGroup){
		return pGroup.getParentId().equals("")?true:false;
	}
	
}
