package com.farmAttic.integration;

import com.farmAttic.Dtos.OrderResponse;
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

import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class OrderControllerTest {
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

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderHistoryRepository orderHistoryRepository;

    @Inject
    CartDetailsRepository cartDetailsRepository;

    Product product = new Product();


    CartDetails cartDetails=new CartDetails();

    User user = new User();

    @BeforeEach
    public void BeforeEach(){

        user.setEmail("dummy@test.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        Cart cart = new Cart();
        cart.setUserInfo(user);

        product = Product.builder().productName("product").productDescription("description").quantity(12).pricePerUnit(12).user(user).build();

        CartDetailsId cartDetailsId=new CartDetailsId();
        cartDetailsId.setCart(cart);
        cartDetailsId.setProduct(product);


        cartDetails = CartDetails.builder().cartDetailsId(cartDetailsId).quantity(2).price(24).build();

        userRepository.save(user);
        productRepository.save(product);
        cartRepository.save(cart);
        cartDetailsRepository.save(cartDetails);
    }

    @AfterEach
    public void AfterEach() {
        orderHistoryRepository.deleteAll();
        orderRepository.deleteAll();
        cartDetailsRepository.deleteAll();
        cartRepository.deleteAll();
        productImageRepository.deleteAll();
        productRepository.deleteAll();
        entityManager.flush();
    }

    @Test
    void shouldPlaceTheOrder() {
        Order order1 =new Order();
        order1.setUser(user);
        order1.setStatus("Ordered");
        orderRepository.save(order1);
        entityManager.getTransaction().commit();
        entityManager.clear();
        entityManager.close();

        HttpResponse<OrderResponse> actualResponse = client.toBlocking().exchange(HttpRequest.POST("v1/order/user",user.getUserId()).bearerAuth("anything"));
        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());

    }

    @Test
    void shouldAbleToGetOrderDetailsOfUser() {
        product = Product.builder().productName("product").productDescription("description").quantity(12).pricePerUnit(12).user(user).build();

        User user1=new User();
        user1.setEmail("dummy@test.com");
        user1.setFirstName("Sahiti");
        user1.setLastName("Priya");

        Cart cart = new Cart();
        cart.setUserInfo(user1);

        CartDetailsId cartDetailsId=new CartDetailsId();
        cartDetailsId.setCart(cart);
        cartDetailsId.setProduct(product);

        cartDetails = CartDetails.builder().cartDetailsId(cartDetailsId).quantity(2).price(24).build();

        Order order1 =new Order();
        order1.setUser(user1);
        order1.setStatus("Ordered");

        OrderHistoryId orderHistoryId=new OrderHistoryId();
        orderHistoryId.setOrder(order1);
        orderHistoryId.setProduct(product);
        OrderHistory orderHistory=OrderHistory.builder().historyId(orderHistoryId).quantity(cartDetails.getQuantity()).price(cartDetails.getPrice()).build();

        userRepository.save(user1);
        productRepository.save(product);
        cartRepository.save(cart);
        cartDetailsRepository.save(cartDetails);
        orderRepository.save(order1);
        orderHistoryRepository.save(orderHistory);
        entityManager.getTransaction().commit();

        HttpResponse<OrderResponse> actualResponse= client.toBlocking().exchange(HttpRequest.GET("v1/order/"+user1.getUserId()).bearerAuth("anything"));
        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
    }
}
