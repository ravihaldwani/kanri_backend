package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.RegisterRequest;
import com.kanrisoft.kanri.user.model.UserDto;
import com.kanrisoft.kanri.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        var user = userService.register(request);
        return ResponseEntity.ok(UserUtils.mapUserToDto(user));
    }
}
