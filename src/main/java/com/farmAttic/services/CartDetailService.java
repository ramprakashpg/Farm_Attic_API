package com.farmAttic.services;

import com.farmAttic.Dtos.CartDto;
import com.farmAttic.models.Cart;
import com.farmAttic.models.CartDetails;
import com.farmAttic.models.CartDetailsId;
import com.farmAttic.repositories.CartDetailsRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Singleton
@AllArgsConstructor
public class CartDetailService {

    public CartDetailsRepository cartDetailsRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public void addToCart(CartDto cart) {
        CartDetailsId cartDetailsId = modelMapper.map(cart, CartDetailsId.class);
        CartDetails userCartDetails = cartDetailsRepository.findById(cartDetailsId).orElse(new CartDetails());
        if (userCartDetails.getCartDetailsId() == null) {
            CartDetails cartDetails = modelMapper.map(cart, CartDetails.class);
            cartDetails.setCartDetailsId(cartDetailsId);
            cartDetailsRepository.save(cartDetails);
        } else {
            Integer existingQty = userCartDetails.getQuantity();
            userCartDetails.setQuantity(existingQty + cart.getQuantity());
            cartDetailsRepository.update(userCartDetails);
        }

    }

}
