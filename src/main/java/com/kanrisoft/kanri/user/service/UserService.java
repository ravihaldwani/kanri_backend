package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(RegisterRequest request);
}
