package com.farmAttic.services;

import com.farmAttic.Dtos.UserCartDto;
import com.farmAttic.models.Cart;
import com.farmAttic.models.User;
import com.farmAttic.repositories.CartRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Singleton
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private static final ModelMapper modelMapper = new ModelMapper();


    public void generateCart(User loggedInUser){
        Cart userCart = cartRepository.findByUserId(loggedInUser.getUserId()).orElse(new Cart());
        if(userCart.getCartId() == null) {
            UserCartDto cart = new UserCartDto();
            cart.setUserInfo(loggedInUser);

            Cart newUserCart = modelMapper.map(cart, Cart.class);
            cartRepository.save(newUserCart);
        }

    }

}
