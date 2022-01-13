package com.kanrisoft.kanri.space;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.Collections;
import java.util.List;

@Configuration
public class SpaceConverters implements CustomConverter {
    @Override
    public @NotNull List<Converter<?, ?>> getConverters() {
        return Collections.emptyList();
    }

}
