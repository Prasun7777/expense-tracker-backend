package com.prasun.expense_tracker.controller;

import com.prasun.expense_tracker.entity.User;
import com.prasun.expense_tracker.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.prasun.expense_tracker.dto.LoginRequest;
import com.prasun.expense_tracker.dto.LoginResponse;
import com.prasun.expense_tracker.security.JwtService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final JwtService jwtService;

    public UserController(
            UserService service,
            JwtService jwtService) {

        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public User register(
            @RequestBody User user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        User user = service.login(
                request.getEmail(),
                request.getPassword());

        String token =
                jwtService.generateToken(
                        user.getEmail());

        return new LoginResponse(token);
    }
}