package com.farmAttic.integration;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.token.TokenAuthenticationFetcher;
import io.micronaut.security.token.reader.TokenResolver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import io.reactivex.rxjava3.core.Flowable;

import java.util.HashMap;
import java.util.Map;

@Singleton
@Replaces(value = TokenAuthenticationFetcher.class)
public class MockTokenAuthenticationFetcher implements AuthenticationFetcher {

    @Inject
    TokenResolver tokenResolver;

    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {

        String email = "dummy@test.com";
        Map<String, Object> authMap = new HashMap<>();
        authMap.put("sub", email);
        Authentication authentication = Authentication.build(email, authMap);

        Flowable<Authentication> auth = Flowable.fromOptional(tokenResolver.resolveToken(request)).flatMap(token -> {
            return Flowable.just(authentication);
        });

        return auth;
    }
}
