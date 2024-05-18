//package com.farmAttic.integration;
//
//import io.micronaut.context.annotation.Replaces;
//import io.micronaut.security.authentication.Authentication;
//import io.micronaut.security.filters.AuthenticationFetcher;
//import io.micronaut.security.token.TokenAuthenticationFetcher;
//import io.micronaut.security.token.reader.TokenResolver;
//import io.reactivex.rxjava3.annotations.NonNull;
//import io.reactivex.rxjava3.core.Flowable;
//import jakarta.inject.Inject;
//import jakarta.inject.Singleton;
//import java.util.HashMap;
//import java.util.Map;
//import org.reactivestreams.Publisher;
//
//
//@Singleton
//@Replaces(value = TokenAuthenticationFetcher.class)
//public class MockTokenAuthenticationFetcher implements AuthenticationFetcher {
//
//    @Inject
//    TokenResolver tokenResolver;
//
//
//    @Override
//    public Publisher<Authentication> fetchAuthentication(Object request) {
//        String email = "dummy@test.com";
//        Map<String, Object> authMap = new HashMap<>();
//        authMap.put("sub", email);
//        Authentication authentication = Authentication.build(email, authMap);
//
//        @NonNull Flowable auth = Flowable.fromArray(tokenResolver.resolveToken(request)).flatMap(token -> {
//            return Flowable.just(authentication);
//        });
//
//        return auth;
//    }
//}
