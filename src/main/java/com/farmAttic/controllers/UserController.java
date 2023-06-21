package com.farmAttic.controllers;

import com.farmAttic.services.UserAuthService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@Controller("v1/user")
@Introspected
@Transactional
@AllArgsConstructor
public class UserController {

    private UserAuthService userAuthService;

    @Get("/login")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public void login(@Header("authorization") String authorizationHeader, Authentication authentication){
        userAuthService.login(authorizationHeader, authentication);
    }

}
