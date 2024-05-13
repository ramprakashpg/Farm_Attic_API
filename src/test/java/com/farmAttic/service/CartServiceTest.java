package com.farmAttic.service;

import com.farmAttic.Dtos.*;
import com.farmAttic.models.*;
import com.farmAttic.repositories.CartRepository;
import com.farmAttic.services.CartDetailService;
import com.farmAttic.services.CartService;
import com.farmAttic.services.ProductService;
import com.farmAttic.services.UserAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;

public class CartServiceTest {
    private CartRepository cartRepository;
    private CartService cartService;
    private CartDetailService cartDetailService;

    private final UserAuthService userAuthService = mock(UserAuthService.class);
    private final ProductService productService = mock(ProductService.class);
    UUID uuid = UUID.randomUUID();

    ProductDto productRequest = ProductDto.builder()
            .productDescription("description")
            .productName("productname")
            .pricePerUnit(13)
            .quantity(13)
//            .userId(uuid)
            .build();
    User user = User.builder()
            .userId(uuid)
            .email("smssaddepalli@gmail.com")
            .firstName("Sahiti")
            .lastName("Priya")
            .build();


    @BeforeEach
    void beforeEach() {
        cartRepository = Mockito.mock(CartRepository.class);
        cartDetailService = mock(CartDetailService.class);
        cartService = new CartService(cartRepository, cartDetailService, productService, userAuthService);
        List<byte[]> productImageDtoList = new ArrayList<>();

        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        productRequest.setImageList(productImageDtoList);

    }

    @Test
    void shouldAssignCartForEachUser() {
        User loggedInUser = User.builder()
                .userId(UUID.randomUUID())
                .email("dummy@gmail.com")
                .firstName("dummy")
                .lastName("user")
                .build();

        Cart userCart = new Cart();
        userCart.setUserInfo(loggedInUser);

        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(new Cart()));
        when(cartRepository.save(any(Cart.class))).thenReturn(userCart);

        cartService.generateCart(loggedInUser);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void shouldAddProductToCart() {
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setProductId(UUID.randomUUID());
        productRequest1.setQuantity(2);
        User loggedInUser = User.builder()
                .userId(UUID.randomUUID())
                .email("dummy@gmail.com")
                .firstName("dummy")
                .lastName("user")
                .build();
        Product product = Product.builder().productId(UUID.randomUUID()).productName("Hello").productDescription("").quantity(123).pricePerUnit(123).user(loggedInUser).build();
        Cart userCart = new Cart();
        userCart.setUserInfo(loggedInUser);

        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(userCart));

        cartService.addToCart(product, loggedInUser, productRequest1);

        verify(cartDetailService).addToCart(any(CartDto.class));


    }

    @Test
    void addProductToCart() {
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setProductId(UUID.randomUUID());
        productRequest1.setQuantity(2);
        Product product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(user).build();

        Cart userCart = new Cart();
        userCart.setUserInfo(user);

        when(productService.getProduct(any(UUID.class))).thenReturn(product);
        when(userAuthService.getCurrentUser(any(String.class))).thenReturn(user);
        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(userCart));

        cartService.saveToCart(productRequest1, "dummy@test.com");

        verify(cartRepository).save(any(Cart.class));


    }

    @Test
    void shouldGetUserCartDetails() {
        Cart userCart = new Cart();
        userCart.setUserInfo(user);

        CartDetails cartDetails = new CartDetails();
        CartDetailsId cartDetailsId = new CartDetailsId();
        Product product = Product.builder().productId(UUID.randomUUID()).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(user).build();

        cartDetailsId.setProduct(product);
        userCart.setCartId(UUID.randomUUID());
        cartDetailsId.setCart(userCart);

        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setQuantity(1);

        when(userAuthService.getUser(any(UUID.class))).thenReturn(user);
        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(userCart));
        when(cartDetailService.getUserCartDetails(any(UUID.class))).thenReturn(Collections.singletonList(cartDetails));

        UserCartResponse cartResponse = cartService.getUserCartDetails(user.getUserId());
        Assertions.assertEquals(cartResponse.getUserProduct().size(), 1);

    }

    @Test
    void shouldUpdateCart() throws Throwable {
        Cart userCart = new Cart();
        userCart.setUserInfo(user);

        CartDetails cartDetails = new CartDetails();
        CartDetailsId cartDetailsId = new CartDetailsId();
        Product product = Product.builder().productId(UUID.randomUUID()).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(user).expiryDate(new Date()).pricePerUnit(40).build();

        cartDetailsId.setProduct(product);
        userCart.setCartId(UUID.randomUUID());
        cartDetailsId.setCart(userCart);

        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setQuantity(1);
        cartDetails.setPrice(80);

        when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.of(userCart));
        when(cartDetailService.updateUserCart(any(Cart.class), any(UUID.class), any(Integer.class))).thenReturn(cartDetails);

        CartResponse actualResponse = cartService.updateCart(cartDetailsId.getCart().getCartId(), product.getProductId(), 2);
        CartResponse expectedResponse = new CartResponse(product, 2, 80);
        Assertions.assertEquals(expectedResponse.getPrice(), actualResponse.getPrice());
    }

    @Test
    void shouldDeleteProductFromCart() throws Throwable {
        Cart userCart = new Cart();
        userCart.setUserInfo(user);
        Product product = Product.builder().productId(UUID.randomUUID()).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(user).expiryDate(new Date()).pricePerUnit(40).build();


        when(cartRepository.findById(any(UUID.class))).thenReturn(Optional.of(userCart));
        doNothing().when(cartDetailService).deleteProductFromCart(any(Cart.class), any(UUID.class));

        cartService.deleteProductFromCart(userCart.getCartId(), product.getProductId());
        verify(cartDetailService).deleteProductFromCart(any(Cart.class), any(UUID.class));
    }
}
