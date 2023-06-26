package com.farmAttic.integration;

import com.farmAttic.Dtos.ProductRequest;
import com.farmAttic.models.Cart;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.repositories.CartRepository;
import com.farmAttic.repositories.ProductImageRepository;
import com.farmAttic.repositories.ProductRepository;
import com.farmAttic.repositories.UserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Inject
    CartRepository cartRepository;


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

    @Test
    void shouldGetAllUserProducts() {
        User user = new User();
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        User user1 = new User();
        user.setEmail("dummy@test123.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        Product product = Product.builder().productName("product").productDescription("description").quantity(12).price(12).user(user).build();
        Product product1 = Product.builder().productName("product").productDescription("description").quantity(12).price(12).user(user1).build();

        userRepository.save(user);
        userRepository.save(user1);
        productRepository.save(product);
        productRepository.save(product1);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        String expectedResponse = "[{\"productId\":\"" + product.getProductId() + "\",\"userId\":\"" + user.getUserId() + "\",\"productName\":\"product\",\"productDescription\":\"description\",\"quantity\":12,\"price\":12}]";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.GET("v1/product"+"/"+user.getUserId()).bearerAuth("anything"), String.class);

        Assertions.assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void shouldCreateProduct() {
        User user = new User();
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        userRepository.save(user);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        String dataRequest="{\n" +
                "    \"userId\":\""+user.getUserId()+"\",\n" +
                "    \"productDescription\": \"sahiti\",\n" +
                "    \"productName\": \"priya Addepalli\",\n" +
                "    \"quantity\": 122,\n" +
                "    \"price\":225,\n" +
                "    \"imageList\": [[-1, -40, -1, -32, 0, 16, 74, 70, 73]]\n" +
                "}";

        String actualResponse = client.toBlocking().retrieve(HttpRequest.POST("v1/product", dataRequest)
                .bearerAuth("anything"));

        List<Product> product=productRepository.findAll();

        String expectedResponse="{\"productId\":\""+product.get(0).getProductId()+"\",\"userId\":\""+user.getUserId()+"\",\"productName\":\"priya Addepalli\",\"productDescription\":\"sahiti\",\"quantity\":122,\"price\":225,\"imageList\":[\"/9j/4AAQSkZJ\"]}";

        Assertions.assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void shouldUpdateProduct() {
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

        String dataRequest="{\n" +
                "    \"userId\":\""+user.getUserId()+"\",\n" +
                "    \"productDescription\": \"sahiti\",\n" +
                "    \"productName\": \"priya Addepalli\",\n" +
                "    \"quantity\": 122,\n" +
                "    \"price\":225,\n" +
                "    \"imageList\": [[-1, -40, -1, -32, 0, 16, 74, 70, 73]]\n" +
                "}";

        String actualResponse = client.toBlocking().retrieve(HttpRequest.PUT("v1/product/"+product.getProductId(), dataRequest)
                .bearerAuth("anything"));

        String expectedResponse="{\"productId\":\""+product.getProductId()+"\",\"userId\":\""+user.getUserId()+"\",\"productName\":\"priya Addepalli\",\"productDescription\":\"sahiti\",\"quantity\":122,\"price\":225,\"imageList\":[\"/9j/4AAQSkZJ\"]}";

        Assertions.assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void shouldAddProductToCart() {
        User user = new User();
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        Cart cart = new Cart();
        cart.setUserInfo(user);

        Product product = Product.builder().productName("product").productDescription("description").quantity(12).price(12).user(user).build();

        userRepository.save(user);
        cartRepository.save(cart);
        productRepository.save(product);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(product.getProductId());
        productRequest.setQuantity(2);
        String expectedResponse = "{\"productId\":\""+product.getProductId()+"\",\"quantity\":2}";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.POST("v1/product/"+product.getProductId()+"/cart",productRequest).bearerAuth("anything"), String.class);

        assertEquals(actualResponse, expectedResponse);
    }
}
