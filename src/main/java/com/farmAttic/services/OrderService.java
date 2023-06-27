package com.farmAttic.services;

import com.farmAttic.Dtos.OrderResponse;
import com.farmAttic.models.*;
import com.farmAttic.repositories.OrderRepository;
import jakarta.inject.Singleton;

import java.sql.SQLOutput;
import java.util.List;
import java.util.UUID;

@Singleton
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;
    private final OrderHistoryService orderHistoryService;

    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, ProductService productService, OrderHistoryService orderHistoryService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderHistoryService = orderHistoryService;
        this.cartService = cartService;
    }

    public OrderResponse placeOrder(UUID userId) {
        Order order=new Order();
        User user=productService.getUser(userId);
        order.setUser(user);
        order.setStatus("Ordered");
        orderRepository.save(order);
        return placeOrderHistory(order);
    }

    private OrderResponse placeOrderHistory(Order order) {
        Cart cart=cartService.getUserCart(order.getUser());
        List<CartDetails> cartDetailsList=cartService.getDetails(cart);
       cartDetailsList.forEach(cartDetail->{
           OrderHistory orderHistory = getOrderHistory(order, cartDetail);
           orderHistoryService.save(orderHistory);
        });
        return getResponse(cartDetailsList,order);
    }

    private OrderHistory getOrderHistory(Order order, CartDetails cartDetail) {
        OrderHistoryId orderHistoryId=new OrderHistoryId();
        orderHistoryId.setOrder(order);
        orderHistoryId.setProduct(cartDetail.getCartDetailsId().getProduct());
        OrderHistory orderHistory=new OrderHistory();
        orderHistory.setHistoryId(orderHistoryId);
        orderHistory.setQuantity(cartDetail.getQuantity());
        orderHistory.setPrice(cartDetail.getPrice());
        return orderHistory;
    }

    private OrderResponse getResponse(List<CartDetails> cartDetailsList, Order order) {
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setCartDetailsList(cartDetailsList);
        return orderResponse;
    }
}
