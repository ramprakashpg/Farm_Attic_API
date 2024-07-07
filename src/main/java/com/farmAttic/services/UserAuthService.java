package com.farmAttic.services;

import com.farmAttic.Dtos.UserDto;
import com.farmAttic.client.UserInfoClient;
import com.farmAttic.models.User;
import com.farmAttic.repositories.UserRepository;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.session.Session;
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
//    private final Session session;

    public User login(Authentication authentication) {
        String email = authentication.getAttributes().get(EMAIL).toString();
        return getCurrentUser(email);

    }

//    public void login(String username) {
//        User loggedInUser = userRepository.findByEmail(username);
//        //NOTE: Can't store POJO objects in redis. Micronaut doesnt support
//        if (loggedInUser != null) {
//            System.out.println("Logged in user: " + loggedInUser.getEmail());
//            session.put("user", loggedInUser);
//        }
//    }

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
