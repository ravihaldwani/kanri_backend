package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.security.jwt.TokenProvider;
import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.LoginRequest;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.User;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
class UserController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;

    UserController(
            AuthenticationManager authenticationManager,
            TokenProvider jwtTokenUtil,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            var user = userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserUtils.mapUserToDto(user));
        } catch (InvalidRequestException exp) {
            return ResponseEntity.badRequest().body(exp);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> generateToken(@RequestBody LoginRequest request) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        var authentication = authenticationManager.authenticate(auth);
        final String token = jwtTokenUtil.generateToken(authentication);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                ).body("Logged in successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(UserUtils.mapUserToDto(user));
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activateUser(@RequestParam(value = "key") String key) {
        userService.activateUser(key);
        // send a redirect to our home page
        return ResponseEntity.ok("User activated");
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto request) {
        var user = userService.updateUser(request);
        return ResponseEntity.ok(UserUtils.mapUserToDto(user));
    }
}
