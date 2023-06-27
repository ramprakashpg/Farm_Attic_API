package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.services.CartService;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {
    private final CartService cartService = mock(CartService.class);
    private final Authentication authentication = Mockito.mock(Authentication.class);
    CartController cartController = new CartController(cartService);

}
