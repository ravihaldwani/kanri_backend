package com.kanrisoft.kanri.user.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class UserDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Instant createdDate;
    private UserStatus status;
    private boolean activated;
}

