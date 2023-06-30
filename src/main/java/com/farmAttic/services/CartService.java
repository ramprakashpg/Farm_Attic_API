package com.farmAttic.services;

import com.farmAttic.Dtos.*;
import com.farmAttic.models.Cart;
import com.farmAttic.models.CartDetails;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.CartRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Singleton
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private CartDetailService cartDetailService;
    private ProductService productService;
    private UserAuthService userAuthService;
    private static final ModelMapper modelMapper = new ModelMapper();


    public void generateCart(User loggedInUser) {
        Cart userCart = getUserCart(loggedInUser);
        if (userCart.getCartId() == null) {
            UserCartDto cart = new UserCartDto();
            cart.setUserInfo(loggedInUser);

            Cart newUserCart = modelMapper.map(cart, Cart.class);
            cartRepository.save(newUserCart);
        }

    }

    public Cart getUserCart(User loggedInUser) {
        return cartRepository.findByUserId(loggedInUser.getUserId()).orElse(new Cart());
    }

    public ProductRequest addToCart(Product product, User user, ProductRequest productRequest) {
        CartDto cartDto = new CartDto();
        Cart userCart = getUserCart(user);
        cartDto.setCart(userCart);
        cartDto.setProduct(product);
        cartDto.setQuantity(productRequest.getQuantity());
        return cartDetailService.addToCart(cartDto);
    }

    public UserCartResponse getUserCartDetails(UUID userId) {
        User loggedInUser = userAuthService.getUser(userId);
        Cart cart = getUserCart(loggedInUser);
        List<CartDetails> userCartDetails = cartDetailService.getUserCartDetails(cart.getCartId());
        List<CartResponse> cartResponses = new ArrayList<>();
        for (CartDetails cartDetail : userCartDetails) {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setProduct(cartDetail.getCartDetailsId().getProduct());
            cartResponse.setPrice(cartDetail.getPrice());
            cartResponse.setQuantity(cartDetail.getQuantity());
            cartResponses.add(cartResponse);
        }
        UserCartResponse userCartResponse = new UserCartResponse();
        userCartResponse.setCart(cart);
        userCartResponse.setUserProduct(cartResponses);
        return userCartResponse;
    }

    public CartResponse updateCart(UUID cartId, UUID productId, Integer quantity) throws Throwable {
        Cart cart = getCartById(cartId);
        CartResponse cartResponse = new CartResponse();
        if (cart.getCartId() != null) {
            CartDetails cartDetails = cartDetailService.updateUserCart(cart, productId, quantity);
            cartResponse.setProduct(cartDetails.getCartDetailsId().getProduct());
            cartResponse.setQuantity(cartDetails.getQuantity());
            cartResponse.setPrice(cartDetails.getPrice());
        }
        return cartResponse;

    }

    public ProductRequest saveToCart(ProductRequest productRequest, String loggedInUserEmail) {
        Product product = productService.getProduct(productRequest.getProductId());
        User currentUser = userAuthService.getCurrentUser(loggedInUserEmail);
        generateCart(currentUser);

        if (product.getProductId() != null && productRequest.getQuantity() <= product.getQuantity()) {
            return addToCart(product, currentUser, productRequest);
        }else{
            throw new NoSuchElementException("Product not found");
        }
    }

    private Cart getCartById(UUID cartId) {
        return cartRepository.findById(cartId).orElse(new Cart());
    }

    public void deleteProductFromCart(UUID cartId, UUID productId) throws Throwable {
        Cart cart = getCartById(cartId);
        cartDetailService.deleteProductFromCart(cart, productId);

    }

    public void clearCart(CartDetails cartDetail) {
        cartDetailService.clearCart(cartDetail);
    }

    public List<CartDetails> getDetails(Cart cart) {
       return cartDetailService.getDetails(cart);
    }
}
