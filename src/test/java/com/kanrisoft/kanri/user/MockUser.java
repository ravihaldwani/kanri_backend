package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.LoginRequest;
import com.kanrisoft.kanri.user.model.RegistrationRequest;


public class MockUser {

    static RegistrationRequest getRegistrationRequest() {
        var request = new RegistrationRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");
        request.setFirstName("first");
        request.setLastName("last");
        request.setPhone("324971290348");
        return request;
    }

    static LoginRequest getLoginRequest(String email, String password) {
        return new LoginRequest(email, password);
    }

}
