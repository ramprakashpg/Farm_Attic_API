package com.farmAttic.controllers;

import com.farmAttic.services.UserAuthService;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UserControllerTest {
    private final UserAuthService userAuthService = mock(UserAuthService.class);
    private final Authentication authentication = mock(Authentication.class);

}
