package com.kedacom.device.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/17
 */
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.kedacom.device.core.mapper"})
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor PaginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
