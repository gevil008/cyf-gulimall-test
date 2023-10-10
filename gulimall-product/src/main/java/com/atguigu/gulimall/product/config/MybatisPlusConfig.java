package com.atguigu.gulimall.product.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.gulimall.product.dao")
public class MybatisPlusConfig {

    /**
     * 插件注册
     *
     * @param paginationInnerInterceptor 分页插件
     * @return MybatisPlus拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(PaginationInnerInterceptor paginationInnerInterceptor) {
        MybatisPlusInterceptor mp = new MybatisPlusInterceptor();
        mp.addInnerInterceptor(paginationInnerInterceptor);
        return mp;
    }

    //分页插件
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor pii = new PaginationInnerInterceptor();
        pii.setMaxLimit(20L);
        pii.setDbType(DbType.MYSQL);
        //当超过最大页数时不会报错
        pii.setOverflow(true);
        return pii;
    }
}