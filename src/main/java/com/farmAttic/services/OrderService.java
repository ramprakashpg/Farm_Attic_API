package com.farmAttic.services;

import com.farmAttic.Dtos.CartResponse;
import com.farmAttic.Dtos.OrderResponse;
import com.farmAttic.models.*;
import com.farmAttic.repositories.OrderRepository;
import jakarta.inject.Singleton;

import java.sql.SQLOutput;
import java.util.ArrayList;
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
           updateProduct(orderHistory);
        });
        return getResponse(cartDetailsList,order);
    }

    private void updateProduct(OrderHistory orderHistory) {
        Product product=productService.getProduct(orderHistory.getHistoryId().getProduct().getProductId());
        System.out.println("product quantity"+orderHistory.getHistoryId().getProduct().getQuantity());
        System.out.println("order quantity"+orderHistory.getQuantity());
        System.out.println("remaining quantity"+(orderHistory.getHistoryId().getProduct().getQuantity()-orderHistory.getQuantity()));
        int quantity= orderHistory.getHistoryId().getProduct().getQuantity()-orderHistory.getQuantity();
        product.setQuantity(quantity);
        productService.updateProduct(product);
    }

    private OrderHistory getOrderHistory(Order order, CartDetails cartDetail) {
        OrderHistoryId orderHistoryId=new OrderHistoryId();
        orderHistoryId.setOrder(order);
        orderHistoryId.setProduct(cartDetail.getCartDetailsId().getProduct());
        System.out.println(cartDetail.getCartDetailsId().getProduct().getQuantity());
        OrderHistory orderHistory=new OrderHistory();
        orderHistory.setHistoryId(orderHistoryId);
        orderHistory.setQuantity(cartDetail.getQuantity());
        orderHistory.setPrice(cartDetail.getPrice());
        return orderHistory;
    }

    private OrderResponse getResponse(List<CartDetails> cartDetailsList, Order order) {
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        List<CartResponse> cartResponseList=new ArrayList<>();
        cartDetailsList.forEach(cartDetail->{
            CartResponse cartResponse=new CartResponse();
            cartResponse.setProduct(cartDetail.getCartDetailsId().getProduct());
            cartResponse.setQuantity(cartDetail.getQuantity());
            cartResponse.setPrice(cartDetail.getPrice());
            cartResponseList.add(cartResponse);
        });
        orderResponse.setStatus(order.getStatus());
        orderResponse.setCartResponseList(cartResponseList);
        return orderResponse;
    }
}
