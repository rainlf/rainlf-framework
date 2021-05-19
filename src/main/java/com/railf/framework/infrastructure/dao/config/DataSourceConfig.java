package com.railf.framework.infrastructure.dao.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author : rain
 * @date : 2021/5/6 12:58
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url:}")
    private String url;
    @Value("${spring.datasource.username:}")
    private String username;
    @Value("${spring.datasource.password:}")
    private String password;
    @Value("${spring.datasource.driver-class-name:com.mysql.cj.jdbc.Driver}")
    private String driver;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriver(driver);
        return dataSource;
    }
}
