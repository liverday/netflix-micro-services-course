package com.liverday.microservices.auth.services.implementations;

import com.liverday.microservices.auth.jwt.JwtTokenProvider;
import com.liverday.microservices.auth.model.User;
import com.liverday.microservices.auth.model.dto.LoginCredentialsDTO;
import com.liverday.microservices.auth.services.IAuthService;
import com.liverday.microservices.auth.services.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@SuppressWarnings("unused")
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUsersService usersService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, IUsersService usersService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
    }

    @Override
    public String attemptAuthentication(LoginCredentialsDTO credentials) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getPassword()));
        User user = usersService.findByUserName(credentials.getUserName());

        if (user == null)
            throw new UsernameNotFoundException("Invalid userName and password combination");

        Map<String, Object> data = Map.of("roles", user.getRoles());

        return jwtTokenProvider.create(user.getUsername(), data);
    }
}
