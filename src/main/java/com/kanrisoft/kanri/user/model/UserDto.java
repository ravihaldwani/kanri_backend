package com.kanrisoft.kanri.user.model;

import com.kanrisoft.kanri.user.domain.UserStatus;

import java.time.Instant;

public record UserDto(
        String firstName,
        String lastName,
        String phone,
        String email,
        Instant createdDate,
        UserStatus status,
        boolean activated
) {
}
