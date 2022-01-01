package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepoImpl implements UserRepository {
    private final Map<String, User> store = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    @Override
    public User save(User user) {
        store.put(user.getEmail(), user);
        return user;
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
