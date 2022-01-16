package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegistrationRequest;
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
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("");
        request.setPassword("");

        assertThrows(InvalidRequestException.class, () -> underTest.validateRegistrationRequest(request));
    }

    @Test
    void shouldNotThrowErrorIfValidData() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");

        try {
            underTest.validateRegistrationRequest(request);
        } catch (InvalidRequestException e) {
            fail("Should not throw error for valid user");
        }
    }

}