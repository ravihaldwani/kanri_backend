package com.kanrisoft.kanri.user.service;

import com.kanrisoft.kanri.user.EmailAlreadyUsedException;
import com.kanrisoft.kanri.user.UserEntity;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.Role;
import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class UserServiceImpl implements UserService {
    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    UserServiceImpl(UserValidator validator, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
        this.repository = userRepository;
    }

    @Override
    public User register(RegisterRequest request) {
        validator.validateRegistrationRequest(request);

        if (repository.findByEmail(request.getEmail()).isPresent()) throw new EmailAlreadyUsedException();

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addRole(Role.USER);
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmail(username);
        log.debug(user.toString());
        return user.orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
