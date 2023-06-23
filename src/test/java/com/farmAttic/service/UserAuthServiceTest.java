package com.farmAttic.service;

import com.farmAttic.Dtos.UserDto;
import com.farmAttic.client.UserInfoClient;
import com.farmAttic.models.User;
import com.farmAttic.repositories.UserRepository;
import com.farmAttic.services.CartService;
import com.farmAttic.services.UserAuthService;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.*;

class UserAuthServiceTest {

    private UserRepository userRepository;
    private final UserInfoClient userInfoClient = mock(UserInfoClient.class);
    private final Authentication authentication = mock(Authentication.class);
    private UserAuthService userAuthService;
    private final CartService cartService = mock(CartService.class);


    @BeforeEach
    void beforeEach(){
        userRepository = mock(UserRepository.class);
        userAuthService = new UserAuthService(userRepository, userInfoClient, cartService);
    }

    @Test
    void shouldSaveLoggedInUser() {
        String email = "dummy@thoughtworks.com";
        Map<String, Object> authMap = new HashMap<>();
        authMap.put("sub", email);

        User currentUser = new User();
        currentUser.setEmail("dummy@thoughtworks.com");
        currentUser.setFirstName("Dummy");
        currentUser.setLastName("Name");

        UserDto loggedInUserInfo = new UserDto();
        loggedInUserInfo.setEmail(email);
        loggedInUserInfo.setFirstName("Dummy");
        loggedInUserInfo.setLastName("Name");

        String authHeader = "authHeader";

        when(authentication.getAttributes()).thenReturn(authMap);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(currentUser));
        when(userInfoClient.getUserInfo(authHeader)).thenReturn(loggedInUserInfo);

        userAuthService.login(authHeader,authentication);
        verify(userRepository).save(any());
    }
}
