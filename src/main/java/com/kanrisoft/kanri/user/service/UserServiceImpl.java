package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.User;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {
    private final UserValidator validator;

    UserServiceImpl(UserValidator validator) {
        this.validator = validator;
    }

    @Override
    public User register(RegisterRequest user) {
        validator.validateRegistrationRequest(user);
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
