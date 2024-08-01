package com.farmAttic.controllers;

import com.farmAttic.Dtos.UserInfoDto;
import com.farmAttic.models.User;
import com.farmAttic.services.UserAuthService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Controller
@Introspected
@AllArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthService.class);
    private UserAuthService userAuthService;

    @Post(value = "/v1/user", produces = MediaType.APPLICATION_JSON)
    public UUID register(@Body UserInfoDto user) {
        LOGGER.info("Registering user: {}", user.getEmail());
        User newUser = userAuthService.saveUserInfo(user);
        return newUser.getUserId();
    }

}
