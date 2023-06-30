package com.farmAttic.controllers;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.CartUpdateRequest;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.Dtos.UserCartResponse;
import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.services.CartService;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {
    private final CartService cartService = mock(CartService.class);
    private final Authentication authentication = Mockito.mock(Authentication.class);
    CartController cartController = new CartController(cartService);

    User user = new User();
    Cart cart = new Cart();
    Product product = new Product();
    @BeforeEach
    public void BeforeEach() {
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        cart.setCartId(UUID.randomUUID());
        cart.setUserInfo(user);
        product = Product.builder().productName("product").productDescription("description").quantity(12).pricePerUnit(12).user(user).build();

    }
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

    @Test
    void shouldGetUserCartDetails() {
        UserCartResponse cartResponse = new UserCartResponse();
        cartResponse.setUser(user);
        cartResponse.setCart(cart);
        CartResponse expectedResponse = new CartResponse(product, 2, 80);

        cartResponse.setUserProduct(Collections.singletonList(expectedResponse));

        when(cartService.getUserCartDetails(user.getUserId())).thenReturn(cartResponse);

        HttpResponse<UserCartResponse> actualResponse = cartController.getUserCartDetails(authentication, user.getUserId());
        assertEquals(HttpResponse.ok(cartResponse).getStatus(), actualResponse.getStatus());
    }

    @Test
    void shouldUpdateCart() throws Throwable {
        CartResponse expectedResponse = new CartResponse(product, 2, 80);

        when(cartService.updateCart(cart.getCartId(),product.getProductId(),2)).thenReturn(expectedResponse);
        CartUpdateRequest cartUpdateRequest = new CartUpdateRequest(2);

        HttpResponse<CartResponse> actualResponse = cartController.updateCart(authentication, cart.getCartId(), product.getProductId(),cartUpdateRequest);
        assertEquals(HttpResponse.ok(expectedResponse).getStatus(), actualResponse.getStatus());
    }

    @Test
    void shouldDeleteProductFromCart() throws Throwable {
        doNothing().when(cartService).deleteProductFromCart(cart.getCartId(), product.getProductId());

        HttpResponse actualResponse = cartController.deleteProductFromCart(cart.getCartId(), product.getProductId(),authentication);

        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());

    }
}
