package com.farmAttic.integration;

import com.farmAttic.Dtos.ProductCategory;
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
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Product product = Product.builder().productName("product").productDescription("description").productCategory(ProductCategory.FRUITS).quantity(12).pricePerUnit(12).user(user).build();

        userRepository.save(user);
        productRepository.save(product);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        String expectedResponse = "[{\"productId\":\"" + product.getProductId() + "\",\"userId\":\"" + user.getUserId() + "\",\"productName\":\"product\",\"productDescription\":\"description\",\"quantity\":12,\"productCategory\":\"FRUITS\",\"pricePerUnit\":12}]";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.GET("v1/product").bearerAuth("anything"), String.class);

        assertEquals(expectedResponse, actualResponse);

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
        Product product = Product.builder().productName("product").productDescription("description").quantity(12).pricePerUnit(12).user(user).build();
        Product product1 = Product.builder().productName("product").productDescription("description").quantity(12).pricePerUnit(12).user(user1).build();

        userRepository.save(user);
        userRepository.save(user1);
        productRepository.save(product);
        productRepository.save(product1);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        String expectedResponse = "[{\"productId\":\"" + product.getProductId() + "\",\"userId\":\"" + user.getUserId() + "\",\"productName\":\"product\",\"productDescription\":\"description\",\"quantity\":12,\"pricePerUnit\":12}]";
        String actualResponse = client.toBlocking().retrieve(HttpRequest.GET("v1/product" + "/" + user.getUserId()).bearerAuth("anything"), String.class);

        assertEquals(expectedResponse, actualResponse);

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

        String dataRequest = "{\n" +
                "    \"userId\":\"" + user.getUserId() + "\",\n" +
                "    \"productDescription\": \"sahiti\",\n" +
                "    \"productName\": \"connsadain\",\n" +
                "    \"quantity\": 50,\n" +
                "     \"productCategory\":\"FRUITS\",\n"+
                "    \"pricePerUnit\":57,\n" +
                "    \"unit\":\"kg\",\n" +
                "    \"expiryDate\":1234,\n" +
                "    \"imageList\": []\n" +
                "}";

        String actualResponse = client.toBlocking().retrieve(HttpRequest.POST("v1/product", dataRequest)
                .bearerAuth("anything"));

        List<Product> product = productRepository.findAll();

        String expectedResponse = "{\"productId\":\"" + product.get(0).getProductId() + "\",\"userId\":\"" + user.getUserId() + "\",\"productName\":\"connsadain\",\"productDescription\":\"sahiti\",\"quantity\":50,\"productCategory\":\"FRUITS\",\"pricePerUnit\":57,\"unit\":\"kg\",\"expiryDate\":1234}";

        Assertions.assertEquals(expectedResponse, actualResponse);

    }

    @Test
    void shouldUpdateProduct() {
        User user = new User();
        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");
        Product product = Product.builder().productName("product").productDescription("description").productCategory(ProductCategory.FRUITS).quantity(12).pricePerUnit(12).expiryDate(new Date(2022, Calendar.FEBRUARY,1)).user(user).build();

        userRepository.save(user);
        productRepository.save(product);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        String dataRequest = "{\n" +
                "    \"userId\":\""+user.getUserId()+"\",\n" +
                "    \"productDescription\": \"sahiti\",\n" +
                "    \"productName\": \"connsadain\",\n" +
                "    \"quantity\": 50,\n" +
                "     \"productCategory\":\"FRUITS\",\n"+
                "    \"pricePerUnit\":57,\n" +
                "    \"unit\":\"kg\",\n" +
                "    \"expiryDate\":1687782675939,\n" +
                "    \"imageList\": []\n" +
                "}";

        String actualResponse = client.toBlocking().retrieve(HttpRequest.PUT("v1/product/" + product.getProductId(), dataRequest)
                .bearerAuth("anything"));

        String expectedResponse = "{\"productId\":\""+product.getProductId()+"\",\"userId\":\""+user.getUserId()+"\",\"productName\":\"connsadain\",\"productDescription\":\"sahiti\",\"quantity\":50,\"productCategory\":\"FRUITS\",\"pricePerUnit\":57,\"expiryDate\":1687782675939}";

        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse, actualResponse);

    }
}
