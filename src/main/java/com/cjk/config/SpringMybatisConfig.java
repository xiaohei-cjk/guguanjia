package com.cjk.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25
 * @description
 */
@Configuration
@MapperScan(basePackages = "com.cjk.mapper")//开启扫描mapper接口
@Import({SpringTxConfig.class,SpringCacheConfig.class})
@PropertySource(value = "classpath:system.properties",encoding = "utf-8")
public class SpringMybatisConfig {

    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        InputStream is = SpringMybatisConfig.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        druidDataSource.configFromPropety(properties);//自动配置参数

        //设置性能监控配置   组合配置  性能监控、sql防火墙、日志信息
        try {
            druidDataSource.setFilters("stat,wall,log4j2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }



    /*
     * 替代原mybatis的总配置文件，用于读取数据源获取一个连接回话对象的工厂bean
     * */
    @Bean
    public SqlSessionFactoryBean getFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);//设置数据源


//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();

        configuration.setMapUnderscoreToCamelCase(true);//设置支持驼峰命名转换
        configuration.setLogImpl(Log4j2Impl.class);//使用log4j2日志输出
        factoryBean.setConfiguration(configuration);

        PageInterceptor pageInterceptor = new PageInterceptor();//分页拦截对象
        //开启分页对象默认设置，解决自动适配方言问题
        pageInterceptor.setProperties(new Properties());
        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});


        return factoryBean;
    }


    /**
     * 设置spring监控:
     * 1.设置DruidStatInterceptor
     * 2.设置BeanNameAutoProxyCreator
     */
    @Bean("druidStatInterceptor")
    public DruidStatInterceptor getDruidStatInteceptor(){
        return new DruidStatInterceptor();
    }

    //设置代理
    @Bean
    public BeanNameAutoProxyCreator getAutoProxyCreator(){
        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
        proxyCreator.setInterceptorNames("druidStatInterceptor");
        proxyCreator.setProxyTargetClass(true);
        proxyCreator.setBeanNames(new String[]{"*Mapper","*ServiceImpl"});//设置需要监控的类
        return proxyCreator;
    }
}
