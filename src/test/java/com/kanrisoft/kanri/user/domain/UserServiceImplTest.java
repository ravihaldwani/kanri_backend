package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.user.exception.EmailAlreadyUsedException;
import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.service.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidator validator;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository repository;

    @Autowired
    @InjectMocks
    private UserServiceImpl underTest;


    @Test
    void shouldThrowErrorIfInvalidData() {
        RegistrationRequest user = new RegistrationRequest();
        doThrow(InvalidRequestException.class).when(validator).validateRegistrationRequest(user);

        assertThrows(InvalidRequestException.class, () -> underTest.register(user));
    }

    @Test
    void shouldNotThrowErrorIfValidData() {
        RegistrationRequest user = new RegistrationRequest();
        user.setEmail("test@test.com");
        user.setPassword("password");

        try {
            underTest.register(user);
        } catch (InvalidRequestException e) {
            fail("Should not have thrown Exception");
        }
    }

    @Test
    void shouldReturnUserIfValidData() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");
        var argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        var mockUser = mock(UserEntity.class);
        given(repository.save(argumentCaptor.capture())).willReturn(mockUser);

        try {
            var user = underTest.register(request);
            assertNotNull(user);
        } catch (InvalidRequestException e) {
            fail("Should not throw Exception");
        }
    }

    @Test
    void shouldThrowEmailExistsException() {
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");
        var mockUser = mock(UserEntity.class);
        given(repository.findByEmail(request.getEmail())).willReturn(Optional.of(mockUser));

        assertThrows(EmailAlreadyUsedException.class, () -> underTest.register(request));
    }

}