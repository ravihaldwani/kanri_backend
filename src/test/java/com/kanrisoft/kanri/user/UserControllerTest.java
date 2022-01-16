package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.TestUtil;
import com.kanrisoft.kanri.config.TestSecurityConfig;
import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private final String baseUrl = "/api/v1/user";

    @MockBean
    private UserService userService;

    @MockBean
    private TestSecurityConfig securityConfig;

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
            when(userService.getCurrentUser()).thenReturn(Optional.of(mockUser));

            mvc.perform(
                            get(baseUrl)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class Update {
    }


    @Nested
    class Activate {

    }
}