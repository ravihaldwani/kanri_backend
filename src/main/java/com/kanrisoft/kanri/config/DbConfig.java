package com.kanrisoft.kanri.config;

import com.kanrisoft.kanri.user.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import static java.util.Arrays.asList;

@Configuration
public class DbConfig extends AbstractJdbcConfiguration {

    @Override
    @Bean
    public @NotNull JdbcCustomConversions jdbcCustomConversions() {
        var converters = asList(UserIdToLongConverter.INSTANCE, LongToUserIdConverter.INSTANCE);
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
