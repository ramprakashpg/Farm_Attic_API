package com.farmAttic.service;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.OrderResponse;
import com.farmAttic.models.*;
import com.farmAttic.repositories.OrderRepository;
import com.farmAttic.services.CartService;
import com.farmAttic.services.OrderHistoryService;
import com.farmAttic.services.OrderService;
import com.farmAttic.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    private final ProductService productService = mock(ProductService.class);
    private final OrderHistoryService orderHistoryService = mock(OrderHistoryService.class);

    private final CartService cartService = mock(CartService.class);

    private OrderService orderService;

    User user = new User();

    OrderResponse orderResponse = new OrderResponse();

    Cart cart = new Cart();

    CartDetails cartDetails = new CartDetails();

    Product product = new Product();

    @BeforeEach
    public void beforeEach() {
        orderService = new OrderService(orderRepository, productService, orderHistoryService, cartService);

        UUID uuid = UUID.randomUUID();
        user.setUserId(uuid);
        user.setEmail("smssaddepalli@gmail.com");
        user.setFirstName("Sahiti");
        user.setLastName("Priya");

        product.setProductId(uuid);
        product.setProductDescription("description");
        product.setProductName("productName");
        product.setPricePerUnit(200);
        product.setQuantity(200);
        product.setUser(user);

        CartResponse cartResponse = new CartResponse();
        cartResponse.setPrice(2400);
        cartResponse.setQuantity(12);
        cartResponse.setProduct(product);

        cart.setUserInfo(user);
        cart.setCartId(uuid);

        CartDetailsId cartDetailsId = new CartDetailsId();
        cartDetailsId.setCart(cart);
        cartDetailsId.setProduct(product);
        cartDetails.setCartDetailsId(cartDetailsId);
        cartDetails.setQuantity(cartResponse.getQuantity());
        cartDetails.setPrice(cartResponse.getPrice());

        orderResponse.setOrderId(uuid);
        orderResponse.setStatus("Ordered");
        orderResponse.setCartResponseList(Collections.singletonList(cartResponse));
        orderResponse.setTotalPrice(2400);
    }

    @Test
    void ShouldAbleToPlaceOrder() {


        Order order = new Order();
        order.setOrderId(UUID.randomUUID());
        order.setStatus(orderResponse.getStatus());
        order.setUser(user);

        OrderHistoryId orderHistoryId = new OrderHistoryId();
        orderHistoryId.setOrder(order);
        orderHistoryId.setProduct(product);
        OrderHistory orderHistory = OrderHistory.builder().historyId(orderHistoryId).quantity(cartDetails.getQuantity()).price(cartDetails.getPrice()).build();

        when(productService.getUser(user.getUserId())).thenReturn(user);
        when(cartService.getUserCart(user)).thenReturn(cart);
        when(cartService.getDetails(cart)).thenReturn(Collections.singletonList(cartDetails));
        when(productService.getProduct(orderHistory.getHistoryId().getProduct().getProductId())).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderHistoryService.save(orderHistory)).thenReturn(orderHistory);

        doNothing().when(productService).updateProduct(product);
        doNothing().when(cartService).clearCart(cartDetails);

        orderService.placeOrder(user.getUserId());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldAbleToFetchOrderDetailsOfUser() {

        Order order = new Order();
        order.setOrderId(orderResponse.getOrderId());
        order.setStatus(orderResponse.getStatus());
        order.setUser(user);

        OrderHistoryId orderHistoryId = new OrderHistoryId();
        orderHistoryId.setOrder(order);
        orderHistoryId.setProduct(product);
        OrderHistory orderHistory = OrderHistory.builder().historyId(orderHistoryId).quantity(cartDetails.getQuantity()).price(cartDetails.getPrice()).build();

        when(orderRepository.findByUser(any(UUID.class))).thenReturn(Collections.singletonList(order));
        when(orderHistoryService.getDetails(any(Order.class))).thenReturn(Collections.singletonList(orderHistory));

        List<OrderResponse> actualResponse = orderService.getOrderDetails(user.getUserId());
        Assertions.assertEquals("Ordered", actualResponse.get(0).getStatus());
    }
}
