package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.service.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidator validator;

    @Mock
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    //    @Autowired
//    @InjectMocks
    private UserServiceImpl underTest;


    @BeforeEach
    void beforeEach() {
        underTest = new UserServiceImpl(validator, encoder, repository);
    }

    @Test
    void shouldThrowErrorIfInvalidData() {
        RegisterRequest user = new RegisterRequest();
        doThrow(InvalidRequestException.class).when(validator).validateRegistrationRequest(user);

        assertThrows(InvalidRequestException.class, () -> underTest.register(user));
    }

    @Test
    void shouldNotThrowErrorIfValidData() {
        RegisterRequest user = new RegisterRequest();
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
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");

        try {
            var user = underTest.register(request);
            assertNotNull(user);
        } catch (InvalidRequestException e) {
            fail("Should not throw Exception");
        }
    }

}