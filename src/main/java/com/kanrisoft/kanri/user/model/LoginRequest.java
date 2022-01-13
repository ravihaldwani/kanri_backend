package com.kanrisoft.kanri.user.model;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
