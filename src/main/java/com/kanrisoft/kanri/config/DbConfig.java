package com.kanrisoft.kanri.config;

import com.kanrisoft.kanri.user.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.List;

@Configuration
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

    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        var converters = List.of(UserIdToLongConverter.INSTANCE, LongToUserIdConverter.INSTANCE);
        return new JdbcCustomConversions(converters);

    }

    @WritingConverter
    enum UserIdToLongConverter implements Converter<UserId, Long> {
        INSTANCE;

        @Override
        public Long convert(UserId source) {
            return source.getId();
        }
    }

    @ReadingConverter
    enum LongToUserIdConverter implements Converter<Long, UserId> {
        INSTANCE;

        @Override
        public UserId convert(@NotNull Long source) {
            return UserId.of(source);
        }
    }
}
