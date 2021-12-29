package com.kanrisoft.kanri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanriApplication {

    public static void main(String[] args) {
        new SpringApplication(KanriApplication.class).run();
    }
}
