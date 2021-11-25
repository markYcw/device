package com.kedacom.device.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BasePage;
import com.kedacom.device.core.convert.StreamingMediaConvert;
import com.kedacom.device.core.entity.StreamingMediaEntity;
import com.kedacom.device.core.mapper.StreamingMediaMapper;
import com.kedacom.device.core.service.StreamingMediaService;
import com.kedacom.streamingMedia.request.StreamingMediaQueryDto;
import com.kedacom.streamingMedia.request.StreamingMediaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
@Slf4j
@Service
public class StreamingMediaServiceImpl extends ServiceImpl<StreamingMediaMapper, StreamingMediaEntity> implements StreamingMediaService {

    @Resource
    StreamingMediaMapper streamingMediaMapper;

    @Override
    public BasePage<StreamingMediaVo> querySmList(StreamingMediaQueryDto streamingMediaQuery) {

        Page<StreamingMediaEntity> page = new Page<>();
        page.setSize(streamingMediaQuery.getLimit());
        page.setCurrent(streamingMediaQuery.getPage());

        Page<StreamingMediaEntity> entityPage = streamingMediaMapper.selectPage(page, null);
        List<StreamingMediaEntity> records = entityPage.getRecords();
        List<StreamingMediaVo> streamingMediaVoList = StreamingMediaConvert.INSTANCE.convertToStreamingMediaVoList(records);
        BasePage<StreamingMediaVo> basePage = new BasePage<>();
        basePage.setTotal(entityPage.getTotal());
        basePage.setData(streamingMediaVoList);
        basePage.setTotalPage(entityPage.getPages());
        basePage.setCurPage(streamingMediaQuery.getPage());
        basePage.setPageSize(streamingMediaQuery.getLimit());

        return basePage;
    }

    @Override
    public StreamingMediaVo querySm(String id) {

        StreamingMediaEntity streamingMediaEntity = streamingMediaMapper.selectById(id);

        return StreamingMediaConvert.INSTANCE.convertToStreamingMediaVo(streamingMediaEntity);
    }

    @Override
    public List<StreamingMediaVo> smList() {

        List<StreamingMediaEntity> streamingMediaList = streamingMediaMapper.selectList(null);

        return StreamingMediaConvert.INSTANCE.convertToStreamingMediaVoList(streamingMediaList);
    }

    @Override
    public String isRepeat(StreamingMediaVo streamingMediaVo) {

        Long id = streamingMediaVo.getId();
        String ip = streamingMediaVo.getIp();
        String name = streamingMediaVo.getName();
        LambdaQueryWrapper<StreamingMediaEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (checkIp(queryWrapper, id, ip)) {
            return "ip重复，请重新填写";
        }

        return checkName(queryWrapper, id, name) ? "名称重复，请重新填写" : "";
    }

    @Override
    public boolean saveSm(StreamingMediaVo streamingMediaVo) {

        StreamingMediaEntity streamingMediaEntity = StreamingMediaConvert.INSTANCE.convertToStreamingMediaEntity(streamingMediaVo);
        int insert = streamingMediaMapper.insert(streamingMediaEntity);

        return insert > 0;
    }

    @Override
    public boolean updateSm(StreamingMediaVo streamingMediaVo) {

        StreamingMediaEntity streamingMediaEntity = StreamingMediaConvert.INSTANCE.convertToStreamingMediaEntity(streamingMediaVo);
        int updateById = streamingMediaMapper.updateById(streamingMediaEntity);

        return updateById > 0;
    }

    public boolean checkIp(LambdaQueryWrapper<StreamingMediaEntity> checkIp, Long id, String ip) {

        checkIp.eq(StreamingMediaEntity::getSmIp, ip);
        if (id != null) {
            checkIp.ne(StreamingMediaEntity::getId, id);
        }
        StreamingMediaEntity entity = streamingMediaMapper.selectOne(checkIp);

        return entity != null;
    }

    public boolean checkName(LambdaQueryWrapper<StreamingMediaEntity> checkName, Long id, String name) {

        checkName.eq(StreamingMediaEntity::getSmName, name);
        if (id != null) {
            checkName.ne(StreamingMediaEntity::getId, id);
        }
        StreamingMediaEntity entity = streamingMediaMapper.selectOne(checkName);

        return entity != null;
    }

}
