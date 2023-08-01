package com.farmAttic;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "Bearer AUTH",
        type = SecuritySchemeType.APIKEY,
        scheme = "bearer",
        bearerFormat = "jwt"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Farm Attic API",
                version = "1.1.0",
                description = "Backend API for Farm Attic application",
                license = @License(name = "Copyright (c) 2022 - Farm Attic. All rights reserved.", url = "https://www.farmattic.com"),
                contact = @Contact(url = "https://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")

        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
