package com.kanrisoft.kanri.user.model;

import lombok.Data;

@Data
public class LoginRequest {
    final String email;
    final String password;
}
