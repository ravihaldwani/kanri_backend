package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.domain.UserId;
import com.kanrisoft.kanri.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserModuleFacade implements UserModule {
    private final UserService service;

    UserModuleFacade(UserService service) {
        this.service = service;
    }

    @Override
    public Optional<User> getCurrentUser() {
        return service.getCurrentUser();
    }

    @Override
    public Optional<UserId> getCurrentUserId() {
        return getCurrentUser().map(u -> UserId.of(u.getId()));
    }

    @Override
    public User findUserById(UserId id) {
        return null;
    }
}
