package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.UserUtils;
import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {
    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = userRepository;
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
//        return repository.findByEmail(username);



//    }
}
