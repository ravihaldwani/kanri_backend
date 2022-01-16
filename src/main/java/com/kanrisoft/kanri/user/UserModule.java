package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.domain.User;

// The interface which will expose our module
public interface UserModule {
    User findUserById(String id);
}