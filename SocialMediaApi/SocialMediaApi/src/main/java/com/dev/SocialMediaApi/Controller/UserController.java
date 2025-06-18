package com.dev.SocialMediaApi.Controller;

import com.dev.SocialMediaApi.DTO.LoginRequest;
import com.dev.SocialMediaApi.DTO.UserRequest;
import com.dev.SocialMediaApi.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// UserController.java
/**
 * User Controller handling user registration and authentication
 *
 * This controller manages user-related operations including registration and login.
 * It provides RESTful endpoints for user authentication and uses JWT tokens for security.
 *
 * Endpoints:
 * - POST /auth/register - User registration
 * - POST /auth/login - User login
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthService authService;

    /**
     * Register a new user in the system
     *
     * This endpoint handles user registration by:
     * 1. Checking if username already exists
     * 2. Creating new user if username is available
     * 3. Returning appropriate response based on operation result
     *
     * @param request UserRequest object containing username and password
     * @return ResponseEntity with success message and user info, or error message if user exists
     */

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        // Check if user already exists to prevent duplicates
        if (authService.exists(request.getUsername())) {
            // Return 400 Bad Request if user already exists
            return ResponseEntity.badRequest().body("User already exists");
        }
        // Register new user and return success response with user details
        return ResponseEntity.ok(authService.register(request.getUsername(), request.getPassword()));
    }

    /**
     * Authenticate user and provide JWT token
     *
     * This endpoint handles user login by:
     * 1. Validating credentials against database
     * 2. Generating JWT token if credentials are valid
     * 3. Returning token or error message
     *
     * @param request LoginRequest object containing username and password
     * @return ResponseEntity with JWT token if successful, 401 Unauthorized if credentials invalid
     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Attempt login and handle Optional return type
        return authService.login(request.getUsername(), request.getPassword())
                .map(token -> ResponseEntity.ok().body(token))// Return token if login successful
                .orElse(ResponseEntity.status(401).body("Invalid credentials")); // Return 401 if login failed
    }
}

