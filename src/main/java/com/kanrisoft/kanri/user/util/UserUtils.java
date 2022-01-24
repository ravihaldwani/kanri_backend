package com.kanrisoft.kanri.user.util;

import com.google.common.hash.Hashing;
import com.kanrisoft.kanri.user.domain.PhoneNumber;
import com.kanrisoft.kanri.user.domain.Role;
import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.model.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtils {
    public static UserDto mapUserToDto(User user) {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getPhone().map(PhoneNumber::value).orElse(null),
                user.getEmail().value(),
                user.getCreatedDate(),
                user.getStatus(),
                user.isActivated()
        );
    }

    public static String generateActivationKey(String base) {
        return Hashing.sha256().hashString(base, StandardCharsets.UTF_8).toString();
    }

    public static Set<Role> mapToRoleSet(Set<Object> roles) {
        return roles.stream().map(role -> {
            if (role instanceof Role r) return r;
            else if (role instanceof String r) return Role.valueOf(r);
            throw new IllegalStateException("");
        }).collect(Collectors.toSet());
    }
}
