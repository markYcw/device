package com.kedacom.device.core.config;

        import cn.hutool.core.util.StrUtil;
        import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
        import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
        import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.beans.factory.annotation.Value;
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

    @Value("${zf.${zf.sql.active}.dbType:}")
    String dbType;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /*hg的dialect必须手动注册*/
        if (!StrUtil.isEmpty(dbType)) {
            paginationInterceptor.setDialectType(dbType);
        }
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
