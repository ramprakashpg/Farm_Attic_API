package com.farmAttic.integration;

import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.ProductImageRepository;
import com.farmAttic.repositories.ProductRepository;
import com.farmAttic.repositories.UserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

@MicronautTest
class ProductControllerTest {

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


    @BeforeEach
    public void BeforeEach() {
        productImageRepository.deleteAll();
        productRepository.deleteAll();
    }

    @AfterEach
    public void AfterEach() {
        productImageRepository.deleteAll();
        productRepository.deleteAll();
        entityManager.flush();
    }

    @Test
    void shouldGetAllProducts() {
        User user = new User();
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        Product product = Product.builder().productName("product").productDescription("description").quantity(12).price(12).user(user).build();

        userRepository.save(user);
        productRepository.save(product);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        String expectedResponse = "[{\"productId\":\"" + product.getProductId() + "\",\"userId\":\"" + user.getUserId() + "\",\"productName\":\"product\",\"productDescription\":\"description\",\"quantity\":12,\"price\":12}]";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.GET("v1/product").bearerAuth("anything"), String.class);

        Assertions.assertEquals(expectedResponse, actualResponse);

    }
}
