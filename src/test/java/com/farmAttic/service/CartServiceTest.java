package com.farmAttic.service;

import com.farmAttic.Dtos.CartDto;
import com.farmAttic.Dtos.ProductDto;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.CartRepository;
import com.farmAttic.services.CartDetailService;
import com.farmAttic.services.CartService;
import com.farmAttic.services.ProductService;
import com.farmAttic.services.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CartServiceTest {
    private CartRepository cartRepository;
    private CartService cartService;
    private CartDetailService cartDetailService;

    private final UserAuthService userAuthService=mock(UserAuthService.class);
    private final ProductService productService=mock(ProductService.class);
    UUID uuid = UUID.randomUUID();
    User user = new User();

    ProductDto productRequest = new ProductDto();

    @BeforeEach
    void beforeEach(){
        cartRepository = Mockito.mock(CartRepository.class);
        cartDetailService = mock(CartDetailService.class);
        cartService = new CartService(cartRepository,cartDetailService, productService, userAuthService);
        List<byte[]> productImageDtoList = new ArrayList<>();
        productRequest.setProductDescription("description");
        productRequest.setProductName("productName");
        productRequest.setPricePerUnit(13);
        productRequest.setQuantity(13);
        productRequest.setUserId(uuid);
        byte[] byteArray = new byte[36];
        productImageDtoList.add(byteArray);
        productRequest.setImageList(productImageDtoList);

        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

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

    @Test
    void shouldAddProductToCart() {
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setProductId(UUID.randomUUID());
        productRequest1.setQuantity(2);
        User loggedInUser = new User();
        loggedInUser.setUserId(UUID.randomUUID());
        loggedInUser.setEmail("dummy@gmail.com");
        loggedInUser.setFirstName("dummy");
        loggedInUser.setLastName("user");
        Product product = Product.builder().productId(UUID.randomUUID()).productName("Hello").productDescription("").quantity(123).pricePerUnit(123).user(loggedInUser).build();
        Cart userCart = new Cart();
        userCart.setUserInfo(loggedInUser);

        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(userCart));

        cartService.addToCart(product,loggedInUser,productRequest1);

        verify(cartDetailService).addToCart(any(CartDto.class));


    }
    @Test
    void addProductToCart() {
        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setProductId(UUID.randomUUID());
        productRequest1.setQuantity(2);
        Product product = Product.builder().productId(uuid).productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).quantity(productRequest.getQuantity()).pricePerUnit(productRequest.getPricePerUnit()).user(user).build();
        User currentUser = new User();
        currentUser.setEmail("dummy@test.com");
        currentUser.setFirstName("Dummy");
        currentUser.setLastName("Name");

        Cart userCart = new Cart();
        userCart.setUserInfo(currentUser);

        when(productService.getProduct(any(UUID.class))).thenReturn(product);
        when(userAuthService.getCurrentUser(any(String.class))).thenReturn(user);
        when(cartRepository.findByUserId(any(UUID.class))).thenReturn(Optional.of(userCart));

        cartService.saveToCart(productRequest1,"dummy@test.com");

        verify(cartRepository).save(any(Cart.class));


    }
}
