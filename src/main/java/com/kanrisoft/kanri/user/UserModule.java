package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.User;

// The interface which will expose our module
public interface UserModule {
    User findUserById(String id);
}