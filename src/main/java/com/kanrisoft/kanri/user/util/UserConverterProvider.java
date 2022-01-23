package com.kanrisoft.kanri.user.util;

import com.kanrisoft.kanri.shared.ConverterProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.Collections;
import java.util.List;

@Configuration
class UserConverterProvider implements ConverterProvider {

    @Override
    public @NotNull List<Converter<?, ?>> getConverters() {
        return Collections.emptyList();
    }

}
