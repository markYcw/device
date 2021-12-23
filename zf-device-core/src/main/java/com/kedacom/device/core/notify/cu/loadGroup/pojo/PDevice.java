package com.kedacom.device.core.notify.cu.loadGroup.pojo;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.Iterator;
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

	@ApiModelProperty("设备是否在线 0:离线，1:在线")
	private Integer online = 0;

	@ApiModelProperty("通道在线数当且仅当改设备底下存在通道时有此属性")
	private Integer onLineCount;

	/**
	 * 获取全部通道
	 * @return
	 */
	public List<SrcChn> getChannels() {
		List<SrcChn>  list = new ArrayList<SrcChn> (this.srcChns.size());
		list.addAll(this.srcChns);
		return list;
	}

	/**
	 * 更新通道在线状态
	 * @param updateSrcChs
	 */
	public void updateChn(List<SrcChns> updateSrcChs){
		for (SrcChns updateSrcCh : updateSrcChs) {
			for (SrcChn srcChn : srcChns) {
				if (srcChn.getSn().equals(updateSrcCh.getSn())){
					srcChn.setOnline(updateSrcCh.getOnline());
					srcChn.setEnable(updateSrcCh.getEnable());
				}
			}
		}
	}

	/**
	 * 更新通道录像状态
	 * @param recs
	 */
	public void updateChnRec(List<Rec> recs){
		for (Rec rec : recs) {
			for (SrcChn srcChn : srcChns) {
				if (srcChn.getSn().equals(rec.getSn())){
					if(rec.getPlat()!=null){
						srcChn.setPuRecord(rec.getPlat());
					}
					if(rec.getPu()!=null){
						srcChn.setPuRecord(rec.getPu());
					}
				}
			}
		}
	}

	
	
}
