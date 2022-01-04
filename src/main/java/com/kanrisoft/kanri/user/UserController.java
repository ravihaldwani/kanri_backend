package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.security.jwt.TokenProvider;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.repository.UserRepository;
import com.kanrisoft.kanri.user.utils.PasswordEncoderUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
class UserController {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;


   // @Lazy
    UserController(UserRepository userRepository, AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    ResponseEntity<Object> register(@RequestBody UserDto request) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(UserUtils.mapDtoToUser(request));
        return ResponseEntity.ok("Registered");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateToken(@RequestBody UserDto request) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(token +" success");
    }
}
