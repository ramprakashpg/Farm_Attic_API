package com.farmAttic.integration;

import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.*;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

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

    @Inject
    CartDetailsRepository cartDetailsRepository;


    @BeforeEach
    public void BeforeEach() {
        User user = new User();
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        Cart cart = new Cart();
        cart.setUserInfo(user);

        product = Product.builder().productName("product").productDescription("description").quantity(12).pricePerUnit(12).user(user).build();
        userRepository.save(user);
        productRepository.save(product);
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
        String expectedResponse = "{\"productId\":\""+product.getProductId()+"\",\"quantity\":2,\"price\":24}";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.POST("v1/cart/" + product.getProductId(), productRequest).bearerAuth("anything"), String.class);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void shouldGetUserCartDetails() {
    }
}
