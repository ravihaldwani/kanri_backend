package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.TestUtil;
import com.kanrisoft.kanri.config.TestSecurityConfig;
import com.kanrisoft.kanri.user.service.LoginService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginService loginService;

    @Nested
    class GenerateToken {

        @Test
        void shouldGenerateToken() throws Exception {
            var request = MockUser.getLoginRequest("test@test.com", "password");
            byte[] body = TestUtil.convertObjectToJsonBytes(request);
            String dummyToken = "asdfadsfahsjflkhadsjfhalkdjfhalkdfhaldfhadslfjhk";
            when(loginService.login(request)).thenReturn(dummyToken);

            mvc.perform(
                            post("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                                    .content(body)
                    )
                    .andExpect(status().isOk())
                    .andExpect(header().stringValues("Authorization", dummyToken));
        }

        @Test
        void shouldReturn401ForInvalidUser() throws Exception {
            var request = MockUser.getLoginRequest("test@test.com", "password");
            byte[] body = TestUtil.convertObjectToJsonBytes(request);
            when(loginService.login(request)).thenThrow(BadCredentialsException.class);

            mvc.perform(
                            post("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                                    .content(body)
                    )
                    .andExpect(status().isUnauthorized());
        }
    }
}