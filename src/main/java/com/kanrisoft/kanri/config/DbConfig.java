package com.kanrisoft.kanri.config;

import com.kanrisoft.kanri.space.CustomConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DbConfig extends AbstractJdbcConfiguration {
    private final List<CustomConverter> converters;

    public DbConfig(List<CustomConverter> converters) {
        this.converters = converters;
    }

    @Override
    @Bean
    public @NotNull JdbcCustomConversions jdbcCustomConversions() {
        var converters = this.converters
                .stream()
                .map(CustomConverter::getConverters)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new JdbcCustomConversions(converters);
    }
}
