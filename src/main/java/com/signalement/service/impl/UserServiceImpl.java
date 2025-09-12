package com.signalement.service.impl;

import com.signalement.dao.UserRepository;
import com.signalement.dto.UserDto;
import com.signalement.entity.Role;
import com.signalement.entity.User;
import com.signalement.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User CreateUser(UserDto user) {
        // Verifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RequestException("Cet utilisateur existe déjà", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RequestException("Cet email existe déjà", HttpStatus.BAD_REQUEST);
        }
        User createdUser = new User();
        createdUser.setUsername(user.getUsername());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setRole(Role.valueOf(user.getRole()));
        createdUser.setActive(true);
        createdUser.setInscriptionDate(LocalDate.now());

        return userRepository.save(createdUser);
    }
}

