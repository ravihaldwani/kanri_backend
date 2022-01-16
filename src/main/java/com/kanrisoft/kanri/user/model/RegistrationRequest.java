package com.kanrisoft.kanri.user.model;

import lombok.*;

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
}
