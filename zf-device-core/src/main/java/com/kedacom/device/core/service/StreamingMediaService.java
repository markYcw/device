package com.kedacom.device.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kedacom.BasePage;
import com.kedacom.device.core.entity.StreamingMediaEntity;
import com.kedacom.streamingMedia.request.StreamingMediaQueryDto;
import com.kedacom.streamingMedia.request.StreamingMediaVo;

import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
public interface StreamingMediaService extends IService<StreamingMediaEntity> {

    /**
     * 分页查询流媒体服务器信息
     * @param streamingMediaQuery
     * @return
     */
    BasePage<StreamingMediaVo> querySmList(StreamingMediaQueryDto streamingMediaQuery);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    StreamingMediaVo querySm(String id);

    /**
     * 查询所有流媒体
     * @return
     */
    List<StreamingMediaVo> smList();

    /**
     * 对会议平台进行去重校验
     * @param streamingMediaVo
     * @return
     */
    String isRepeat(StreamingMediaVo streamingMediaVo);

    /**
     * 保存流媒体服务器
     * @param streamingMediaVo
     */
    boolean saveSm(StreamingMediaVo streamingMediaVo);

    /**
     * 修改流媒体服务器信息
     * @param streamingMediaVo
     * @return
     */
    boolean updateSm(StreamingMediaVo streamingMediaVo);

}
