package com.cjk.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.cjk.service")
@EnableTransactionManagement
public class SpringTxConfig {


    @Bean
    public DataSourceTransactionManager getTransactionManager(DruidDataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
