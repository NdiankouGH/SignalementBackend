package com.signalement.controller;

import com.signalement.dto.AuthRequest;
import com.signalement.dto.AuthResponse;
import com.signalement.dto.UserDto;
import com.signalement.entity.RefreshToken;
import com.signalement.entity.User;
import com.signalement.exception.ApiException;
import com.signalement.service.impl.CustomUserDetailsService;
import com.signalement.service.impl.RefreshTokenService;
import com.signalement.service.impl.UserServiceImpl;
import com.signalement.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;
    private final RefreshTokenService refreshTokenService;


    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil, UserServiceImpl userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            logger.info("=== DEBUT LOGIN ===");
            logger.debug("Username: " + authRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            logger.info("Authentication FAILED: " + e.getMessage());
            return ResponseEntity.status(401).body("Incorrect username or password");
        } catch (Exception e) {
            logger.info("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal error");
        }
        final var userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final var jwt = jwtUtil.generateToken(userDetails);
        // Créer un refresh token
        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken.getToken(), userDetails.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto authRequest) {
        try {
            User user = userService.CreateUser(authRequest);
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Créer un refresh token
            RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(user.getUsername());

            return ResponseEntity.ok(new AuthResponse(jwt, refreshToken.getToken(), user.getUsername()));


        } catch (ApiException e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'inscription: " + e.getMessage());
        }
    }

}
