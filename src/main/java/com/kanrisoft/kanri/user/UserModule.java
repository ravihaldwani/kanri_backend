package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.domain.UserId;

import java.util.Optional;

public interface UserModule {
    User findUserById(UserId id);

    Optional<User> getCurrentUser();

    Optional<UserId> getCurrentUserId();
}