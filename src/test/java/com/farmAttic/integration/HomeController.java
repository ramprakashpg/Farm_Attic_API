package com.farmAttic.integration;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class HomeController {
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void shouldLoginSuccessfully() {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sherlock", "password");
        HttpRequest<UsernamePasswordCredentials> request = HttpRequest.POST("/login", credentials);
        HttpResponse<BearerAccessRefreshToken> actualResponse = client.toBlocking().exchange(request, BearerAccessRefreshToken.class);
        System.out.println(actualResponse.body().getAccessToken());

    }
}
