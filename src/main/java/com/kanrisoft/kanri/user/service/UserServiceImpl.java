package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    @Override
    public User register(UserDto user) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
