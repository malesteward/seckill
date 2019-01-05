package com.boe.springbootmybatis.config;/*
 *ClassName:MyDataSource3309
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

@Configuration
/* 这段配置会扫描org.mybatis.spring.sample.mapper下的所有接口，然后创建各自接口的动态代理类。*/
@MapperScan(basePackages = "com.boe.springbootmybatis.mapper.mapper3309",sqlSessionTemplateRef = "sqlSessionTemplate3309")
public class MyDataSource3309 {
    @Value("${spring.datasource.username3309}")
    private String username3309;
    @Value("${spring.datasource.password3309}")
    private String password3309;
    @Value("${spring.datasource.driver3309}")
    private String driver3309;
    @Value("${spring.datasource.url3309}")
    private String url3309;

    @Bean
    public DruidDataSource dataSource3309(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3309);
        druidDataSource.setUsername(username3309);
        druidDataSource.setPassword(password3309);
        druidDataSource.setDriverClassName(driver3309);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory3309 (){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource3309());
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate3309() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory3309 ().getObject());
        return  sqlSessionTemplate;
    }


    @Bean
    public DataSourceTransactionManager transactionManager3309(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource3309());
        return dataSourceTransactionManager;
    }


}
