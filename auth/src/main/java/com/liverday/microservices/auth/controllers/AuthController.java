package com.liverday.microservices.auth.controllers;

import com.liverday.microservices.auth.model.dto.LoginCredentialsDTO;
import com.liverday.microservices.auth.services.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/login")
@SuppressWarnings("unused")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> login(@RequestBody @Valid LoginCredentialsDTO credentials) {
        try {
            String token = authService.attemptAuthentication(credentials);

            Map<String, Object> model = Map.of("userName", credentials.getUserName(), "token", token);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid userName and password combination");
        }
    }
}
