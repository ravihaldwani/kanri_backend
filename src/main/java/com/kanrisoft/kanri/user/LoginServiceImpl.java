package com.kanrisoft.kanri.user;

import com.kanrisoft.kanri.security.jwt.TokenProvider;
import com.kanrisoft.kanri.user.model.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;

    public LoginServiceImpl(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String login(LoginRequest request) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        var authentication = authenticationManager.authenticate(auth);
        return jwtTokenUtil.generateToken(authentication);
    }
}
