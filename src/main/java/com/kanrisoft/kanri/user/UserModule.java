package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.domain.User;
import com.kanrisoft.kanri.user.domain.UserId;

// The interface which will expose our module
public interface UserModule {
    User findUserById(UserId id);
}