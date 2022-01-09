package com.kanrisoft.kanri.user;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
