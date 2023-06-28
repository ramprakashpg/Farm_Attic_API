package com.farmAttic.service;

import com.farmAttic.Dtos.CartDto;
import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.models.*;
import com.farmAttic.repositories.CartDetailsRepository;
import com.farmAttic.services.CartDetailService;
import com.farmAttic.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartDetailsServiceTest {
    CartDetailsRepository cartDetailsRepository;
    CartDetailService cartDetailService;
    ProductDto productRequest = new ProductDto();
    private final ProductService productService=mock(ProductService.class);
    User currentUser = new User();
    Cart userCart = new Cart();




    @BeforeEach
    void beforeEach(){
        cartDetailsRepository = Mockito.mock(CartDetailsRepository.class);
        cartDetailService = new CartDetailService(cartDetailsRepository, productService);
        productRequest.setProductDescription("description");
        productRequest.setProductName("productName");
        productRequest.setPricePerUnit(13);
        productRequest.setQuantity(13);
        productRequest.setUserId(UUID.randomUUID());

        currentUser.setEmail("dummy@gmail.com");
        currentUser.setFirstName("Dummy");
        currentUser.setLastName("Name");

        userCart.setUserInfo(currentUser);
    }

    @Test
    void shouldAddProductToCart() {
        CartDetails cartDetails = new CartDetails();
        CartDetailsId cartDetailsId = new CartDetailsId();
        Product product = Product.builder().productId(UUID.randomUUID()).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(currentUser).build();

        cartDetailsId.setProduct(product);
        userCart.setUserInfo(currentUser);
        cartDetailsId.setCart(userCart);

        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setQuantity(1);
        CartDto cartDto = new CartDto(userCart,product,12);

        when(cartDetailsRepository.save(any(CartDetails.class))).thenReturn(cartDetails);

        cartDetailService.addToCart(cartDto);

        verify(cartDetailsRepository).save(any(CartDetails.class));
    }

    @Test
    void shouldUpdateCartWhenSameProductIsAdded() {
        CartDetails cartDetails = new CartDetails();
        CartDetailsId cartDetailsId = new CartDetailsId();
        Product product = Product.builder().productId(UUID.randomUUID()).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(currentUser).build();

        cartDetailsId.setProduct(product);
        cartDetailsId.setCart(userCart);

        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setQuantity(1);
        CartDto cartDto = new CartDto(userCart,product,12);

        when(cartDetailsRepository.findById(any(CartDetailsId.class))).thenReturn(Optional.of(cartDetails));
        when(cartDetailsRepository.update(cartDetails)).thenReturn(cartDetails);

        cartDetailService.addToCart(cartDto);

        verify(cartDetailsRepository).update(any(CartDetails.class));
    }

    @Test
    void shouldGetUserCartDetails() {
        CartDetails cartDetails = new CartDetails();
        CartDetailsId cartDetailsId = new CartDetailsId();
        Product product = Product.builder().productId(UUID.randomUUID()).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(currentUser).build();

        cartDetailsId.setProduct(product);
        userCart.setCartId(UUID.randomUUID());
        cartDetailsId.setCart(userCart);

        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setQuantity(1);

        when(cartDetailsRepository.findByCartId(any(UUID.class))).thenReturn(Collections.singletonList(cartDetails));

        List<CartDetails> expectedResponse = cartDetailService.getUserCartDetails(cartDetailsId.getCart().getCartId());
        verify(cartDetailsRepository).findByCartId(any(UUID.class));

        Assertions.assertEquals(1, expectedResponse.size());
    }
}
