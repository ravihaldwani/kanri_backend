package com.kanrisoft.kanri.user.repository;

import com.kanrisoft.kanri.user.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepoImpl implements UserRepository{

    Map<String,User> userData = new HashMap<>();

    @Override
    public void save(User user) {
        userData.put(user.getEmail(),user);
    }

    @Override
    public User findByEmail(String email) {
       return userData.get(email);
    }
}
