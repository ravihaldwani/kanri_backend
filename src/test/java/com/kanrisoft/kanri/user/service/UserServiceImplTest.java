package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidator validator;

    @Autowired
    @InjectMocks
    private UserServiceImpl target;

    @Test
    void shouldThrowErrorIfInvalidData() {
        RegisterRequest user = new RegisterRequest("", "");
        doThrow(IllegalStateException.class).when(validator).validateRegistrationRequest(user);

        assertThrows(IllegalStateException.class, () -> target.register(user));
    }

    @Test
    void shouldRegisterIfValidData() {

    }

}