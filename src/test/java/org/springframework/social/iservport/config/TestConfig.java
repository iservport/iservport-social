package org.springframework.social.iservport.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Test configuration.
 * 
 * @author mauriciofernandesdecastro
 */
@Configuration
@PropertySource("classpath:/org/springframework/social/iservport/config/test.properties")
public class TestConfig {

    @Bean  
    public DataSource dataSource() {  
            DriverManagerDataSource dataSource = new DriverManagerDataSource();  
              
            dataSource.setDriverClassName("org.hsqldb.jdbcDriver");  
            dataSource.setUrl("jdbc:hsqldb:file:target/testdb/db2");  
            dataSource.setUsername("sa");  
            dataSource.setPassword("");  
              
            return dataSource;  
    }  
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
    
}
