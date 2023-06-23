package com.farmAttic.service;

import com.farmAttic.models.Cart;
import com.farmAttic.models.CartDetails;
import com.farmAttic.models.User;
import com.farmAttic.repositories.CartRepository;
import com.farmAttic.services.CartDetailService;
import com.farmAttic.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CartServiceTest {
    private CartRepository cartRepository;
    private CartService cartService;
    private CartDetailService cartDetailService;

    @BeforeEach
    void beforeEach(){
        cartRepository = Mockito.mock(CartRepository.class);
        cartDetailService = mock(CartDetailService.class);
        cartService = new CartService(cartRepository,cartDetailService);
    }

    @Test
    void shouldAssignCartForEachUser(){
        User loggedInUser = new User();
        loggedInUser.setUserId(UUID.randomUUID());
        loggedInUser.setEmail("dummy@gmail.com");
        loggedInUser.setFirstName("dummy");
        loggedInUser.setLastName("user");

        Cart userCart = new Cart();
        userCart.setUserInfo(loggedInUser);

        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(new Cart()));
        when(cartRepository.save(any(Cart.class))).thenReturn(userCart);

        cartService.generateCart(loggedInUser);
        verify(cartRepository).save(any(Cart.class));
    }
}
