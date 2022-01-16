package com.kanrisoft.kanri.space;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface ConverterProvider {
    @NotNull
    List<Converter<?, ?>> getConverters();
}
