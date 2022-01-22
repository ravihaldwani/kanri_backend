package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.TestUtil;
import com.kanrisoft.kanri.config.MyControllerTest;
import com.kanrisoft.kanri.user.domain.Email;
import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MyControllerTest
@WebMvcTest(UserController.class)
@Import(UserController.class)
class UserControllerTest {
    private final String baseUrl = "/api/v1/user";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Nested
    class Register {

        @Test
        void shouldRegisterUser() throws Exception {
            var request = MockUser.getRegistrationRequest();
            byte[] body = TestUtil.convertObjectToJsonBytes(request);
            var capture = ArgumentCaptor.forClass(RegistrationRequest.class);
            var mockUser = mock(User.class);
            when(mockUser.getEmail()).thenReturn(Email.of(request.getEmail()));
            when(userService.register(capture.capture())).thenReturn(mockUser);

            mvc.perform(
                            post(baseUrl + "/register").contentType(MediaType.APPLICATION_JSON)
                                    .content(body))
                    .andExpect(status().isCreated());

            assertEquals(request, capture.getValue());
        }
    }

    @Nested
    class GetMe {

        @Test
        @WithMockUser
        void shouldReturnUserDto() throws Exception {
            var mockUser = mock(User.class);
            when(mockUser.getEmail()).thenReturn(Email.of("test@test.com"));
            when(userService.getCurrentUser()).thenReturn(Optional.of(mockUser));

            mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldThrowAccessDenied() throws Exception {
            mvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class Update {
        @Test
        void shouldThrowAccessDenied() throws Exception {
            mvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        }
    }


    @Nested
    class Activate {

    }
}