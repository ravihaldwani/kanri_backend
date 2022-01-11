package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class UserValidatorTest {
    UserValidator underTest;

    @BeforeEach
    void setup() {
        underTest = new UserValidator();
    }

    @Test
    void shouldThrowErrorIfInvalidData() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("");
        request.setPassword("");

        assertThrows(InvalidRequestException.class, () -> underTest.validateRegistrationRequest(request));
    }

    @Test
    void shouldNotThrowErrorIfValidData() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");

        try {
            underTest.validateRegistrationRequest(request);
        } catch (InvalidRequestException e) {
            fail("Should not throw error for valid user");
        }
    }

}