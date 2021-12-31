package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepoImpl implements UserRepository {
    private List<User> store = new ArrayList<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User save(User user) {
        store.add(user);
        return user;
    }
}
