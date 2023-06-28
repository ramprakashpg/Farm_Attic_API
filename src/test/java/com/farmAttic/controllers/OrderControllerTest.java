package com.farmAttic.controllers;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.OrderResponse;
import com.farmAttic.models.Product;
import com.farmAttic.models.User;
import com.farmAttic.services.OrderService;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private final Authentication authentication = Mockito.mock(Authentication.class);
    private final OrderService orderService=mock(OrderService.class);

    User user =new User();

    OrderResponse orderResponse=new OrderResponse();

    OrderController orderController=new OrderController(orderService);

    @BeforeEach
    public void beforeEach() {
        UUID uuid = UUID.randomUUID();
        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        Product product=new Product();
        product.setProductDescription("description");
        product.setProductName("productName");
        product.setPricePerUnit(200);
        product.setQuantity(200);
        product.setUser(user);

        CartResponse cartResponse=new CartResponse();
        cartResponse.setPrice(2400);
        cartResponse.setQuantity(12);
        cartResponse.setProduct(product);

        orderResponse.setOrderId(uuid);
        orderResponse.setStatus("Ordered");
        orderResponse.setCartResponseList(Collections.singletonList(cartResponse));
        orderResponse.setTotalPrice(2400);
    }

    @Test
    void shouldGetOrderDetailsForUser() {
        List<OrderResponse> orderResponseList=new ArrayList<>();
        orderResponseList.add(orderResponse);

        when(orderService.getOrderDetails(user.getUserId())).thenReturn(orderResponseList);

        HttpResponse<List<OrderResponse>> actualResponse = orderController.getOrderDetails(user.getUserId(),authentication);
        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
        assertEquals(1, Objects.requireNonNull(actualResponse.body()).size());

    }

    @Test
    void shouldAbleToPlaceOrderForUser() {

        when(orderService.placeOrder(user.getUserId())).thenReturn(orderResponse);

        HttpResponse<OrderResponse> actualResponse = orderController.placeOrder(user.getUserId(),authentication);
        assertEquals(HttpResponse.ok().getStatus(), actualResponse.getStatus());
        assertEquals(orderResponse.getOrderId(), Objects.requireNonNull(actualResponse.body()).getOrderId());

    }
}
