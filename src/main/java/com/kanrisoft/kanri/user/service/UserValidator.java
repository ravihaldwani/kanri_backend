package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    public void validateRegistrationRequest(RegisterRequest request) throws InvalidRequestException {
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
            throw new InvalidRequestException();
        }
    }
}
