package com.labsync.lms.service.impl;

import com.labsync.lms.dto.request.LoginRequest;
import com.labsync.lms.dto.request.RegisterRequest;
import com.labsync.lms.dto.response.JwtResponse;
import com.labsync.lms.exception.DuplicateResourceException;
import com.labsync.lms.exception.ResourceNotFoundException;
import com.labsync.lms.model.Role;
import com.labsync.lms.model.User;
import com.labsync.lms.repository.RoleRepository;
import com.labsync.lms.repository.UserRepository;
import com.labsync.lms.security.JwtUtils;
import com.labsync.lms.security.UserDetailsImpl;
import com.labsync.lms.service.AuthService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AuthServiceImpl — handles login (JWT issuance) and user registration.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // ── Login ────────────────────────────────────────────────────────────────
    @Override
    public JwtResponse login(LoginRequest req) {
        // Delegates to DaoAuthenticationProvider → UserDetailsServiceImpl
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        log.info("User \'{}\' logged in successfully", userDetails.getUsername());
        return JwtResponse.builder().token(jwt).type("Bearer").id(userDetails.getId()).username(userDetails.getUsername()).email(userDetails.getEmail()).fullName(userDetails.getFullName()).roles(roles).build();
    }

    // ── Register ─────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public User register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new DuplicateResourceException("Username \'" + req.getUsername() + "\' is already taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email \'" + req.getEmail() + "\' is already registered");
        }
        Set<Role> roles = resolveRoles(req.getRoles());
        User user = User.builder().username(req.getUsername()).email(req.getEmail()).password(passwordEncoder.encode(req.getPassword())).fullName(req.getFullName()).roles(roles).enabled(true).build();
        User saved = userRepository.save(user);
        log.info("New user registered: {} with roles: {}", saved.getUsername(), roles);
        return saved;
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    /**
     * Maps role name strings (e.g., "ROLE_ADMIN") to Role entities.
     * Defaults to ROLE_STAFF when no roles are specified.
     */
    private Set<Role> resolveRoles(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        if (roleNames == null || roleNames.isEmpty()) {
            roles.add(findRole(Role.ERole.ROLE_STAFF));
        } else {
            for (String roleName : roleNames) {
                switch (roleName.toUpperCase()) {
                    case "ROLE_ADMIN", "ADMIN" -> roles.add(findRole(Role.ERole.ROLE_ADMIN));
                    default -> roles.add(findRole(Role.ERole.ROLE_STAFF));
                }
            }
        }
        return roles;
    }

    private Role findRole(Role.ERole eRole) {
        return roleRepository.findByName(eRole).orElseThrow(() -> new ResourceNotFoundException("Role not found: " + eRole.name() + " — run DataInitializer to seed roles"));
    }

    public AuthServiceImpl(final AuthenticationManager authenticationManager, final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder passwordEncoder, final JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
}
