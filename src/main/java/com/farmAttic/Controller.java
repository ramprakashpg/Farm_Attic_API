package com.farmAttic;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@io.micronaut.http.annotation.Controller("v1/")
public class Controller {

    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse check(Authentication authentication){
        return HttpResponse.ok();
    }

}
