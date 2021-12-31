package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.InvalidRequestException;
import com.kanrisoft.kanri.user.UserEntity;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidator validator;

    @Autowired
    @InjectMocks
    private UserServiceImpl underTest;

    @Mock
    private UserRepository repository;

    @Test
    void shouldThrowErrorIfInvalidData() {
        RegisterRequest user = new RegisterRequest("", "");
        doThrow(InvalidRequestException.class).when(validator).validateRegistrationRequest(user);

        assertThrows(InvalidRequestException.class, () -> underTest.register(user));
    }

    @Test
    void shouldNotThrowErrorIfValidData() {
        RegisterRequest user = new RegisterRequest("test@test.com", "password");

        try {
            underTest.register(user);
        } catch (InvalidRequestException e) {
            fail("Should not have thrown Exception");
        }
    }

    @Test
    void shouldReturnUserIfValidData() {
        RegisterRequest request = new RegisterRequest("test@test.com", "password");
        UserEntity entity = new UserEntity();
        given(repository.findByEmail(request.getEmail())).willReturn(Optional.of(entity));

        try {
            var user = underTest.register(request);
            assertNotNull(user);
        } catch (InvalidRequestException e) {
            fail("Should not throw Exception");
        }
    }

}