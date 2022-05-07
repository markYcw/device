package com.kedacom.device.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kedacom.BaseResult;
import com.kedacom.common.constants.DevTypeConstant;
import com.kedacom.data.entity.DataEntity;
import com.kedacom.device.core.mapper.DataMapper;
import com.kedacom.device.core.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;

/**
 * @author ycw
 * @date 2022/04/28
 * @deprecated 数据迁移
 */
@Slf4j
@Service("dataService")
public class DataServiceImpl extends ServiceImpl<DataMapper, DataEntity> implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private DataSource dataSource;

    private static final String sqlPath = "/opt/kedacom/web/config/databases/movedata.sql";


    @Override
    public BaseResult<Integer> data() {
        DataEntity entity = dataMapper.selectById(1);
        if (ObjectUtil.isNull(entity)) {
            return BaseResult.succeed(1);
        } else {
            if (entity.getDc() == 1) {
                return BaseResult.succeed(1);
            }
        }
        return BaseResult.succeed(0);
    }

    @Override
    public void dcOne() {
        File file = new File(sqlPath);
        if (file.exists()) {
            try {
                if (!check(1) || getResult(1) != 1) {
                    PathResource resource = new PathResource(sqlPath);
                    ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
                }
            } catch (Exception e) {
                log.error("========执行数据迁移时出错", e);
                if (!check(1)) {
                    DataEntity entity = new DataEntity();
                    entity.setDc(0);
                    dataMapper.insert(entity);
                }
            }
            if (!check(1)) {
                DataEntity entity = new DataEntity();
                entity.setDc(1);
                dataMapper.insert(entity);
            }else {
                updateResult(1);
            }
        }
    }

    public void updateResult(Integer id){
        DataEntity entity = dataMapper.selectById(id);
        entity.setDc(1);
        dataMapper.updateById(entity);
    }

    public Integer getResult(Integer id) {
        DataEntity entity = dataMapper.selectById(id);
        return entity.getDc();
    }

    public boolean check(Integer id) {
        DataEntity entity = dataMapper.selectById(id);
        if (ObjectUtil.isNull(entity)) {
            return false;
        } else {
            return true;
        }
    }
}
