package com.farmAttic.controllers;

import com.farmAttic.models.User;
import com.farmAttic.services.UserAuthService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.session.Session;
import io.micronaut.session.annotation.SessionValue;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Introspected
@Transactional
@AllArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserAuthService userAuthService;

    @Post("/login1")
    public String login(Session session, @Body User user) {
        session.put("user", user);
        return "User logged in";
    }
    @Get("/hello")
    public User hello(@SessionValue("user") User user) {
        return user != null ? user : new User();
    }

}
