package com.pluralsight.conferencedemo.com.pluralsight.conferencedemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public DataSource getDataSourceConfig(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:postgresql://0.0.0.0:5432/conference");
        builder.username("postgres");
        builder.password("_Chiru123_");
        System.out.println("Building datasource from builder");
        return builder.build();
    }
}
