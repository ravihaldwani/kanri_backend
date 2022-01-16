package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.LoginRequest;
import org.springframework.security.core.AuthenticationException;

public interface LoginService {
    String login(LoginRequest request) throws AuthenticationException;
}
