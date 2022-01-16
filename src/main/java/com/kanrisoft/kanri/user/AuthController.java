package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.user.model.LoginRequest;
import com.kanrisoft.kanri.user.service.LoginService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
class AuthController {
    private final LoginService loginService;

    AuthController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping(value = "api/v1/login")
    public ResponseEntity<Object> generateToken(@RequestBody LoginRequest request) {
        var token = loginService.login(request);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                ).body("Logged in successfully");
    }

}
