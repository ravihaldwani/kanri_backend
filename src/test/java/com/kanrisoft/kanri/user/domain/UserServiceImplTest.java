package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.user.MockUser;
import com.kanrisoft.kanri.user.exception.EmailAlreadyUsedException;
import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.service.UserValidator;
import org.junit.jupiter.api.Nested;
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
import static org.mockito.Mockito.*;

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

    @Nested
    class Register {

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
            given(repository.existsByEmail(Email.of(request.getEmail()))).willReturn(true);

            assertThrows(EmailAlreadyUsedException.class, () -> underTest.register(request));
        }
    }

    @Nested
    class ActivateUser {
        @Test
        void shouldActivateUser() {
            String key = "asdfasdfhhjasdgfhjkagsdfjhadsgfjkhasdf";
            var mockUser = mock(UserEntity.class);
            var keyCapture = ArgumentCaptor.forClass(String.class);
            given(repository.findByActivationKey(keyCapture.capture())).willReturn(Optional.of(mockUser));

            underTest.activateUser(key);

            verify(mockUser).activateUser(key);
            assertEquals(key, keyCapture.getValue());
        }

        @Test
        void shouldThrowExceptionIfActivationKeyNotFound() {
            String key = "asdfasdfhhjasdgfhjkagsdfjhadsgfjkhasdf";
            given(repository.findByActivationKey(key)).willReturn(Optional.empty());

            var errMsg = assertThrows(IllegalStateException.class, () -> underTest.activateUser(key)).getMessage();
            assertEquals("No User found for activation key", errMsg);
        }
    }

    @Nested
    class CreateUserFromRequest {
        @Test
        void shouldEncodePasswordForUser() {
            var request = MockUser.getRegistrationRequest();
            String encodedPassword = "adfhjasgflasdfglasfgkjahsdfgasjhfgaksjdfh";
            given(encoder.encode(request.getPassword())).willReturn(encodedPassword);

            var user = underTest.createUserFromRequest(request);

            assertEquals(encodedPassword, user.getPassword());
        }

        @Test
        void userCreatedShouldMatchRegistrationRequest() {
            var request = MockUser.getRegistrationRequest();
            String encodedPassword = "adfhjasgflasdfglasfgkjahsdfgasjhfgaksjdfh";
            given(encoder.encode(request.getPassword())).willReturn(encodedPassword);

            var user = underTest.createUserFromRequest(request);

            assertAll(
                    () -> assertEquals(request.getFirstName(), user.getFirstName()),
                    () -> assertEquals(request.getLastName(), user.getLastName()),
                    () -> assertEquals(request.getEmail(), user.getEmail().getValue()),
                    () -> assertEquals(request.getPhone(), user.getPhone())
            );
        }

        @Test
        void createdUserShouldHaveRoleUser() {
            var request = MockUser.getRegistrationRequest();
            String encodedPassword = "adfhjasgflasdfglasfgkjahsdfgasjhfgaksjdfh";
            given(encoder.encode(request.getPassword())).willReturn(encodedPassword);

            var user = underTest.createUserFromRequest(request);

            assertEquals(1, user.getRoles().size());
            assertEquals(Role.USER, user.getRoles().stream().findFirst().get());
        }
    }
}