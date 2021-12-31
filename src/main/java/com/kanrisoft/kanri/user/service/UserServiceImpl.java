package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.EmailAlreadyUsedException;
import com.kanrisoft.kanri.user.UserEntity;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.User;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {
    private final UserValidator validator;
    private final UserRepository repository;

    UserServiceImpl(UserValidator validator, UserRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    @Override
    public User register(RegisterRequest request) {
        validator.validateRegistrationRequest(request);

        if (repository.findByEmail(request.getEmail()).isPresent()) throw new EmailAlreadyUsedException();
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return repository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}
