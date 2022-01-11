package com.kanrisoft.kanri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJdbcRepositories
public class KanriApplication {

    public static void main(String[] args) {
        new SpringApplication(KanriApplication.class).run();
    }
}
