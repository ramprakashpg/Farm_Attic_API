package com.farmAttic.controllers;

import com.farmAttic.services.UserAuthService;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;

import static org.mockito.Mockito.*;

public class UserControllerTest {
    private final UserAuthService userAuthService = mock(UserAuthService.class);
    private final Authentication authentication = mock(Authentication.class);

    private final UserController userController = new UserController(userAuthService);
    @Test
    void shouldSaveLoggedInUserInfo() {
        String authHeader = "authHeader";
        doNothing().when(userAuthService).login(authHeader,authentication);

        userController.login(authHeader,authentication);
        verify(userAuthService).login(authHeader,authentication);

    }
}
