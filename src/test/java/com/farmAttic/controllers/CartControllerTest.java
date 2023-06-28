package com.farmAttic.controllers;

import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.Dtos.UserCartResponse;
import com.farmAttic.services.CartService;
import com.farmAttic.services.UserAuthService;
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
    @Test
    void shouldAddProductToCart() {
        String loggedInUser = "123@gmail.com";
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(UUID.randomUUID());
        productRequest.setProductId(UUID.randomUUID());


        when(cartService.saveToCart(productRequest,loggedInUser)).thenReturn(productRequest);
        when(authentication.getName()).thenReturn(loggedInUser);
        HttpResponse<ProductRequest> actualResponse = cartController.addProductToCart(authentication, productRequest);

        verify(cartService).saveToCart(productRequest, loggedInUser);
        assertEquals(HttpResponse.created(productRequest).getStatus(), actualResponse.getStatus());

    }
}
