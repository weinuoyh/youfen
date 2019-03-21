package com.moerlong.youfen.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
@MapperScan("com.moerlong.youfen.dao")
public class DatabaseConfiguration {
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
    @Bean
    public DataSource getDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        return datasource;
    }
    @Value(value = "${spring.datasource.url}")
    private String dbUrl;

    @Value(value = "${spring.datasource.username}")
    private String username;

    @Value(value ="${spring.datasource.password}")
    private String password;

    @Value(value ="${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value(value ="${spring.datasource.initialSize}")
    private int initialSize;

    @Value(value ="${spring.datasource.minIdle}")
    private int minIdle;

    @Value(value ="${spring.datasource.maxActive}")
    private int maxActive;

    @Value(value ="${spring.datasource.maxWait}")
    private int maxWait;

    @Value(value ="${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value(value ="${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value(value ="${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value(value ="${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value(value ="${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value(value ="${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value(value ="${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

}