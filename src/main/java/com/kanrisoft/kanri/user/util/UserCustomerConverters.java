package com.kanrisoft.kanri.user.util;

import com.kanrisoft.kanri.space.CustomConverter;
import com.kanrisoft.kanri.user.domain.Role;
import com.kanrisoft.kanri.user.domain.UserId;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Configuration
class UserCustomerConverters implements CustomConverter {
    @Override
    public @NotNull List<Converter<?, ?>> getConverters() {
        return asList(
                UserIdToLongConverter.INSTANCE,
                LongToUserIdConverter.INSTANCE,
                RoleToStringConverter.INSTANCE,
                StringToRoleConverter.INSTANCE
        );
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

    @WritingConverter
    enum RoleToStringConverter implements Converter<Set<Role>, String[]> {
        INSTANCE;

        @Override
        public String[] convert(Set<Role> source) {
            return source.stream().map(Enum::name).toArray(String[]::new);
        }
    }

    @ReadingConverter
    enum StringToRoleConverter implements Converter<String[], Set<Role>> {
        INSTANCE;

        @Override
        public Set<Role> convert(@NotNull String[] source) {
            return Arrays.stream(source).map(Role::valueOf).collect(Collectors.toSet());
        }
    }
}
