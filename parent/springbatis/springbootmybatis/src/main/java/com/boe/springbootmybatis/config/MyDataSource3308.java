package com.boe.springbootmybatis.config;/*
 *ClassName:MyDataSource3308
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
@MapperScan(basePackages = "com.boe.springbootmybatis.mapper.mapper3308",sqlSessionTemplateRef = "sqlSessionTemplate3308")
public class MyDataSource3308 {
    @Value("${spring.datasource.username3308}")
    private String username3308;
    @Value("${spring.datasource.password3308}")
    private String password3308;
    @Value("${spring.datasource.driver3308}")
    private String driver3308;
    @Value("${spring.datasource.url3308}")
    private String url3308;

    @Bean
    public DruidDataSource dataSource3308(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url3308);
        druidDataSource.setUsername(username3308);
        druidDataSource.setPassword(password3308);
        druidDataSource.setDriverClassName(driver3308);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory3308 (){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource3308());
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate3308() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory3308 ().getObject());
        return  sqlSessionTemplate;
    }


    @Bean
    public DataSourceTransactionManager transactionManager3308(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource3308());
        return dataSourceTransactionManager;
    }


}
