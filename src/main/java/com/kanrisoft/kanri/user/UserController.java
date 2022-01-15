package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.exception.InvalidRequestException;
import com.kanrisoft.kanri.user.model.LoginRequest;
import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.service.UserService;
import com.kanrisoft.kanri.user.util.UserUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
class UserController {
    private final UserService userService;
    private final LoginService loginService;

    UserController(
            UserService userService,
            LoginService loginService
    ) {
        this.userService = userService;
        this.loginService = loginService;
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
    public ResponseEntity<Object> generateToken(@RequestBody LoginRequest request) {
        var token = loginService.login(request);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                ).body("Logged in successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        var user = userService.getCurrentUser();
        return ResponseEntity.ok(UserUtils.mapUserToDto(user.get()));
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
