package com.farmAttic.services;

import com.farmAttic.Dtos.UserDto;
import com.farmAttic.Dtos.UserInfoDto;
import com.farmAttic.client.UserInfoClient;
import com.farmAttic.models.User;
import com.farmAttic.repositories.UserRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Singleton
@AllArgsConstructor
public class UserAuthService {
    private UserRepository userRepository;
    private UserInfoClient userInfoClient;
    private static final ModelMapper modelMapper = new ModelMapper();


    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User saveUserInfo(UserInfoDto userInfo) {
        User user = modelMapper.map(userInfo, User.class);
        return userRepository.save(user);
    }

    public boolean isValid(Object username, Object password) {
        User user = userRepository.findByEmail((String) username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
