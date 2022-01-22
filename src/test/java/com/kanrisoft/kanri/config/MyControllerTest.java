package com.kanrisoft.kanri.config;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ContextConfiguration(classes = {TestSecurityConfig.class})
@AutoConfigureMockMvc()
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyControllerTest {
}
