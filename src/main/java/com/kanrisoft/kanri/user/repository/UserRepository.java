package com.kanrisoft.kanri.user.repository;

import com.kanrisoft.kanri.user.model.User;

public interface UserRepository{
    public void save(User user);
    public User findByEmail(String email);

}
