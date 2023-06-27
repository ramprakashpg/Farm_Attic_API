package com.farmAttic.services;

import com.farmAttic.Dtos.UserCartDto;
import com.farmAttic.models.Cart;
import com.farmAttic.models.User;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Singleton
@AllArgsConstructor
public class UserCartService {
    private final CartService cartService;
    private static final ModelMapper modelMapper = new ModelMapper();

    public Cart generateCart(User loggedInUser){
        Cart userCart = cartService.getUserCart(loggedInUser);
        Cart newUserCart = new Cart();
        if(userCart.getCartId() == null) {
            UserCartDto cart = new UserCartDto();
            cart.setUserInfo(loggedInUser);

            newUserCart = modelMapper.map(cart, Cart.class);
        }
        return newUserCart;

    }
}
