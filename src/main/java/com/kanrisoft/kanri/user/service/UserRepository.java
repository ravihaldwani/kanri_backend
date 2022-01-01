package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteAll();
}
