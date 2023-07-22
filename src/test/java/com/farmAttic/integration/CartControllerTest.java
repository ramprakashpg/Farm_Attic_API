package com.farmAttic.integration;

import com.farmAttic.Dtos.CartUpdateRequest;
import com.farmAttic.Dtos.ProductCategory;
import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.*;
import com.farmAttic.repositories.*;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class CartControllerTest {
    @Inject
    @Client("/")
    HttpClient client;


    @Inject
    ProductRepository productRepository;

    @Inject
    ProductImageRepository productImageRepository;
    @Inject
    UserRepository userRepository;

    @Inject
    EntityManager entityManager;
    @Inject
    CartRepository cartRepository;
    Product product = new Product();
    User user = new User();
    Cart cart = new Cart();
    CartDetails cartDetails = new CartDetails();
    CartDetailsId cartDetailsId = new CartDetailsId();


    @Inject
    CartDetailsRepository cartDetailsRepository;


    @BeforeEach
    public void BeforeEach() {
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        cart.setUserInfo(user);
        product = Product.builder().productName("product").productDescription("description")
                .productCategory(ProductCategory.FRUITS)
                .quantity(12).pricePerUnit(12).user(user).build();
        userRepository.save(user);
        productRepository.save(product);
        cartRepository.save(cart);

        cartDetailsId.setCart(cart);
        cartDetailsId.setProduct(product);
        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setPrice(12);
        cartDetails.setQuantity(24);

        cartDetailsRepository.save(cartDetails);
    }

    @AfterEach
    public void AfterEach() {
        cartDetailsRepository.deleteAll();
        cartRepository.deleteAll();
        productImageRepository.deleteAll();
        productRepository.deleteAll();
        entityManager.flush();
    }

    @Test
    void shouldAddProductToCart() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(product.getProductId());
        productRequest.setQuantity(2);
        String expectedResponse = "{\"productId\":\"" + product.getProductId() + "\",\"quantity\":2,\"price\":24}";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.POST("v1/cart/product", productRequest).bearerAuth("anything"), String.class);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void shouldGetUserCartDetails() {
        HttpResponse actualResponse = client.toBlocking().exchange(HttpRequest.GET("v1/cart/user/" + user.getUserId()).bearerAuth("anything"), HttpResponse.class);

        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
    }

    @Test
    void shouldUpdateCart() {
        CartUpdateRequest cartUpdateRequest = new CartUpdateRequest(2);

        HttpResponse actualResponse = client.toBlocking().exchange(HttpRequest.PATCH("v1/cart/" + cart.getCartId() + "/product/" + product.getProductId(), cartUpdateRequest).bearerAuth("anything"), HttpResponse.class);

        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
    }
}
