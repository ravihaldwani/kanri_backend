package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User register(RegistrationRequest request);

    void activateUser(String key);

    User updateUser(UserDto request);

    Optional<User> getCurrentUser();
}
