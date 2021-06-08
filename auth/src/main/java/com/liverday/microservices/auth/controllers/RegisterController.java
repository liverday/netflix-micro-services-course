package com.liverday.microservices.auth.controllers;

import com.liverday.microservices.auth.model.User;
import com.liverday.microservices.auth.model.dto.CreateUserDTO;
import com.liverday.microservices.auth.services.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@SuppressWarnings("unused")
public class RegisterController {
    private final IUsersService usersService;

    @Autowired
    public RegisterController(IUsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            User user = usersService.createUser(createUserDTO);
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
