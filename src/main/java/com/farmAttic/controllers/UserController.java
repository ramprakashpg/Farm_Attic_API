package com.farmAttic.controllers;

import com.farmAttic.models.User;
import com.farmAttic.services.UserAuthService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.session.Session;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;

import java.util.UUID;

import static com.farmAttic.AuthConstant.EMAIL;

@Controller
@Introspected
@Transactional
@AllArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserAuthService userAuthService;

    @Get("/v1/user/login")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public MutableHttpResponse<UUID> login(@Header("authorization") String authorizationHeader, Authentication authentication){
        LOGGER.info("Logged in user:{}",authentication.getAttributes().get(EMAIL));
        User user = userAuthService.login(authorizationHeader, authentication);
        return HttpResponse.ok(user.getUserId());
    }
//    @Post("/login")
//    User setupLoggedInUser(Session session, String name){
//        return session.get("user", User.class).orElseGet(()->
//        {
//            User newUser = new User(UUID.randomUUID(), "email", "name1", "name2");
//            session.put("user", newUser);
//            return newUser;
//
//        });
//    }

}
