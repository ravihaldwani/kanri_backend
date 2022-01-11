package com.kanrisoft.kanri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

//@Configuration
public class DbConfig extends AbstractJdbcConfiguration {

//    @Bean
//    DataSource dataSource() {
//        var databaseBuilder = new EmbeddedDatabaseBuilder();
//        return databaseBuilder.setType(EmbeddedDatabaseType.H2).build();
//    }
//
//    @Bean
//    NamedParameterJdbcOperations jdbcOperations(DataSource dataSource) {
//        return new NamedParameterJdbcTemplate(dataSource);
//    }
//
//    @Bean
//    TransactionManager transactionManager(DataSource source) {
//        return new DataSourceTransactionManager(source);
//    }
//
//    @Bean
//    DataSourceInitializer initializer(DataSource source) {
//        var initializer = new DataSourceInitializer();
//        initializer.setDataSource(source);
//
//        var script = new ClassPathResource("schema.sql");
//        var populator = new ResourceDatabasePopulator(script);
//        initializer.setDatabasePopulator(populator);
//        return initializer;
//    }
}
