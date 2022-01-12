package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User register(RegisterRequest request);

    void activateUser(String key);

    User updateUser(UserDto request);

    Optional<User> getCurrentUser();
}
