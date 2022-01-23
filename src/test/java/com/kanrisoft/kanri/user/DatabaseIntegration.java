package com.kanrisoft.kanri.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@DataJdbcTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = DatabaseIntegration.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public abstract class DatabaseIntegration {

    public static PostgreSQLContainer<?> pgDBContainer;

    static {
        pgDBContainer = new PostgreSQLContainer<>("postgres:13.4")
                .withInitScript("schema.sql");
        pgDBContainer.start();
    }

    public static class DockerPostgreDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + pgDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + pgDBContainer.getUsername(),
                    "spring.datasource.password=" + pgDBContainer.getPassword()
            );
        }
    }
}