package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.domain.UserId;
import com.kanrisoft.kanri.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
class UserModuleFacade implements UserModule {
    private final UserService service;

    UserModuleFacade(UserService service) {
        this.service = service;
    }

    @Override
    public User findUserById(UserId id) {
        return null;
    }
}
