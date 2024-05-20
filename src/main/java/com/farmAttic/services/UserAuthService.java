package com.farmAttic.services;

import com.farmAttic.Dtos.UserDto;
import com.farmAttic.client.UserInfoClient;
import com.farmAttic.models.User;
import com.farmAttic.repositories.UserRepository;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static com.farmAttic.AuthConstant.EMAIL;

@Singleton
@AllArgsConstructor
public class UserAuthService {
    private UserRepository userRepository;
    private UserInfoClient userInfoClient;
    private static final ModelMapper modelMapper = new ModelMapper();
    private StatefulRedisConnection<String, UserDto> connection;

    public User login(String authorizationHeader, Authentication authentication) {
        String email = authentication.getAttributes().get(EMAIL).toString();
        User currentUser = getCurrentUser(email);
        if (currentUser.getUserId() == null) {
            currentUser = saveUserInfo(authorizationHeader);
        }
        return currentUser;

    }

    public void login(String username) {
        User loggedInUser = userRepository.findByEmail(username);
        //Can't store POJO objects in redis. Micronaut doesnt support
        UserDto newUser = new UserDto(loggedInUser.getUserId(), loggedInUser.getEmail(), loggedInUser.getFirstName(), loggedInUser.getLastName());
        RedisCommands<String, UserDto> redisCommands = connection.sync();
        redisCommands.set("user", newUser);
        redisCommands.expire("user", 24);
    }

    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    private User saveUserInfo(String authorizationHeader) {
        UserDto userInfo = userInfoClient.getUserInfo(authorizationHeader);
        User user = modelMapper.map(userInfo, User.class);
        return userRepository.save(user);
    }
}
