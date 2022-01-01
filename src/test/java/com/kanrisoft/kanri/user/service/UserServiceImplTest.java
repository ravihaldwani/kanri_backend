package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserValidator validator;

    private UserRepository repository = new UserRepoImpl();

    //    @Autowired
//    @InjectMocks
    private UserServiceImpl underTest;


    @BeforeEach
    void beforeEach() {
        underTest = new UserServiceImpl(validator, repository);
    }

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
//        given(repository.findByEmail(request.getEmail())).willReturn(Optional.empty());

        try {
            var user = underTest.register(request);
            assertNotNull(user);
        } catch (InvalidRequestException e) {
            fail("Should not throw Exception");
        }
    }

}