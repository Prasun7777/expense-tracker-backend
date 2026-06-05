package com.prasun.expense_tracker.service;

import com.prasun.expense_tracker.entity.User;
import com.prasun.expense_tracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {

        if(repository.findByEmail(user.getEmail())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already exists");
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        return repository.save(user);
    }
    public User login(String email, String password) {

        User user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean matches =
                passwordEncoder.matches(
                        password,
                        user.getPassword());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid password");
        }

        return user;
    }
}