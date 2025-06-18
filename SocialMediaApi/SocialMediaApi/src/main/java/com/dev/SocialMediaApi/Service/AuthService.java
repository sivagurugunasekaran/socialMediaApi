package com.dev.SocialMediaApi.Service;

import java.util.Optional;

public interface AuthService {
    // Registers a new user with the provided username and password
    String register(String username, String password);

    // Authenticates user and returns a JWT token if successful
    Optional<String> login(String username, String password);

    // Checks if the user already exists
    boolean exists(String username);
}

