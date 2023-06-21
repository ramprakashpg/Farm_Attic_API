package com.farmAttic.services;

import com.farmAttic.models.User;
import com.farmAttic.repositories.UserRepository;
import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
