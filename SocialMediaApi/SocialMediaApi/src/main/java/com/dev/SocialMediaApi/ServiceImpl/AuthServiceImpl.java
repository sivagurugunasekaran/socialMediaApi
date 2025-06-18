package com.dev.SocialMediaApi.ServiceImpl;

import com.dev.SocialMediaApi.Model.User;
import com.dev.SocialMediaApi.Service.AuthService;
import com.dev.SocialMediaApi.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;
    // In-memory user store
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

    // Registers a new user by storing in memory
    @Override
    public String register(String username, String password) {
        userStore.put(username, new User(username, password));
        return "User registered successfully";
    }

    // Authenticates the user and returns JWT token if credentials are valid
    @Override
    public Optional<String> login(String username, String password) {
        User user = userStore.get(username);
        if (user != null && user.getPassword().equals(password)) {
            // Return JWT if credentials are valid
            return Optional.of(jwtUtil.generateToken(username));
        }
        return Optional.empty();
    }

    // Checks whether a user with the given username already exists
    @Override
    public boolean exists(String username) {
        return userStore.containsKey(username);
    }
}

