package com.kanrisoft.kanri.user.domain;

import com.kanrisoft.kanri.user.exception.EmailAlreadyUsedException;
import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.service.UserService;
import com.kanrisoft.kanri.user.service.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public User register(RegistrationRequest request) {
        validator.validateRegistrationRequest(request);

        if (repository.existsByEmail(Email.of(request.getEmail())))
            throw new EmailAlreadyUsedException(request.getEmail());

        UserEntity user = createUserFromRequest(request);
        return repository.save(user);
    }

    @NotNull
    UserEntity createUserFromRequest(RegistrationRequest request) {
        var encodedPassword = passwordEncoder.encode(request.getPassword());
        var phone = request.getPhone() != null ? PhoneNumber.of(request.getPhone()) : null;
        UserEntity user = UserEntity.of(request.getFirstName(), request.getLastName(), Email.of(request.getEmail()), encodedPassword, phone);
        user.addRole(Role.USER);
        return user;
    }

    @Override
    public void activateUser(String key) {
        var user = repository.findByActivationKey(key);
        user.orElseThrow(() -> new IllegalStateException("No User found for activation key"))
                .activateUser(key);
        repository.save(user.get());
    }

    @Override
    public User updateUser(UserDto request) {
        var userId = getCurrentUser().get().getId();
        var userEntity = repository.findById(userId).get();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setPhone(PhoneNumber.of(request.getPhone()));
        return repository.save(userEntity);
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return Optional.of((User) principal);
        }
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<? extends User> user = Optional.empty();
        try {
            user = repository.findByEmail(Email.of(username));
            log.debug(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user.orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
