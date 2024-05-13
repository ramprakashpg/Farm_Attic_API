package com.farmAttic.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.print.attribute.standard.Media;
import java.security.Principal;

@Secured(SecurityRule.IS_AUTHENTICATED)
public class HomeController {
    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index(Principal principal){
        System.out.println(principal.getName());
        return principal.getName();
    }
}
