package com.kanrisoft.kanri.user.util;

import com.google.common.hash.Hashing;
import com.kanrisoft.kanri.user.domain.Role;
import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.model.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtils {
    public static UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail().getValue());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone().getValue());
        userDto.setStatus(user.getStatus());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setActivated(user.isActivated());
        return userDto;
    }

    public static String generateActivationKey(String base) {
        return Hashing.sha256().hashString(base, StandardCharsets.UTF_8).toString();
    }

    public static Set<Role> mapToRoleSet(Set<Object> roles) {
        return roles.stream().map(role -> {
            if (role instanceof Role) return (Role) role;
            else if (role instanceof String) return Role.valueOf((String) role);
            throw new IllegalStateException("");
        }).collect(Collectors.toSet());
    }
}
