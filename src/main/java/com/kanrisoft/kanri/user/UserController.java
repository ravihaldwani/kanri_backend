package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.RegistrationRequest;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.service.UserService;
import com.kanrisoft.kanri.user.util.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody RegistrationRequest request) {
        var user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserUtils.mapUserToDto(user));
    }

    @GetMapping
    public ResponseEntity<UserDto> getMe() {
        var user = userService.getCurrentUser();
        return ResponseEntity.ok(UserUtils.mapUserToDto(user.get()));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto request) {
        var user = userService.updateUser(request);
        return ResponseEntity.ok(UserUtils.mapUserToDto(user));
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activateUser(@RequestParam(value = "key") String key) {
        userService.activateUser(key);
        // send a redirect to our home page
        return ResponseEntity.ok("User activated");
    }
}
