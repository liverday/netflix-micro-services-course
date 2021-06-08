package com.liverday.microservices.auth.services;

import com.liverday.microservices.auth.model.User;
import com.liverday.microservices.auth.model.dto.CreateUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsersService extends UserDetailsService {
    User findByUserName(String userName);
    User createUser(CreateUserDTO createUserDTO) throws Exception;
}
