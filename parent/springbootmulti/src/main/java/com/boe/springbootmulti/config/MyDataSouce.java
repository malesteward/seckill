package com.boe.springbootmulti.config;/*
 *ClassName:MyDataSouce
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/17 21:17
 *@author:tang@qq.com
 */

import com.alibaba.druid.pool.DruidDataSource;
import com.boe.springbootmulti.dynamic.DynamicDataSource;
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
@MapperScan(basePackages = "com.boe.springbootmulti.mapper",sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyDataSouce {
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
    /*======================================================================================================*/
    @Value("${spring.datasource.username3308}")
    private String username3308;
    @Value("${spring.datasource.password3308}")
    private String password3308;
    @Value("${spring.datasource.driver3308}")
    private String driver3308;
    @Value("${spring.datasource.url3308}")
    private String url3308;
    @Bean
    public DruidDataSource dataSource3308() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3308);
        druidDataSource.setUsername(username3308);
        druidDataSource.setPassword(password3308);
        druidDataSource.setDriverClassName(driver3308);
        return druidDataSource;
    }
    /*======================================================================================================*/
    @Value("${spring.datasource.username3309}")
    private String username3309;
    @Value("${spring.datasource.password3309}")
    private String password3309;
    @Value("${spring.datasource.driver3309}")
    private String driver3309;
    @Value("${spring.datasource.url3309}")
    private String url3309;
    @Bean
    public DruidDataSource dataSource3309() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3309);
        druidDataSource.setUsername(username3309);
        druidDataSource.setPassword(password3309);
        druidDataSource.setDriverClassName(driver3309);
        return druidDataSource;
    }
    /*======================================================================================================*/
    @Value("${spring.datasource.username3310}")
    private String username3310;
    @Value("${spring.datasource.password3310}")
    private String password3310;
    @Value("${spring.datasource.driver3310}")
    private String driver3310;
    @Value("${spring.datasource.url3310}")
    private String url3310;
    @Bean
    public DruidDataSource dataSource3310() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3310);
        druidDataSource.setUsername(username3310);
        druidDataSource.setPassword(password3310);
        druidDataSource.setDriverClassName(driver3310);
        return druidDataSource;
    }


    /*======================================================================================================*/
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object> mapj = new HashMap<>();
        dynamicDataSource.setTargetDataSources(mapj);
        mapj.put(DynamicDataSource.DB3307,dataSource3307());
        mapj.put(DynamicDataSource.DB3308,dataSource3308());
        mapj.put(DynamicDataSource.DB3309,dataSource3309());
        mapj.put(DynamicDataSource.DB3310,dataSource3310());
        return dynamicDataSource;
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory().getObject());
        return sqlSessionTemplate;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource());
        return dataSourceTransactionManager;
    }

}
