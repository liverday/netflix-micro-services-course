package com.liverday.microservices.auth.services;

import com.liverday.microservices.auth.model.dto.LoginCredentialsDTO;
import org.springframework.security.core.AuthenticationException;

public interface IAuthService {
    String attemptAuthentication(LoginCredentialsDTO credentials) throws AuthenticationException;
}
