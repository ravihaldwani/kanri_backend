package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.TestUtil;
import com.kanrisoft.kanri.config.TestSecurityConfig;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {
    private final String baseUrl = "/api/v1/user";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Nested
    class Register {

        @Test
        void shouldRegisterUser() throws Exception {
            byte[] body = TestUtil.convertObjectToJsonBytes(new RegisterRequest());

            mvc.perform(post(baseUrl + "/register").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class GenerateToken {

    }

    @Nested
    class GetMe {

    }

    @Nested
    class Update {
    }


    @Nested
    class Activate {

    }
}