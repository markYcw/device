package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 分组
 * @author ycw
 */
@Data
public class PGroup implements Cloneable{

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
	private List<PGroup> sortChildGroups = new LinkedList<PGroup>();
	
	/**
	 * 改分组下面是否有设备，平台2.0有效，方便刷设备。
	 */
	@ApiModelProperty("改分组下面是否有设备 0否 1是")
	private Integer hasDev;

	@ApiModelProperty("总设备数")
	private Integer count;

	/**
	 * 设备在线数
	 */
	@ApiModelProperty("设备在线数")
	private Integer onLineCount;

	public void addChildGroup(PGroup group){
		synchronized(sortChildGroups){
			if(group == null)
				return;
			//排序加入缓存
			for(int index = sortChildGroups.size()-1; index >= 0; index -- ){
				PGroup temp = sortChildGroups.get(index);
				if(temp.getName().compareToIgnoreCase(group.getName())<0){
					sortChildGroups.add(index+1, group);
					return;
				}
			}
			sortChildGroups.add(0,group);
		}
	}
	
	/**
	 * 返回当前分组是否是监控平台的内置“未分组”。
	 */
	public boolean isRootGroup(PGroup pGroup){
		return rootGroupId(pGroup);
	}

	/**
	 * 判断当前分组是否跟分组
	 * @param pGroup
	 * @return
	 */
	public static boolean rootGroupId(PGroup pGroup){
		return pGroup.getParentId().equals("")?true:false;
	}
	
}
