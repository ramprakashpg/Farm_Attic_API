package com.farmAttic.services;

import com.farmAttic.Dtos.CartDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.Dtos.UserCartDto;
import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.CartRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@Singleton
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private CartDetailService cartDetailService;
    private static final ModelMapper modelMapper = new ModelMapper();


    public void generateCart(User loggedInUser){
        Cart userCart = getUserCart(loggedInUser);
        if(userCart.getCartId() == null) {
            UserCartDto cart = new UserCartDto();
            cart.setUserInfo(loggedInUser);

            Cart newUserCart = modelMapper.map(cart, Cart.class);
            cartRepository.save(newUserCart);
        }

    }

    public Cart getUserCart(User loggedInUser) {
        return cartRepository.findByUserId(loggedInUser.getUserId()).orElse(new Cart());
    }

    public void addToCart(Product product, User user, ProductRequest productRequest){
        CartDto cartDto = new CartDto();
        Cart userCart = getUserCart(user);
        cartDto.setCart(userCart);
        cartDto.setProduct(product);
        cartDto.setQuantity(productRequest.getQuantity());
        cartDetailService.addToCart(cartDto);
    }

}
