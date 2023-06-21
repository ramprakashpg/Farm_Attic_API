package com.farmAttic.integration;

import com.farmAttic.repositories.UserRepository;
import com.farmAttic.services.UserAuthService;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;

public class UserControllerTest {
    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    UserAuthService userAuthService;

    @Inject
    UserRepository userRepository;

}
