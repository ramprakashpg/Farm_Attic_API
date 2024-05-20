package com.farmAttic.controllers;

import com.farmAttic.Dtos.UserDto;
import com.farmAttic.services.UserAuthService;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;

@Controller
@Introspected
@Transactional
@AllArgsConstructor
public class UserController {

    private StatefulRedisConnection<String, UserDto> connection;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserAuthService userAuthService;

    @Get("/login")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public MutableHttpResponse<UserDto> getCache(){
        RedisCommands<String, UserDto> session = connection.sync();
        UserDto userDetails = session.get("user");
        return HttpResponse.ok(userDetails);
    }

}
