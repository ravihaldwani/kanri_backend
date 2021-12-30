package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    void validateRegistrationRequest(RegisterRequest request) throws IllegalStateException {
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
