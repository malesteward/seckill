package com.boe.springbootmybatis.config;/*
 *ClassName:MyDataSource3307
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/17 20:00
 *@author:tang@qq.com
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
/* 这段配置会扫描org.mybatis.spring.sample.mapper下的所有接口，然后创建各自接口的动态代理类。*/
@MapperScan(basePackages = "com.boe.springbootmybatis.mapper.mapper3307",sqlSessionTemplateRef = "sqlSessionTemplate3307")
public class MyDataSource3307 {
    @Value("${spring.datasource.username3307}")
    private String username3307;
    @Value("${spring.datasource.password3307}")
    private String password3307;
    @Value("${spring.datasource.driver3307}")
    private String driver3307;
    @Value("${spring.datasource.url3307}")
    private String url3307;

    @Bean
    public DruidDataSource dataSource3307() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3307);
        druidDataSource.setUsername(username3307);
        druidDataSource.setPassword(password3307);
        druidDataSource.setDriverClassName(driver3307);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory3307() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource3307());
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate3307() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory3307().getObject());
        return sqlSessionTemplate;
    }

    @Bean
    public DataSourceTransactionManager transactionManager3307() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource3307());
        return dataSourceTransactionManager;
    }
}