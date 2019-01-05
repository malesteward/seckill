package com.boe.springbootmybatis.config;/*
 *ClassName:MyDataSource3310
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
@MapperScan(basePackages = "com.boe.springbootmybatis.mapper.mapper3310",sqlSessionTemplateRef = "sqlSessionTemplate3310")
public class MyDataSource3310 {
    @Value("${spring.datasource.username3310}")
    private String username3310;
    @Value("${spring.datasource.password3310}")
    private String password3310;
    @Value("${spring.datasource.driver3310}")
    private String driver3310;
    @Value("${spring.datasource.url3310}")
    private String url3310;

    @Bean
    public DruidDataSource dataSource3310(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3310);
        druidDataSource.setUsername(username3310);
        druidDataSource.setPassword(password3310);
        druidDataSource.setDriverClassName(driver3310);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory3310 (){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource3310());
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate3310() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory3310 ().getObject());
        return  sqlSessionTemplate;
    }


    @Bean
    public DataSourceTransactionManager transactionManager3310(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource3310());
        return dataSourceTransactionManager;
    }


}
