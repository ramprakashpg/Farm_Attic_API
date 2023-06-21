package com.farmAttic.services;

import com.farmAttic.Dtos.UserDto;
import com.farmAttic.client.UserInfoClient;
import com.farmAttic.models.User;
import com.farmAttic.repositories.UserRepository;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Singleton
@AllArgsConstructor
public class UserAuthService {
    private UserRepository userRepository;
    private UserInfoClient userInfoClient;
    private CartService cartService;
    private static final ModelMapper modelMapper = new ModelMapper();

    public void login(String authorizationHeader, Authentication authentication) {
        String email = authentication.getAttributes().get("sub").toString();
        User currentUser = userRepository.findByEmail(email).orElse(new User());
        if(currentUser.getUserId() == null) {
            currentUser = saveUserInfo(authorizationHeader);
        }
        assignCart(currentUser);

    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }
    private User saveUserInfo(String authorizationHeader) {
        UserDto userInfo = userInfoClient.getUserInfo(authorizationHeader);
        User user = modelMapper.map(userInfo, User.class);
        return userRepository.save(user);
    }
    private void assignCart(User userInfo) {
        cartService.generateCart(userInfo);
    }
}
