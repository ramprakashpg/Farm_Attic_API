package com.farmAttic.client;


import com.farmAttic.services.UserAuthService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class HttpAuthenticationProvider implements HttpRequestAuthenticationProvider {
    private UserAuthService authService;

    @Override
    public @NonNull AuthenticationResponse authenticate(Object requestContext, @NonNull AuthenticationRequest authRequest) {
        if (authRequest.getIdentity().equals("sherlock12") && authRequest.getSecret().equals("password")) {
            authService.login(authRequest.getIdentity().toString());
            return AuthenticationResponse.success(authRequest.getIdentity().toString());
        } else {
            return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
        }
    }
}
