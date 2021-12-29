package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(UserDto user);
}
