package com.labsync.lms.service;

import com.labsync.lms.dto.request.LoginRequest;
import com.labsync.lms.dto.request.RegisterRequest;
import com.labsync.lms.dto.response.JwtResponse;
import com.labsync.lms.model.User;

/**
 * AuthService — contract for authentication and user registration.
 */
public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    User register(RegisterRequest registerRequest);
}
