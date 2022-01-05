package com.kanrisoft.kanri.user.repository;

import com.kanrisoft.kanri.user.model.User;

import java.util.Optional;

public interface UserRepository{
    User save(User user);

    Optional<User> findByEmail(String email);

    void deleteAll();
}
