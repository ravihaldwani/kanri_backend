package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.security.jwt.TokenProvider;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
class UserController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;

    UserController(
            AuthenticationManager authenticationManager,
            TokenProvider jwtTokenUtil,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            var user = userService.register(request);
            return ResponseEntity.ok(UserUtils.mapUserToDto(user));
        } catch (InvalidRequestException exp) {
            return ResponseEntity.badRequest().body(exp);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> generateToken(@RequestBody UserDto request) throws AuthenticationException {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        var authentication = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(token + " success");
    }
}
