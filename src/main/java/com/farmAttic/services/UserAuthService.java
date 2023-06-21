package com.farmAttic.services;

import com.farmAttic.Dtos.UserInfoDto;
import com.farmAttic.client.UserInfoClient;
import com.farmAttic.models.User;
import com.farmAttic.models.UserInfo;
import com.farmAttic.repositories.UserRepository;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Singleton
@AllArgsConstructor
public class UserAuthService {
    private UserRepository userRepository;
    private UserInfoClient userInfoClient;
    private static final ModelMapper modelMapper = new ModelMapper();

    public void login(String authorizationHeader, Authentication authentication) {
        String email = authentication.getAttributes().get("sub").toString();
        User currentUser = userRepository.findByEmail(email).orElse(new User());
        if(currentUser.getUserId() == null) {
            UserInfoDto userInfo = userInfoClient.getUserInfo(authorizationHeader);
            User user = modelMapper.map(userInfo, User.class);
            userRepository.save(user);
        }
    }
}
